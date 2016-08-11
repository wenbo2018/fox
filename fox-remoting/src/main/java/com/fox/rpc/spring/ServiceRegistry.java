package com.fox.rpc.spring;

import com.fox.rpc.remoting.ServiceFactory;
import com.fox.rpc.remoting.provider.config.ProviderConfig;
import com.fox.rpc.remoting.provider.config.ServerConfig;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class ServiceRegistry {
    /*****配置需要发布的服务******/
    private Map<String, Object> services;
    /**发布端口***/
    private String port;

    public void init() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(port);
        List<ProviderConfig<?>> providerConfigList = new ArrayList<ProviderConfig<?>>();
        for (String url : services.keySet()) {
            ProviderConfig<Object> providerConfig = new ProviderConfig<Object>();
            //设置服务地址
            providerConfig.setUrl(url);
            //设置服务接口Class信息
            providerConfig.setServiceInterface(getServiceInterface(services.get(url).getClass()));
            //配置发布服务服务器信息,端口号等。
            providerConfig.setServerConfig(serverConfig);
            providerConfigList.add(providerConfig);
        }
        ServiceFactory.addServices(providerConfigList);
    }


    public <T> Class<?> getServiceInterface(Class<?> type) {
        Class<?> interfaceClass = null;

        return interfaceClass;
    }

}
