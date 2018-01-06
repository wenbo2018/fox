package com.github.wenbo2018.fox.registry.api;

import java.util.Properties;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public interface Registry {

    void init(Properties properties);

    void registerService(String serviceName, String serviceAddress);

    void unregisterService(String serviceName, String serviceAddress) throws Exception;

    String getServiceAddress(String serviceName);

}
