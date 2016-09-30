package com.fox.rpc.spring;

import com.fox.rpc.RemotingService;
import com.fox.rpc.SpiServiceLoader;
import com.fox.rpc.common.util.StringUtil;
import com.fox.rpc.registry.RegisterCfg;
import com.fox.rpc.remoting.ServiceFactory;
import com.fox.rpc.remoting.provider.api.ThreadPool;
import com.fox.rpc.remoting.provider.config.ServiceProviderConfig;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class RemotingServiceRegistry implements ApplicationContextAware{

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceFactory.class);

    /**
     * 保存服务信息
     */
    private Map<String,Object> services;
    /**
     * 获取Spring上下文信息
     */
    private ApplicationContext ctx;

    /***
     * 服务发布端口号
     */
    private  String servicePort;
    /**
     * 服务发布地址
     */
    private  String serviceAddress;
    /**
     * 服务注册地址
     */
    private  String registryAddress;
    /**
     * 服务注册端口号
     */
    private  String registryPort;


    public void init() {
        LOGGER.debug("service begin");
        Map<String, Object> handlerMap=new HashMap<String,Object>();
        //如果没有使用注解,那么采用Spring配置进行发布服务
        if (services!=null) {
            for (String url : services.keySet()) {
               handlerMap.put(url,services.get(url));
            }
        } else {
            handlerMap = handlerServiceBeans();
        }

        //封装服务提供信息
        ServiceProviderConfig providerCfg=new ServiceProviderConfig();
        providerCfg.setServicePort(this.servicePort);
        providerCfg.setServiceAddress(this.serviceAddress);
        providerCfg.setHandlerMap(handlerMap);
        providerCfg.setRemotingServiceRegistry(SpiServiceLoader.getExtension(com.fox.rpc.registry.RemotingServiceRegistry.class));
        //封装注册信息
        RegisterCfg registerCfg=new RegisterCfg();
        registerCfg.setPort(this.registryPort);
        registerCfg.setAddress(this.registryAddress);
        registerCfg.setHandlerMap(handlerMap);

        ThreadPool threadPool=SpiServiceLoader.newExtension(com.fox.rpc.remoting.provider.api.ThreadPool.class);

        threadPool.init();
        // 将服务注册到注册中心
//        ServiceFactory.registryService(registerCfg,providerCfg);
//        //启动服务器
//        ServiceFactory.publishService(providerCfg);
    }


    private Map<String, Object> handlerServiceBeans() {
        Map<String, Object> handlerMap = new HashMap<String, Object>();
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RemotingService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                RemotingService rpcService = serviceBean.getClass().getAnnotation(RemotingService.class);
                String serviceName = rpcService.value().getName();
                String serviceVersion = rpcService.version();
                if (StringUtil.isNotEmpty(serviceVersion)) {
                    serviceName += "-" + serviceVersion;
                }
                handlerMap.put(serviceName, serviceBean);
            }
        }
        if (handlerMap==null||handlerMap.size()==0) {
            LOGGER.error("service bean is not found");
        }
        return handlerMap;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx=applicationContext;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public ApplicationContext getCtx() {
        return ctx;
    }

    public void setCtx(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    public String getRegistryPort() {
        return registryPort;
    }

    public void setRegistryPort(String registryPort) {
        this.registryPort = registryPort;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public Map<String, Object> getServices() {
        return services;
    }

    public void setServices(Map<String, Object> services) {
        this.services = services;
    }
}
