package com.fox.rpc.remoting.invoker;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.async.Callback;
import com.fox.rpc.remoting.invoker.async.RemoteInvocationBean;
import com.fox.rpc.common.bean.InvokeRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 2017/3/25.
 */
public class ServiceInvocationRepository {

    private static ServiceInvocationRepository instance = new ServiceInvocationRepository();
    private static Map<String, RemoteInvocationBean> invocations = new ConcurrentHashMap<String, RemoteInvocationBean>();
    public static ServiceInvocationRepository getInstance() {
        return instance;
    }
    public void processResponse(InvokeResponse response) {
        RemoteInvocationBean invocationBean = invocations.get(response.getRequestId());
        if (invocationBean != null) {
            InvokeRequest request = invocationBean.request;
            try {
                Callback callback = invocationBean.callback;
                if (callback != null) {
                    callback.callback(response);
                    callback.run();
                }
            } finally {
                invocations.remove(response.getRequestId());
            }
        }
    }
    public void put(String requestId, RemoteInvocationBean invocation) {
        invocations.put(requestId, invocation);
    }

}
