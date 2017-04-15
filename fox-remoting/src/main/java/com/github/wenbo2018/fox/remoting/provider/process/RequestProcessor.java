package com.github.wenbo2018.fox.remoting.provider.process;

import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import com.github.wenbo2018.fox.remoting.provider.config.ProviderConfig;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public interface RequestProcessor<T> {

    public void start();

    public void processRequest(InvokeRequest invokeRequest, ServiceProviderChannel channel);

    public void addService(ProviderConfig<T> serviceProviderConfig);

    public void removeService(ProviderConfig<T> serviceProviderConfig);

}
