package com.fox.rpc.remoting.invoker.proxy;


import com.fox.rpc.remoting.exception.RpcException;
import com.fox.rpc.remoting.invoker.InvokerBootStrap;
import com.fox.rpc.remoting.invoker.api.ServiceProxy;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class DefaultRemotingServiceProxy extends AbstractRemotingServiceProxy implements ServiceProxy {

    @Override
    public <T> T getProxy(InvokerConfig config) {
        return super.getProxy(config);
    }
}
