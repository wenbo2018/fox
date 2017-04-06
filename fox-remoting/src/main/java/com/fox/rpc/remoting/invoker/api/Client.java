package com.fox.rpc.remoting.invoker.api;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.async.CallbackFuture;
import com.fox.rpc.common.bean.InvokeRequest;

/**
 * Created by shenwenbo on 16/8/19.
 */
public interface Client {

    void connect();

    CallFuture send(InvokeRequest request) throws Exception;

    InvokeResponse send(InvokeRequest request, CallbackFuture callbackFuture) throws Exception;

    void setContext(String host, int port);

    void processResponse(InvokeResponse invokeResponse);

    InvokeResponse getResponse();

    String getAdress();
}
