package com.fox.rpc.remoting.invoker.filter;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.InvokeContext;
import com.fox.rpc.remoting.invoker.async.CallbackFuture;
import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.remoting.invoker.handler.Filter;
import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.handler.ServiceInvocationHandler;
import com.fox.rpc.remoting.invoker.ClientManager;
import com.fox.rpc.remoting.invoker.RemoteServiceCall;

import java.util.UUID;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class RemoteCallInvokeFilter implements Filter {


    @Override
    public InvokeResponse invoke(ServiceInvocationHandler handler, InvokeContext invokeContext) throws Throwable {
        InvokeRequest request = createInvokeRequest(invokeContext);
        Client client = ClientManager.getInstance().getClient(invokeContext.getInvokerConfig());
        CallbackFuture callback = new CallbackFuture();
        InvokeResponse response=null;
        RemoteServiceCall.requestInvoke(request,callback,client);
        response = client.send(request, callback);
        if (response == null) {
            response = callback.get();
        }
        long time = System.currentTimeMillis();
        if (response == null) {
            throw new RuntimeException("response is null");
        }
        // 返回 RPC 响应结果
        if (response.hasException()) {
            throw response.getException();
        } else {
            return response;
        }
    }

    /***
     * 封装RPC请求
     * @param ic
     * @return
     */
    private InvokeRequest createInvokeRequest(InvokeContext ic) {
        InvokeRequest request = new InvokeRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterfaceName(ic.getMethodName());
        request.setServiceVersion(ic.getInvokerConfig().getServiceVersion());
        request.setMethodName(ic.getMethodName());
        request.setParameterTypes(ic.getParameterTypes());
        request.setParameters(ic.getArguments());
        request.setServiceName(ic.getInvokerConfig().getServiceName());
        request.setSerialize(ic.getInvokerConfig().getSerializer());
        request.setCreateMillisTime(System.currentTimeMillis());
        return request;
    }
}
