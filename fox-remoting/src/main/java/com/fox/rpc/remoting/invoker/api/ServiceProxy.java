package com.fox.rpc.remoting.invoker.api;


import com.fox.rpc.remoting.invoker.config.InvokerConfig;

/**
 * Created by shenwenbo on 16/8/6.
 */
public interface ServiceProxy {

    public void init();

    public <T> T getProxy(InvokerConfig invokerConfig);
}
