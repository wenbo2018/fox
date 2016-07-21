package com.xxx.rpc.sample.server;

import com.xxx.rpc.sample.api.HelloService;
import com.xxx.rpc.sample.api.Person;
import com.xxx.rpc.server.RpcService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }

    @Override
    public String hello(Person person) {
        return "Hello! " + person.getFirstName() + " " + person.getLastName();
    }
}
