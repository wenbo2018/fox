package com.fox.rpc.remoting;

import com.fox.rpc.InvokerConfig;
import com.fox.rpc.ServiceProxy;
import com.fox.rpc.ServiceProxyLoader;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class ServiceFactory {

    static ServiceProxy serviceProxy = ServiceProxyLoader.getServiceProxy();

    public static <T> T getService(InvokerConfig<T> invokerConfig)  {
        return serviceProxy.getProxy(invokerConfig);
    }

}
