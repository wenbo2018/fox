package com.fox.rpc.remoting.invoker.api;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;

/**
 * Created by shenwenbo on 16/8/19.
 */
public interface Client {

    public InvokeResponse send(InvokeRequest request) throws Exception;

    public void setContext(String host, int port);

}
