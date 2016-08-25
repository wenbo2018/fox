#fox
![Gitter](https://badges.gitter.im/Join Chat.svg)
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
            <property name="serviceName" value="http://service.fox.com/helloTestService/helloService_1.0.0"/>
            <property name="zkAddress" value="170.0.0.0:8000"/>
        </bean>

```

when you publish your services,you can just do as following:

```xml
<bean id="registryService" class="com.fox.rpc.spring.RemotingServiceRegistry" init-method="init">
            <property name="servicePort" value="8000"/>
            <property name="serviceAddress" value="170.0.0.0"/>
            <property name="registryAddress" value="170.0.0.0"/>
            <property name="registryPort" value="8000"/>
            <property name="services" >
                <map>
                    <entry key="helloService-01" value-ref="helloService"/>
                </map>
            </property>
        </bean>

```

