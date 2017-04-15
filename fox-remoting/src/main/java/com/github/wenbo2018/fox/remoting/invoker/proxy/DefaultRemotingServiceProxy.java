package com.github.wenbo2018.fox.remoting.invoker.proxy;


import com.github.wenbo2018.fox.remoting.invoker.api.ServiceProxy;
import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class DefaultRemotingServiceProxy extends AbstractRemotingServiceProxy implements ServiceProxy {

    @Override
    public <T> T getProxy(InvokerConfig config) {
        return super.getProxy(config);
    }
}
