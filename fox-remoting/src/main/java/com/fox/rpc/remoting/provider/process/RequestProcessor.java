package com.fox.rpc.remoting.provider.process;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.remoting.provider.config.ProviderConfig;
import com.fox.rpc.remoting.provider.config.ServiceProviderConfig;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public interface RequestProcessor<T> {

    public void start();

    public void processRequest(InvokeRequest invokeRequest,ServiceProviderChannel channel);

    public void addService(ProviderConfig<T> serviceProviderConfig);

    public void removeService(ServiceProviderConfig<T> serviceProviderConfig);

}
