package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.nbcb.api.IGoodsService;
import com.nbcb.api.IOrderService;
import com.nbcb.config.AccessLimit;
import com.nbcb.constant.ShopCode;
import com.nbcb.entity.Result;
import com.nbcb.exception.CastException;
import com.nbcb.pojo.ShopGoods;
import com.nbcb.pojo.ShopOrder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.alibaba.com.caucho.hessian.io.HessianInputFactory.log;

@RestController
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Reference
    private IGoodsService goodsService;

    @Reference
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisScript<Long> redisScript;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Value("${rocketmq.producer.group}")
    private String groupName;

    @Value("${mq.seckill.topic}")
    private String topic;

    @Value("${mq.seckill.tag}")
    private String tag;

    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();

    /**
     * 系统初始化，把商品库存数量加载到Redis
     **/
    @Override
    public void afterPropertiesSet() throws Exception {
        List<ShopGoods> list = goodsService.findAll();

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getGoodsId(), goodsVo.getGoodsNumber());
            EmptyStockMap.put(goodsVo.getGoodsId(), false);
        });
    }

    /**
     *
     * @param userId
     * @param goodsId
     * @return 秒杀地址 string返回不合理
     */
    @AccessLimit(second = 5, maxCount = 5, needLogin = true)
    @GetMapping(value = "/path")
    public String getPath(long userId, long goodsId) {
        String str = orderService.createPath(userId, goodsId);
        return str;
    }

    /**
     * 秒杀
     *
     * @param shopOrder
     * @return
     */
    @PostMapping("/{path}/doSeckill")
    public Result doSeckill(@PathVariable String path, @RequestBody ShopOrder shopOrder) {
        ShopOrder order = new ShopOrder();
        order.setGoodsId(shopOrder.getGoodsId());
        order.setUserId(shopOrder.getUserId());

        ValueOperations valueOperations = redisTemplate.opsForValue();

        // 校验地址
        boolean check = orderService.checkPath(order.getUserId(), order.getGoodsId(), path);
        if (!check) {
           return new Result(ShopCode.SHOP_SEKILL_PATHWRONG.getSuccess(), ShopCode.SHOP_SEKILL_PATHWRONG.getCode(), ShopCode.SHOP_SEKILL_PATHWRONG.getMessage());
        }

        // 判断是否重复抢购
        String seckillOrderJson = (String) valueOperations.get("order:" +
                order.getUserId() + ":" + order.getGoodsId());

        if (!StringUtils.isEmpty(seckillOrderJson)) {
            return new Result(ShopCode.SHOP_SEKILL_REPEAT.getSuccess(), ShopCode.SHOP_SEKILL_REPEAT.getCode(), ShopCode.SHOP_SEKILL_REPEAT.getMessage());
        }

        //内存标记,减少Redis访问
        if (EmptyStockMap.get(order.getGoodsId())) {
            return new Result(ShopCode.SHOP_GOODS_NUM_NOT_ENOUGH.getSuccess(), ShopCode.SHOP_GOODS_NUM_NOT_ENOUGH.getCode(), ShopCode.SHOP_GOODS_NUM_NOT_ENOUGH.getMessage());
        }

        //预减库存
        Long stock = (Long) redisTemplate.execute(redisScript, Collections.singletonList("seckillGoods:" + order.getGoodsId()), Collections.EMPTY_LIST);
        if (stock < 0) {
            EmptyStockMap.put(order.getGoodsId(), true);
            valueOperations.increment("seckillGoods:" + order.getGoodsId(), 1);
            return new Result(ShopCode.SHOP_GOODS_NUM_NOT_ENOUGH.getSuccess(), ShopCode.SHOP_GOODS_NUM_NOT_ENOUGH.getCode(), ShopCode.SHOP_GOODS_NUM_NOT_ENOUGH.getMessage());
        }

        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                //发送消息到MQ,有延迟和堆积,使用线程异步优化
                SendResult sendResult = null;
                try {
                    sendResult = sendMessage(topic, tag, String.valueOf(order.getGoodsId()), JSON.toJSONString(order));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                    log.info("秒杀订单,消息发送成功");
                }
            }
        });
        return new Result(ShopCode.SHOP_SUCCESS.getSuccess(), ShopCode.SHOP_SUCCESS.getCode(), ShopCode.SHOP_SUCCESS.getMessage());
    }

    private SendResult sendMessage(String topic, String tag, String key, String body) throws Exception {
        if (StringUtils.isEmpty(topic)) {
            CastException.cast(ShopCode.SHOP_MQ_TOPIC_IS_EMPTY);
        }
        if (StringUtils.isEmpty(body)) {
            CastException.cast(ShopCode.SHOP_MQ_MESSAGE_BODY_IS_EMPTY);
        }
        Message message = new Message(topic, tag, key, body.getBytes());
        SendResult sendResult = rocketMQTemplate.getProducer().send(message);
        return sendResult;
    }
}