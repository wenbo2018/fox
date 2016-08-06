package com.fox.rpc.client;

/**
 * Created by shenwenbo on 16/8/6.
 */
public interface ServiceProxy {

    public void init();

    public <T> T getProxy(InvokerConfig<T> invokerConfig);
}
