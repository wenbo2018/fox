package com.fox.rpc.remoting;

import com.fox.rpc.SpiServiceLoader;
import com.fox.rpc.registry.RegisterCfg;
import com.fox.rpc.registry.RemotingServiceRegistry;
import com.fox.rpc.remoting.invoker.api.ServiceProxy;
import com.fox.rpc.remoting.invoker.config.InvokerCfg;
import com.fox.rpc.remoting.invoker.proxy.ServiceProxyLoader;
import com.fox.rpc.remoting.provider.api.Server;
import com.fox.rpc.remoting.provider.config.ProviderCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by shenwenbo on 16/8/6.
 */
public class ServiceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceFactory.class);

    static ServiceProxy serviceProxy = ServiceProxyLoader.getServiceProxy();
    /**
     * 获取服务
     * @param invokerConfig
     * @param <T>
     * @return
     */
    public static <T> T getService(InvokerCfg<T> invokerConfig)  {
        return serviceProxy.getProxy(invokerConfig);
    }

    public static void registryService(RegisterCfg registerCfg,ProviderCfg providerCfg) {
        LOGGER.info("begin registery");
        RemotingServiceRegistry remotingServiceRegistry=SpiServiceLoader.newExtension(RemotingServiceRegistry.class);
        remotingServiceRegistry.setContext(registerCfg);
        if (remotingServiceRegistry != null) {
            for (String interfaceName : registerCfg.getHandlerMap().keySet()) {
                remotingServiceRegistry.register(interfaceName,providerCfg.getServiceAddress()+":"+providerCfg.getServicePort());
                LOGGER.debug("register service: {} => {}", interfaceName,providerCfg.getServiceAddress());
            }
        } else {
            LOGGER.error("register center fail");
        }
    }

    public static void publishService(ProviderCfg cfg) {
        Server server= SpiServiceLoader.newExtension(Server.class);
        server.setContext(cfg);
        try {
            server.star();
        } catch (Exception e) {
            System.out.println(e);
            LOGGER.error("Service publish fail",e);
        }
    }
}
