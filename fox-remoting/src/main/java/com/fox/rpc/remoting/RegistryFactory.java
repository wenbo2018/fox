package com.fox.rpc.remoting;

import com.fox.rpc.SpiServiceLoader;
import com.fox.rpc.registry.RemotingServiceDiscovery;
import com.fox.rpc.registry.RemotingServiceRegistry;


/**
 * Created by shenwenbo on 16/8/22.
 */
public class RegistryFactory {


    static RemotingServiceDiscovery remotingServiceDiscovery = SpiServiceLoader.newExtension(RemotingServiceDiscovery.class);

    static RemotingServiceRegistry remotingServiceRegistry=SpiServiceLoader.newExtension(RemotingServiceRegistry.class);

    public static <T> T getServiceDiscovery()  {
        return (T) remotingServiceDiscovery;
    }

    public static <T> T getServiceRegistry()  {
        return (T) remotingServiceRegistry;
    }

}
