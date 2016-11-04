package com.fox.rpc.remoting.common;

import com.fox.rpc.UserServiceLoader;
import com.fox.rpc.registry.RemotingServiceDiscovery;
import com.fox.rpc.registry.RemotingServiceRegistry;
import org.apache.log4j.Logger;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public class RegisterFactory {
    static Logger LOGGER=Logger.getLogger(RegisterFactory.class);
    public static RemotingServiceRegistry  selectRegistrant() {
        RemotingServiceRegistry serviceRegistry= UserServiceLoader.getExtension(RemotingServiceRegistry.class);
        if (serviceRegistry != null) {
            return serviceRegistry;
        } else {
            LOGGER.error("spi load service error:"+"RemotingServiceRegistry");
        }
        return null;
    }

    public static RemotingServiceDiscovery selectDiscovery() {
        RemotingServiceDiscovery  serviceDiscovery= com.fox.rpc.UserServiceLoader.getExtension(RemotingServiceDiscovery.class);
        if (serviceDiscovery != null) {
            return serviceDiscovery;
        } else {
            LOGGER.error("spi load service error:"+"RemotingServiceDiscovery");
        }
        return null;
    }

}
