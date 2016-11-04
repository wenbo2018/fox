package com.fox.rpc.registry;

import java.util.Properties;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public interface Registry {

    void init(Properties properties);

    void registerService(String serviceName, String serviceAddress);

    boolean unregisterService(String serviceName, String serviceAddress);

    String getServiceAddress(String serviceName);

}
