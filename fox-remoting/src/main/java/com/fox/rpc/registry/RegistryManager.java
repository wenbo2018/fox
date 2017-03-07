package com.fox.rpc.registry;

import com.fox.rpc.common.extension.UserServiceLoader;
import com.fox.rpc.config.ConfigManager;
import com.fox.rpc.config.ConfigManagerLoader;
import java.util.List;
import java.util.Properties;

/**
 * Created by shenwenbo on 2016/10/27.
 */
public class RegistryManager {

    private static RegistryManager instance = new RegistryManager();

    private static ConfigManager configManager = ConfigManagerLoader.getConfigManager();

    private static RegistryConfigManager registryConfigManager = new DefaultRegistryConfigManager();

    private static volatile boolean isInit = false;


    public static RegistryManager getInstance() {
        if (!isInit) {
            synchronized (RegistryManager.class) {
                if (!isInit) {
                    instance.init(registryConfigManager.getRegistryConfig());
                    isInit = true;
                }
            }
        }
        return instance;
    }

    public static void init(Properties properties) {
        List<Registry> registryList = UserServiceLoader.getExtensionList(Registry.class);
        if (registryList.size() > 0) {
            for (Registry registry : registryList) {
                registry.init(properties);
            }
        }
    }
}
