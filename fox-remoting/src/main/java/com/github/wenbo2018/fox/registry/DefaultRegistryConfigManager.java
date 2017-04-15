package com.github.wenbo2018.fox.registry;

import com.github.wenbo2018.fox.config.ConfigManager;
import com.github.wenbo2018.fox.config.ConfigManagerLoader;

import java.util.Properties;

/**
 * Created by shenwenbo on 2016/11/4.
 */
public class DefaultRegistryConfigManager implements RegistryConfigManager {

    private static ConfigManager configManager = ConfigManagerLoader.getConfigManager();

    @Override
    public Properties getRegistryConfig() {
        String registryType = configManager.getStringValue(Constants.FOX_REGISTRY_TYPE);
        String registryAddr = configManager.getStringValue(Constants.FOX_REGISTRY_ADDRESS);
        Properties properties = new Properties();
        properties.put(Constants.FOX_REGISTRY_TYPE, registryType);
        properties.put(Constants.FOX_REGISTRY_ADDRESS, registryAddr);
        return properties;
    }
}
