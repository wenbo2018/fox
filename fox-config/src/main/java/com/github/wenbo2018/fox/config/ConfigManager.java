package com.github.wenbo2018.fox.config;

import java.util.Properties;

/**
 * Created by shenwenbo on 2016/10/4.
 */
public interface ConfigManager {

    void init();

    void init(Properties properties);

    Properties getRegistryConfig();

    String getStringValue(String key, String defaultValue);

    String getStringValue(String key);
}
