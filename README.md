# fox
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://github.com/wenbo2018/fox/)


fox is a distributed, lightweight RPC framework which empowers applications with service import/export capabilities.

It contains three key parts, which include:

* **Remoting**: a network communication framework providing request-response messaging.

* **Registration**: a service directory framework for service registration and service event publish/subscription



## Quick Config

You only need to configure your zookeeper address in the appkeys.properties:

appkeys.properties file address:

```xml
/data/app/appkeys.properties(Linux)

C:/data/app/appkeys.properties(WIN)

```

### Configuration format:

```xml
fox.registry.adress="zk server ip"

fox.registry.type=zookeeper

fox.registry.ip="your rpc server ip"

```

## Quick Start

### Configurations1:

#### Service invocation

```xml
         <bean id="helloService" class="com.github.wenbo2018.fox.spring.config.ServiceProxy" init-method="init">
            <property name="iface" value="com.github.wenbo2018.fox.demo.api"/>
            <property name="serviceName" value="service.fox.com_helloTestService_helloService_1.0.0"/>
            <property name="serializer" value="protostuff"/>
         </bean>
```
#### Publish service

```xml
        <bean id="helloService" class="com.github.wenbo2018.fox.demo.server.HelloServiceImpl"/>

        <bean id="helloTestService" class="com.github.wenbo2018.fox.spring.config.ServiceRegister" init-method="init">
            <property name="port" value="4080"/>
            <property name="services" >
                <map>
                    <entry key="service.fox.com_helloTestService_helloService_1.0.0" value-ref="helloService"/>
                </map>
            </property>
        </bean>
```

### Configurations2:

#### Service invocation

```xml
<fox:invoker id="helloService"
                 iface="com.github.wenbo2018.fox.demo.api.HelloService"
            serviceName="service.fox.com_helloTestService_helloService_1.0.0"
            serializer="hessian"/>
```

#### Publish service

```xml
    <bean id="helloService" class="com.github.wenbo2018.fox.demo.server.HelloServiceImpl"/>
    <bean id="userService" class="com.github.wenbo2018.fox.demo.server.UserServiceImpl"/>
    
    <fox:server  id="server1" port="4019"/>
    <fox:service server="server1"
                 serviceName="service.fox.com_helloTestService_helloService_1.0.0"
                 ref="helloService"/>
    <fox:service server="server1"
                 serviceName="service.fox.com_helloTestService_userService_1.0.0"
                 ref="userService"/>
```

