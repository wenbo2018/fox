package com.fox.rpc.remoting.invoker.proxy;

import com.fox.rpc.InvokerConfig;

/**
 * Created by shenwenbo on 16/8/6.
 */
public interface ServiceProxy {

    public void init();

    public <T> T getProxy(InvokerConfig<T> invokerConfig);
}
