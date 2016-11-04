package com.fox.rpc.registry;

import java.util.Properties;

/**
 * Created by shenwenbo on 2016/10/4.
 */
public interface ConfigManager {

    public void init();

    public void init(Properties properties);

    public Properties getRegistryConfig();
}
