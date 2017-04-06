package com.fox.rpc.remoting.invoker;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.async.CallbackFuture;
import com.fox.rpc.remoting.invoker.async.RemoteInvocationBean;
import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.remoting.invoker.api.Client;

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
