package com.fox.rpc.remoting.provider.process;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.remoting.common.Constants;
import com.fox.rpc.remoting.provider.async.AsyncServiceRunnable;
import com.fox.rpc.remoting.provider.config.ProviderConfig;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public class ThreadPoolRequestProcessor<T> implements RequestProcessor<T>{

    private static int MAX_QUEUE_SIZE;
    private static int CORE_POOL_SIZE;
    private static int MAX_POOL_SIZE;

    private static volatile ThreadPoolExecutor executorService = null;

    private static ConcurrentHashMap<String,ProviderConfig>  cacheServices=new ConcurrentHashMap<String,ProviderConfig>();

    public ThreadPoolRequestProcessor () {
        if(executorService == null){
            if(executorService == null){
                executorService = new ThreadPoolExecutor(5, 5, 600L, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(65536));
            }
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void processRequest(InvokeRequest invokeRequest, ServiceProviderChannel channel) {

        AsyncServiceRunnable asyncServiceRunnable=new
                AsyncServiceRunnable(channel,invokeRequest,getServiceConfig(invokeRequest));
        executorService.submit(asyncServiceRunnable);
    }

    @Override
    public void addService(ProviderConfig<T> providerConfig) {
        cacheServices.put(providerConfig.getServiceName(),providerConfig);
    }


    @Override
    public void removeService(ProviderConfig<T> serviceProviderConfig) {

    }

    private ProviderConfig getServiceConfig(InvokeRequest invokeRequest) {
        if (invokeRequest.getMessageType()== com.fox.rpc.common.common.Constants.MESSAGE_TYPE_HEART)
            return null;
        return  cacheServices.get(invokeRequest.getServiceName());
    }
}
