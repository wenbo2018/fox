package com.fox.rpc.remoting.invoker.proxy;

import com.fox.rpc.SpiServiceLoader;
import com.fox.rpc.remoting.invoker.api.ServiceProxy;
import org.slf4j.LoggerFactory;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class ServiceProxyLoader {

    private static ServiceProxy serviceProxy = SpiServiceLoader.getExtension(ServiceProxy.class);

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ServiceProxy.class);

    static {
        if (serviceProxy == null) {
            serviceProxy = new DefaultRemotingServiceProxy();
        }
        LOGGER.info("serviceProxy:" + serviceProxy);
        serviceProxy.init();
    }

    public static ServiceProxy getServiceProxy() {
        return serviceProxy;
    }
}
