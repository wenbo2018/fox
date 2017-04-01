package com.fox.rpc.remoting.invoker.process;

import com.fox.rpc.remoting.provider.config.ProviderConfig;
import com.fox.rpc.remoting.provider.process.ServiceProviderChannel;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.ServiceInvocationRepository;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public class ThreadPoolResponseProcessor implements ResponseProcessor {

    private static int MAX_QUEUE_SIZE;
    private static int CORE_POOL_SIZE;
    private static int MAX_POOL_SIZE;

    private volatile ThreadPoolExecutor executorService = null;

    private static ConcurrentHashMap<String,ProviderConfig>  cacheServices=new ConcurrentHashMap<String,ProviderConfig>();

    public ThreadPoolResponseProcessor() {
        if(executorService == null){
            if(executorService == null){
                executorService = new ThreadPoolExecutor(5, 5, 600L, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(65536));
            }
        }
    }

    @Override
    public void processRequest(final InvokeResponse invokeResponse, ServiceProviderChannel channel) {
        Runnable task=new Runnable() {
            @Override
            public void run() {
                ServiceInvocationRepository.getInstance().processResponse(invokeResponse);
            }
        };
        executorService.submit(task);
    }
}
