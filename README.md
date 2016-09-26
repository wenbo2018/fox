#fox
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://github.com/wenbo2018/fox/)


fox is a distributed, lightweight RPC framework which empowers applications with service import/export capabilities.

It contains three key parts, which include:

* **Remoting**: a network communication framework providing request-response messaging.

* **Registration**: a service directory framework for service registration and service event publish/subscription


## Quick Start
when you need invoke service ,you can just do as following:

```xml
        <bean id="helloService" class="com.fox.rpc.spring.RemotingServiceProxy" init-method="init">
            <property name="iface" value="com.dianping.HelloService"/>
            <property name="serviceName" value="service.fox.com_helloTestService_helloService_1.0.0"/>
            <property name="zkAddress" value="202.38.214.167:2181"/>
        </bean>

```

when you publish your services,you can just do as following:

```xml
        <bean id="helloService" class="com.dianping.HelloServiceImpl"/>

        <bean id="helloTestService" class="com.fox.rpc.spring.RemotingServiceRegistry" init-method="init">
            <property name="servicePort" value="4080"/>
            <property name="serviceAddress" value="202.38.214.167"/>
            <property name="registryAddress" value="202.38.214.167:2181"/>
            <property name="registryPort" value="2181"/>
            <property name="services" >
                <map>
                    <entry key="service.fox.com_helloTestService_helloService_1.0.0" value-ref="helloService"/>
                </map>
            </property>
        </bean>
```

