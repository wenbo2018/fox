package com.github.wenbo2018.fox.spring.config;

import com.github.wenbo2018.fox.config.ConfigManager;
import com.github.wenbo2018.fox.config.ConfigManagerLoader;
import com.github.wenbo2018.fox.remoting.common.Constants;
import com.github.wenbo2018.fox.remoting.provider.config.ProviderConfig;
import com.github.wenbo2018.fox.remoting.provider.config.ServerConfig;
import com.github.wenbo2018.fox.remoting.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public class ServiceBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceFactory.class);
    private static ConfigManager configManager;
    private boolean publish = true;
    private Map<String, Object> services;
    private int port = Constants.DEFAULT_PORT;
    private int corePoolSize = Constants.PROVIDER_POOL_CORE_SIZE;
    private int maxPoolSize = Constants.PROVIDER_POOL_MAX_SIZE;
    private int workQueueSize = Constants.PROVIDER_POOL_QUEUE_SIZE;

    public void init() {
        /**初始化服务配置*/
        configManager = ConfigManagerLoader.getConfigManager();
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setCorePoolSize(this.getCorePoolSize());
        serverConfig.setPort(this.getPort());
        serverConfig.setIp(configManager.getStringValue(com.github.wenbo2018.fox.registry.Constants.FOX_REGISTRY_IP));
        serverConfig.setWorkQueueSize(this.getWorkQueueSize());
        serverConfig.setMaxPoolSize(this.getMaxPoolSize());
        LOGGER.info("service begin");
        /**封装服务信息**/
        List<ProviderConfig<?>> providerConfigList = new ArrayList<ProviderConfig<?>>();
        if (services != null) {
            for (String serviceName : services.keySet()) {
                ProviderConfig config = new ProviderConfig(services.get(serviceName));
                config.setServiceName(serviceName);
                config.setServerConfig(serverConfig);
                providerConfigList.add(config);
            }
        }
        ServiceFactory.addService(providerConfigList);
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public Map<String, Object> getServices() {
        return services;
    }

    public void setServices(Map<String, Object> services) {
        this.services = services;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getWorkQueueSize() {
        return workQueueSize;
    }

    public void setWorkQueueSize(int workQueueSize) {
        this.workQueueSize = workQueueSize;
    }
}
