package com.github.wenbo2018.fox.demo.api;

import com.github.wenbo2018.fox.demo.api.dto.User;

/**
 * Hello world!
 */
public interface HelloService {

    public String hello(String hello,String hello2);

    public User changeUser(User user);

    String incer();
}
