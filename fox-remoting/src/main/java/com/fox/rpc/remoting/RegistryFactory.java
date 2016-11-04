package com.fox.rpc.remoting;

import com.fox.rpc.common.extension.UserServiceLoader;
import com.fox.rpc.registry.RemotingServiceDiscovery;
import com.fox.rpc.registry.RemotingServiceRegistry;


/**
 * Created by shenwenbo on 16/8/22.
 */
public class RegistryFactory {


    static RemotingServiceDiscovery remotingServiceDiscovery = UserServiceLoader.newExtension(RemotingServiceDiscovery.class);

    static RemotingServiceRegistry remotingServiceRegistry= UserServiceLoader.newExtension(RemotingServiceRegistry.class);

    public static <T> T getServiceDiscovery()  {
        return (T) remotingServiceDiscovery;
    }

    public static <T> T getServiceRegistry()  {
        return (T) remotingServiceRegistry;
    }

}
