package com.github.wenbo2018.fox.remoting;

import com.github.wenbo2018.fox.common.extension.ExtensionServiceLoader;
import com.github.wenbo2018.fox.config.ConfigManagerLoader;
import com.github.wenbo2018.fox.config.ConfigManager;
import com.github.wenbo2018.fox.registry.Constants;
import com.github.wenbo2018.fox.registry.Registry;
import com.github.wenbo2018.fox.registry.RegistryManager;
import com.github.wenbo2018.fox.remoting.invoker.api.ServiceProxy;
import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;
import com.github.wenbo2018.fox.remoting.provider.ProviderBootStrap;
import com.github.wenbo2018.fox.remoting.provider.api.Server;
import com.github.wenbo2018.fox.remoting.provider.config.ProviderConfig;
import com.github.wenbo2018.fox.remoting.provider.config.ServerConfig;
import com.github.wenbo2018.fox.remoting.provider.process.RequestProcessor;
import com.github.wenbo2018.fox.remoting.invoker.proxy.ServiceProxyLoader;
import com.github.wenbo2018.fox.remoting.provider.util.NetUtil;
import com.github.wenbo2018.fox.common.util.CollectionUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shenwenbo on 16/8/6.
 */
public class ServiceFactory {

    private static ConfigManager configManager;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ServiceFactory.class);

    static volatile Map<String, Server> serversMap = new HashMap<String, Server>();

    static ServiceProxy serviceProxy = ServiceProxyLoader.getServiceProxy();

    static {
        try {
            ProviderBootStrap.init();
            configManager = ConfigManagerLoader.getConfigManager();
        } catch (Throwable e) {
            e.printStackTrace();
            LOGGER.error("error while initializing service factory:", e);
            System.exit(1);
        }
    }

    public static <T> T getService(InvokerConfig invokerConfig) {
        return serviceProxy.getProxy(invokerConfig);
    }

    public static void addService(List<ProviderConfig<?>> providerConfigList) {
        publishService(providerConfigList);
        List<Server> servers = ExtensionServiceLoader.getExtensionList(Server.class);
        ServerConfig serverConfig = null;
        if (CollectionUtil.isNotEmpty(providerConfigList)) {
            serverConfig = providerConfigList.get(0).getServerConfig();
            for (Server server : servers) {
                RequestProcessor requestProcessor = null;
                if (!server.isStarted()) {
                    try {
                        requestProcessor = server.star(serverConfig);
                        serversMap.put(server.getPort() + "netty", server);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("server star error:" + serverConfig, e);
                    }
                }
                //将服务添加到线程池服务处理器中;
                for (ProviderConfig config : providerConfigList) {
                    requestProcessor.addService(config);
                }
            }
        } else {
            LOGGER.error("serverConfig is  null");
        }
    }

    public static void startUpServer(ServerConfig serverConfig) {
        String host = configManager.getStringValue(Constants.FOX_REGISTRY_IP);
        if (host==null) {
            host=NetUtil.getHostIp();
        }
        serverConfig.setIp(host);
        List<Server> servers = ExtensionServiceLoader.getExtensionList(Server.class);
        if (serverConfig != null) {
            for (Server server : servers) {
                RequestProcessor requestProcessor = null;
                if (!server.isStarted()) {
                    try {
                        requestProcessor = server.star(serverConfig);
                        serversMap.put(server.getPort() + "netty", server);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("server star error:" + serverConfig, e);
                    }
                }
            }
        } else {
            LOGGER.error("serverConfig  is  null");
        }
    }


    public static void addService(ProviderConfig providerConfig) {
        List<ProviderConfig<?>> providerConfigList = new ArrayList<>();
        providerConfigList.add(providerConfig);
        publishService(providerConfigList);
        List<Server> servers = new ArrayList<>();
        servers.add(serversMap.get(providerConfig.getServerConfig().getPort() + "netty"));
        RequestProcessor requestProcessor = null;
        for (Server server : servers) {
            requestProcessor = server.getRequestProcessor();
            requestProcessor.addService(providerConfig);
        }

    }


    public static void publishService(List<ProviderConfig<?>> providerConfigList) {
        List<Registry> registryList = ExtensionServiceLoader.getExtensionList(Registry.class);
        if (registryList.size() > 0) {
            if (providerConfigList != null) {
                for (ProviderConfig config : providerConfigList) {
                    RegistryManager.getInstance().registerService(config.getServiceName(),
                            config.getServerConfig().getIp() + ":" + config.getServerConfig().getPort());
                    LOGGER.info("register service success:" + config.getServiceName());
                }
            } else {
                LOGGER.error("register center fail");
            }
        }
    }
}
