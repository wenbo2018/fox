package com.fox.rpc.remoting.invoker.proxy;

import com.fox.rpc.remoting.invoker.api.ServiceProxy;
import com.fox.rpc.remoting.invoker.config.InvokerCfg;
import com.fox.rpc.remoting.invoker.util.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 16/8/6.
 */
public abstract class AbstractRemotingServiceProxy implements ServiceProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRemotingServiceProxy.class);

    protected static Map<InvokerCfg<?>, Object> services = new ConcurrentHashMap<InvokerCfg<?>, Object>();

    @Override
    public void init() {
    }

    @Override
    public <T> T getProxy(InvokerCfg<T> cfg) {
        Object service = null;
        service=services.get(cfg);
        if (service==null) {
            try {
                ServiceInvocationProxy serviceInvocationProxy=new ServiceInvocationProxy(cfg);
                service=(T) Proxy.newProxyInstance(cfg.getClass().getClassLoader(),
                        new Class<?>[]{ cfg.getServiceInterface() },serviceInvocationProxy);
            } catch (Throwable e) {
                throw new RpcException("error while trying to invoke service",e);
            }
            services.put(cfg,service);
        }
        return (T) service;
    }
}
