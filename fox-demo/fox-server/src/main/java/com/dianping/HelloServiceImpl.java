package com.dianping;


import com.dianping.dto.User;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class HelloServiceImpl implements HelloService {
    public String hello(String hello) {
        return hello+"1";
    }

    @Override
    public User changeUser(User user) {
        user.setUsername(user.getUsername()+"rpc test");
        user.setPassword(user.getPassword()+"rpc test");
        return user;
    }
}
