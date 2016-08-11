fox是一款支持分布式部署的轻量级RPC框架,
========================================================
fox主要用于自己学习设计的,fox参考了业界一些PRC框架设计,包括但不限于dubbo、pigeon等。
---------------------------- ---------- -------
fox架构设计
=========
fox主要由四部分构成,服务消费者,服务提供者,服务监控以及服务注册中心。架构如如下所示:
-----------------------------------
 ![image](https://github.com/wenbo2018/fox/blob/master/fox-framework1.png)

 1.消费者,消费者主要是服务的调用方,消费者只需要依赖服务api接口即可,因此服务api接口需要单独打包;

 2.提供者,提供者是服务的提供者,服务接着远程调用请求再将请求返回到消费者;

 3.服务注册,服务注册提供一个注册中心供消费者查询可调用服务同时供服务提供者发布服务地址,注册中心采用
 zk实现,服务提供者将服务注册到zk上,消费者向zk调阅服务,一旦服务发生变化消费者能自动感知;

 4.监控,服务监控主要是用于监控服务调用情况;


 fox基于Spring配置,如果要使用必须要依赖Spring,
 客户端需要使用fox时只需要在Spring 配置文件按照如下格式配置即可配置:

 <bean id="userService" class="com.fox.rpc.spring.ProxyBeanFactory" init-method="init">
 		<property name="serviceName"
 			value="http://service.fox.com/userService/userService_1.0.0" />
 		<property name="iface"
 			value="com.dianping.combiz.decorator.remote.DecoratorService" />
 </bean>

 使用时直接可以按照使用Spring 普通bean一样直接使用。