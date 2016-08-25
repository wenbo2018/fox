package com.dianping;


/**
 * Created by shenwenbo on 16/8/23.
 */
public class HelloServiceImpl implements HelloService {
    public String hello(String hello) {
        return hello+"1";
    }
}
