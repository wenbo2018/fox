package com.fox.rpc.remoting.invoker.api;


import com.fox.rpc.remoting.invoker.config.InvokerCfg;

/**
 * Created by shenwenbo on 16/8/6.
 */
public interface ServiceProxy {

    public void init();

    public <T> T getProxy(InvokerCfg<T> invokerConfig);
}
