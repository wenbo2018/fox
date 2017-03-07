package com.fox.rpc;

import com.fox.rpc.registry.Registry;

import java.util.Properties;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class RedisRegistry implements Registry {

    @Override
    public void init(Properties properties) {

    }

    @Override
    public void registerService(String serviceName, String serviceAddress) {

    }

    @Override
    public boolean unregisterService(String serviceName, String serviceAddress) {
        return false;
    }

    @Override
    public String getServiceAddress(String serviceName) {
        return null;
    }
}
