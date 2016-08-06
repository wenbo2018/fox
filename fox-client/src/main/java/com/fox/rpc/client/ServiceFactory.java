package com.fox.rpc.client;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class ServiceFactory {

    static ServiceProxy serviceProxy = ServiceProxyLoader.getServiceProxy();

    public static <T> T getService(InvokerConfig<T> invokerConfig)  {
        return serviceProxy.getProxy(invokerConfig);
    }

}
