金融场景微服务模式订单支付系统

    复现常规业务流程：资料搜集、学习、复现项目

    环境配置、搭建Zookeeper、RocketMQ集群（2m-2s，同步双写）

    新增并优化登录功能：两次MD5加密-前端MD5加密(防止用户密码在网络中明文传输)，后端MD5加密(提高密码安全性)，Redis实现分布式Session

    库存扣减方案优化： 低并发场景--数据库乐观锁                                       
    高并发场景--Redis预减库存，Lua脚本保证操作库存原子性，通过MQ真正扣减库存

    在支付与订单系统中增加秒杀功能：利用Redis缓存秒杀信息；秒杀接口地址隐藏，先GET获得秒杀地址，通过Redis校验；通过自定义并注册拦截器类，配合Redis实现接口限流；RocketMQ实现MySQL与Redis的数据同步

    根据前端项目的需求，设计并实现对应的接口：登录、显示商品、创建订单、支付、查询订单支付状态
通过配置代理，解决跨域问题（前端）

    登录、显示商品、创建订单、支付、查询订单支付状态完成支付等功能的api设计、实现与路由跳转（前端）


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


