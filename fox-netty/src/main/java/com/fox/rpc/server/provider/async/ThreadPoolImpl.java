package com.fox.rpc.server.provider.async;


import com.fox.rpc.remoting.provider.api.ThreadPool;

/**
 * Created by shenwenbo on 16/8/24.
 */
public class ThreadPoolImpl implements ThreadPool {

    @Override
    public void init() {
        AsyncServiceExecutor.init();
    }
}
