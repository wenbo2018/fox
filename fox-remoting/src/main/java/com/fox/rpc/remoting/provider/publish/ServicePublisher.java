package com.fox.rpc.remoting.provider.publish;

import com.fox.rpc.registry.ServiceRegistry;
import com.fox.rpc.remoting.provider.config.ProviderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 16/8/7.
 */
public class ServicePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicePublisher.class);

    private static String serviceAddress;

    private static ServiceRegistry serviceRegistry;

    /****获取本机ip,并在ip:4080地址上发布服务*****/
    static {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serviceAddress=addr.getHostAddress().toString()+":4080";
    }

    /**
     * 存放服务名 与 服务对象 之间的映射关系
     */
    private Map<String, Object> handlerMap = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, ProviderConfig<?>> serviceCache = new ConcurrentHashMap<String, ProviderConfig<?>>();


    public static <T> void addService(ProviderConfig<T> providerConfig) {
        String url = providerConfig.getUrl();
        if (serviceCache.containsKey(url)) {
        } else {
            // use this service as the default provider
            serviceCache.put(url, providerConfig);
        }
    }

    /**
     * 将服务注册到zk
     * @param providerConfig
     * @param <T>
     */
    public static <T> void publishService(ProviderConfig<T> providerConfig) {
        // 注册 RPC 服务地址
        if (serviceRegistry != null) {
            for (String interfaceName : serviceCache.keySet()) {
                serviceRegistry.register(interfaceName, serviceAddress);
                LOGGER.debug("register service: {} => {}", interfaceName, serviceAddress);
            }
        }
    }
}
