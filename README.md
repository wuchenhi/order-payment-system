金融场景微服务模式订单支付系统

    应用技术: SpringBoot
             Mybatis
             Dubbo
             RocketMQ
             Zookeeper
             MySQL
             Redis

注意事项:

    1. service.impl 服务需要添加:
        @Component :添加注解 'spring @Service' , 使用原因: 避免与dubbo @Service冲突
        @Service(interfaceClass = xxxxxxService.class) :该 service 为dubbo service
        
    2. application 需要添加Dubbo配置 @EnableDubboConfiguration
    
    3. 库存回退和扣减均存在并发问题,解决:
       低并发场景--数据库乐观锁
       高并发场景--Redis预减库存，库存充足则通过MQ真正扣减库存


