package com.github.wenbo2018.fox.remoting.invoker;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.remoting.invoker.async.CallbackFuture;
import com.github.wenbo2018.fox.remoting.invoker.async.RemoteInvocationBean;
import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import com.github.wenbo2018.fox.remoting.invoker.api.Client;

/**
 * Created by shenwenbo on 2017/3/26.
 */
public class RemoteServiceCall {

    public static InvokeResponse requestInvoke(InvokeRequest request, CallbackFuture callback, Client client) throws Exception {
        InvokeResponse response = null;
        RemoteInvocationBean invocationBean = new RemoteInvocationBean();
        invocationBean.request = request;
        invocationBean.callback = callback;
        callback.setRequest(request);
        ServiceInvocationRepository.getInstance().put(request.getRequestId(), invocationBean);
        response = client.send(request, callback);
        return response;
    }
}
