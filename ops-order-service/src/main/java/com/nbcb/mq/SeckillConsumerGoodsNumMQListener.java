package com.nbcb.mq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.nbcb.api.IGoodsService;
import com.nbcb.api.IOrderService;
import com.nbcb.constant.ShopCode;
import com.nbcb.exception.CastException;
import com.nbcb.pojo.ShopOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import java.util.List;


@Slf4j
@SpringBootConfiguration
public class SeckillConsumerGoodsNumMQListener {


//    @Reference(check = false)
    @Autowired
    private IOrderService orderService;


    @Value("${mq.rocketmq.name-server}")
    private String namesrvAddr;
    @Value("${mq.seckill.consumer.group.name}")
    private String groupName;
    @Value("${mq.seckill.topic}")
    private String topic;
    @Value("${mq.seckill.tag}")
    private String tag;
    @Value("${mq.rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${mq.rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;

    @Bean
    public DefaultMQPushConsumer getSeckillRocketMQConsumer() throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.registerMessageListener(new SeckillMessageReduceGoodsNumMQListener());
        consumer.subscribe(topic, this.tag);
        consumer.start();
        System.out.println("SeckillReduceGoodsNumMQListener消费者启动");
        return consumer;
    }


    class SeckillMessageReduceGoodsNumMQListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt messageExt : msgs) {
            try {
                // 1 解析消息
                String body = new String(messageExt.getBody(), "UTF-8");
                ShopOrder shopOrder = JSON.parseObject(body, ShopOrder.class);
                log.info("秒杀商品扣减服务,接受到消息");

                // 空指针过滤
                if (shopOrder.getUserId() != null ||
                        shopOrder.getGoodsId() != null) {

                    orderService.secKill(shopOrder.getUserId(), shopOrder.getGoodsId());
                    log.info("秒杀商品扣减服务:已更改");
                } else {
                    CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("秒杀商品扣减服务:失败");
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
    }

}
