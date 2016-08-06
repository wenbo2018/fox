package com.fox.rpc.client;

import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class ServiceProxyLoader {
    private static ServiceProxy serviceProxy = ExtensionLoader.getExtension(ServiceProxy.class);
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ServiceProxy.class);

    static {
        if (serviceProxy == null) {
            serviceProxy = new DefaultServiceProxy();
        }
        LOGGER.info("serviceProxy:" + serviceProxy);
        serviceProxy.init();
    }

    public static ServiceProxy getServiceProxy() {
        return serviceProxy;
    }
}
