金融场景微服务模式订单支付系统

    应用技术: SpringBoot
             Mybatis
             Dubbo
             RocketMQ
             Zookeeper
             MySQL
    应用工具: Twitter-Snowflake
             RestTemplate

注意事项:

    1. service.impl 服务需要添加:
        @Component :添加注解 'spring @Service' , 使用原因: 避免与dubbo @Service冲突
        @Service(interfaceClass = xxxxxxService.class) :该 service 为dubbo service
        
    2. application 需要添加Dubbo配置 @EnableDubboConfiguration
    
    3. 微服务模式下 dubbo.protocol端口不能重复,否则启动报错
    
    4. 库存回退和扣减均存在并发问题,解决:
        方案1: 数据库乐观锁
        方案2: MQ+数据库乐观锁
        
    5. 4中解决方案2存在库存不足时,需要通过MQ异步通知订单服务问题
