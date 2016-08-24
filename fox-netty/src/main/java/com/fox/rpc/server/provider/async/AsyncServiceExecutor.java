package com.fox.rpc.server.provider.async;

import com.fox.rpc.common.bean.InvokeRequest;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenwenbo on 16/8/24.
 */
public class AsyncServiceExecutor {

    private static int MAX_QUEUE_SIZE;
    private static int CORE_POOL_SIZE;
    private static int MAX_POOL_SIZE;
    private static volatile ThreadPoolExecutor executorService = null;

    public static void  init() {
        if(executorService == null){
                if(executorService == null){
                    executorService = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
        }
    }

    public static void  submitCallback(ChannelHandlerContext ctx, InvokeRequest request, Object service) {
        AsyncServiceRunnableExecutor asyncServiceRunnableExecutor=new
                AsyncServiceRunnableExecutor(ctx,request,service);
        executorService.submit(asyncServiceRunnableExecutor);
    }

    public static void init(int corePoolSize, int maxPoolSize, int queueSize) {
        CORE_POOL_SIZE = corePoolSize;
        MAX_POOL_SIZE = maxPoolSize;
        MAX_QUEUE_SIZE = queueSize;
        init();
    }

}
