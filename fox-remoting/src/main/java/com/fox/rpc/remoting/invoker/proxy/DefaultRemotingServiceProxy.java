package com.fox.rpc.remoting.invoker.proxy;


import com.fox.rpc.remoting.invoker.api.ServiceProxy;
import com.fox.rpc.remoting.invoker.config.InvokerCfg;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;

import java.lang.reflect.InvocationHandler;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class DefaultRemotingServiceProxy extends AbstractRemotingServiceProxy implements ServiceProxy {

    @Override
    public <T> T getProxy(InvokerConfig invokerConfig) {
        return super.getProxy(invokerConfig);
    }
}
