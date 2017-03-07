package com.fox.rpc.remoting;

import com.fox.rpc.common.extension.UserServiceLoader;
import com.fox.rpc.registry.Registry;
import com.fox.rpc.remoting.invoker.api.ServiceProxy;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import com.fox.rpc.remoting.invoker.proxy.ServiceProxyLoader;
import com.fox.rpc.remoting.provider.ProviderBootStrap;
import com.fox.rpc.remoting.provider.api.Server;
import com.fox.rpc.remoting.provider.config.ProviderConfig;
import com.fox.rpc.remoting.provider.config.ServerConfig;
import com.fox.rpc.remoting.provider.process.RequestProcessor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;


/**
 * Created by shenwenbo on 16/8/6.
 */
public class ServiceFactory {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ServiceFactory.class);

    static ServiceProxy serviceProxy = ServiceProxyLoader.getServiceProxy();

    public static <T> T getService(InvokerConfig invokerConfig) {
        return serviceProxy.getProxy(invokerConfig);
    }

    static {
        try {
            ProviderBootStrap.init();
        } catch (Throwable e) {
            e.printStackTrace();
            LOGGER.error("error while initializing service factory:", e);
            System.exit(1);
        }
    }

    public static void addService(List<ProviderConfig<?>> providerConfigList) {

        publishService(providerConfigList);
        List<Server> servers = UserServiceLoader.getExtensionList(Server.class);
        ServerConfig serverConfig = null;
        if (CollectionUtils.isNotEmpty(providerConfigList)) {
            serverConfig = providerConfigList.get(0).getServerConfig();
            for (Server server : servers) {
                RequestProcessor requestProcessor = null;
                if (!server.isStarted()) {
                    try {
                        requestProcessor = server.star(serverConfig);
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

    public static void publishService(List<ProviderConfig<?>> providerConfigList) {
        //RemotingServiceRegistry remotingServiceRegistry = UserServiceLoader.newExtension(RemotingServiceRegistry.class);
        List<Registry> registryList = UserServiceLoader.getExtensionList(Registry.class);
        if (registryList.size() > 0) {
            for (Registry registry : registryList) {
                if (providerConfigList != null) {
                    for (ProviderConfig config : providerConfigList) {
                        registry.registerService(config.getServiceName(),
                                config.getServerConfig().getIp() + ":" + config.getServerConfig().getPort());
                        LOGGER.debug("register service:" + config.getServiceName());
                    }
                } else {
                    LOGGER.error("register center fail");
                }
            }
        }


    }
}
