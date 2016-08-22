package com.fox.rpc.spring;

import com.fox.rpc.SpiServiceLoader;
import com.fox.rpc.common.util.StringUtil;
import com.fox.rpc.remoting.ServiceFactory;
import com.fox.rpc.remoting.provider.api.Server;
import com.fox.rpc.remoting.provider.config.ProviderCfg;
import com.fox.rpc.server.Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class RemotingServiceRegistry implements ApplicationContextAware{

    private ApplicationContext ctx;

    private  String port;
    private  String serviceAddress;
    public void init() {
        ProviderCfg cfg=new ProviderCfg();
        cfg.setPort(this.port);
        Map<String, Object> handlerMap = new HashMap<>();
        Server server= SpiServiceLoader.newExtension(Server.class);
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(Service.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                Service rpcService = serviceBean.getClass().getAnnotation(Service.class);
                String serviceName = rpcService.value().getName();
                String serviceVersion = rpcService.version();
                if (StringUtil.isNotEmpty(serviceVersion)) {
                    serviceName += "-" + serviceVersion;
                }
                handlerMap.put(serviceName, serviceBean);
            }
        }
        cfg.setHandlerMap(serviceBeanMap);
        ServiceFactory.publishService(cfg,serviceAddress);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx=applicationContext;
    }
}
