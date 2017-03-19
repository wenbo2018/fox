package com.fox;


import com.fox.dto.User;

import java.util.Date;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class HelloServiceImpl implements HelloService {


    public String hello(String hello,String hello2) {
        String re=hello+hello2;
        return re;
    }

    @Override
    public User changeUser(User user) {
        user.setUsername(user.getUsername()+"-rpc test-"+new Date().toString());
        user.setPassword(user.getPassword()+"-rpc test-"+new Date().toString());
        return user;
    }

    @Override
    public String incer() {
        return String.valueOf(System.currentTimeMillis());
    }
}
