package com.fox.rpc.registry.listener;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public interface ServiceProviderChangeListener {

    /**
     * 增加服务
     * @param event
     */
    void serviceProviderAdded(ServiceProviderChangeEvent event);

    /**
     * 减少服务
     * @param event
     */
    void serviceProviderRemoved(ServiceProviderChangeEvent event);

}
