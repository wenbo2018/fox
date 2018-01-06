package com.github.wenbo2018.fox.config.api;

import com.github.wenbo2018.fox.config.api.dto.User;

/**
 * Hello world!
 */
public interface HelloService {

    public String hello(String hello, String hello2);

    public User changeUser(User user);

    String incer();

    int count(int cur);
}
