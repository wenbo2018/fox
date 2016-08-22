package com.fox.rpc.remoting;

import com.fox.rpc.SpiServiceLoader;
import com.fox.rpc.registry.RemotingServiceRegistry;
import com.fox.rpc.remoting.invoker.api.ServiceProxy;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import com.fox.rpc.remoting.invoker.proxy.ServiceProxyLoader;
import com.fox.rpc.remoting.provider.api.Server;
import com.fox.rpc.remoting.provider.config.ProviderCfg;


/**
 * Created by shenwenbo on 16/8/6.
 */
public class ServiceFactory {

    static ServiceProxy serviceProxy = ServiceProxyLoader.getServiceProxy();
    /**
     * 获取服务
     * @param invokerConfig
     * @param <T>
     * @return
     */
    public static <T> T getService(InvokerConfig<T> invokerConfig)  {
        return serviceProxy.getProxy(invokerConfig);
    }

    public static void publishService(ProviderCfg cfg,String serviceAddress) {
        Server server= SpiServiceLoader.newExtension(Server.class);
        server.setContext(SpiServiceLoader.getExtension(RemotingServiceRegistry.class)
                ,cfg.getHandlerMap(),serviceAddress);
        try {
            server.star();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
