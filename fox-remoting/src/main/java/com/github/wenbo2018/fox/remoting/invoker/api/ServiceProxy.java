package com.github.wenbo2018.fox.remoting.invoker.api;


import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;

/**
 * Created by shenwenbo on 16/8/6.
 */
public interface ServiceProxy {

    void init();

    <T> T getProxy(InvokerConfig invokerConfig);
}
