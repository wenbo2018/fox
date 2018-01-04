package com.github.wenbo2018.fox.config;

import com.github.wenbo2018.fox.common.extension.ExtensionServiceLoader;
import org.apache.log4j.Logger;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public class ConfigManagerLoader {

    private static ConfigManager configManager = ExtensionServiceLoader.getExtension(ConfigManager.class);

    private static final Logger logger = Logger.getLogger(ConfigManagerLoader.class);

    public static void init() {
        if (configManager == null) {
            configManager = new PropertiesFileConfigManager();
        }
        logger.info("config manager init");
        configManager.init();
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

}
