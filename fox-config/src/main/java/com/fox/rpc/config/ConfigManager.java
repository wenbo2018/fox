package com.fox.rpc.config;

import java.util.Properties;

/**
 * Created by shenwenbo on 2016/10/4.
 */
public interface ConfigManager {

    public void init();

    public void init(Properties properties);

    public Properties getRegistryConfig();

    public String getStringValue(String key, String defaultValue);

    public String getStringValue(String key);
}
