package com.fox.rpc.remoting.invoker.proxy;

import com.fox.rpc.*;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class DefaultServiceProxy extends AbstractServiceProxy implements ServiceProxy {

    @Override
    public <T> T getProxy(InvokerConfig<T> invokerConfig) {
        return super.getProxy(invokerConfig);
    }
}
