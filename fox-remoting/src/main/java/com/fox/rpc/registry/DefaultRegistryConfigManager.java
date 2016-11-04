package com.fox.rpc.registry;

import com.fox.rpc.config.ConfigManager;
import com.fox.rpc.config.ConfigManagerLoader;

import java.util.Properties;

/**
 * Created by shenwenbo on 2016/11/4.
 */
public class DefaultRegistryConfigManager implements RegistryConfigManager {

    @Override
    public Properties getRegistryConfig() {

        ConfigManager configManager = ConfigManagerLoader.getConfigManager();

        String registryType = configManager.getStringValue(Constants.KEY_REGISTRY_TYPE, Constants.DEFAULT_REGISTRY_TYPE);
        String registryAddr = configManager.getStringValue(Constants.KEY_REGISTRY_ADDRESS, "");

        Properties properties = new Properties();
        properties.put(Constants.KEY_REGISTRY_TYPE, registryType);
        properties.put(Constants.KEY_REGISTRY_ADDRESS, registryAddr);

        return properties;
    }
}
