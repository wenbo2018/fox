package com.github.wenbo2018.fox.demo.server;


import com.github.wenbo2018.fox.config.api.HelloService;
import com.github.wenbo2018.fox.config.api.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class HelloServiceImpl implements HelloService {


    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(String hello, String hello2) {
        String re = hello + hello2;
        return re;
    }

    @Override
    public User changeUser(User user) {
        user.setUsername(user.getUsername() + "-rpc test-" + new Date().toString());
        user.setPassword(user.getPassword() + "-rpc test-" + new Date().toString());
        return user;
    }

    @Override
    public String incer() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.valueOf(System.currentTimeMillis());
    }

    @Override
    public int count(int cur) {
        int re = cur*100;
        return re;
    }
}
