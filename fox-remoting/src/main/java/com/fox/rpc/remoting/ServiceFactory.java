package com.fox.rpc.remoting;


import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import com.fox.rpc.remoting.invoker.proxy.ServiceProxy;
import com.fox.rpc.remoting.invoker.proxy.ServiceProxyLoader;
import com.fox.rpc.remoting.provider.config.ProviderConfig;
import com.fox.rpc.remoting.provider.publish.ServicePublisher;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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

    /**
     * 发布服务
     * @param providerConfigList
     */
    public static void addServices(List<ProviderConfig<?>> providerConfigList){
        if (providerConfigList != null && !providerConfigList.isEmpty()) {
                for (ProviderConfig<?> providerConfig : providerConfigList) {
                    /******添加需要发布的服务到容器管理******/
                    ServicePublisher.addService(providerConfig);
                    /***************启动netty服务器******************/

                    /***************将服务注册到zk注册中心************/
                    ServicePublisher.publishService(providerConfig);
                }
        }
    }
}
