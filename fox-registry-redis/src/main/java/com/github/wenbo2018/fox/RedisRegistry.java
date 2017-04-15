package com.github.wenbo2018.fox;

import com.github.wenbo2018.fox.registry.Registry;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class RedisRegistry implements Registry {

    private static Logger LOGGER = Logger.getLogger(RedisRegistry.class);

    @Override
    public void init(Properties properties) {

    }

    @Override
    public void registerService(String serviceName, String serviceAddress) {

    }

    @Override
    public void unregisterService(String serviceName, String serviceAddress) {
    }

    @Override
    public String getServiceAddress(String serviceName) {
        return null;
    }
}
