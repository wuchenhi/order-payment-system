//package com.nbcb.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.nbcb.constant.ShopCode;
//import com.nbcb.exception.CastException;
//import com.nbcb.mapper.ShopUserMapper;
//import com.nbcb.pojo.ShopOrder;
//import com.nbcb.pojo.ShopPay;
//import com.nbcb.pojo.ShopUser;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.context.annotation.Bean;
//
//import java.util.Date;
//import java.util.List;
//
//@SuppressWarnings("ALL")
//@Slf4j
//@SpringBootConfiguration
//public class UserPaymentConsumerMQListener {
//    @Autowired
//    private ShopUserMapper userMapper;
//
//    @Value("${mq.rocketmq.name-server}")
//    private String namesrvAddr;
//    @Value("${mq.pay.consumer.group.name}")
//    private String groupName;
//    @Value("${mq.pay.topic}")
//    private String topic;
//    @Value("${mq.rocketmq.consumer.tag}")
//    private String tag;
//    @Value("${mq.rocketmq.consumer.consumeThreadMin}")
//    private int consumeThreadMin;
//    @Value("${mq.rocketmq.consumer.consumeThreadMax}")
//    private int consumeThreadMax;
//
//    @Bean
//    public DefaultMQPushConsumer getPaymentRocketMQConsumer() throws MQClientException {
//
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
//        consumer.setNamesrvAddr(namesrvAddr);
//        consumer.setConsumeThreadMin(consumeThreadMin);
//        consumer.setConsumeThreadMax(consumeThreadMax);
//        consumer.setMessageModel(MessageModel.BROADCASTING);
//        consumer.registerMessageListener(new PaymentConsumerMessageListener());
//        consumer.subscribe(topic, this.tag);
//        consumer.start();
//        System.out.println("UserPaymentConsumerMQListener消费者启动");
//        return consumer;
//    }
//
//    // mq支付成功回调 增加积分
//    class PaymentConsumerMessageListener implements MessageListenerConcurrently {
//        @Override
//        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//            for (MessageExt messageExt : msgs) {
//                try {
//                    // 1 解析消息
//                    String body = new String(messageExt.getBody(), "UTF-8");
//                    ShopPay pay = JSON.parseObject(body, ShopPay.class);
//                    // 监听订阅已支付MQ消息
//                    log.info("用户已支付回调服务,接受到消息");
//                    if (pay != null &&
//                            pay.getOrderId() != null &&
//                            pay.getPayId() != null &&
//                            pay.getIsPaid().intValue() == ShopCode.SHOP_ORDER_PAY_STATUS_IS_PAY.getCode().intValue()) {
//                        // 2 查询订单
//                        ShopOrder order = userMapper.selectByPrimaryKey(pay.getId());
//                        // 3 更改订单状态
//                        order.setPayStatus(ShopCode.SHOP_ORDER_PAY_STATUS_IS_PAY.getCode());
//                        order.setPayTime(new Date());
//                        // 4 更新订单数据到数据库
//                        userMapper.updateByPrimaryKey(order);
//                        log.info("更改订单支付状态:已支付");
//                    } else {
//                        CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    log.info("更改订单支付状态失败");
//                }
//            }
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        }
//    }
//}
