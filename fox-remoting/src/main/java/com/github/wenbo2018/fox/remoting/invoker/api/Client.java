package com.github.wenbo2018.fox.remoting.invoker.api;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.remoting.invoker.async.CallbackFuture;
import com.github.wenbo2018.fox.common.bean.InvokeRequest;

/**
 * Created by shenwenbo on 16/8/19.
 */
public interface Client {

    void connect();

    void close();

    void reConnect();

    boolean isAlive();

    CallFuture send(InvokeRequest request) throws Exception;

    InvokeResponse send(InvokeRequest request, CallbackFuture callbackFuture) throws Exception;

    void setContext(String host, int port);

    void processResponse(InvokeResponse invokeResponse);

    InvokeResponse getResponse();

    String getAdress();
}
