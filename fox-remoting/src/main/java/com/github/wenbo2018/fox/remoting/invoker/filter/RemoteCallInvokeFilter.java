package com.github.wenbo2018.fox.remoting.invoker.filter;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.remoting.invoker.InvokeContext;
import com.github.wenbo2018.fox.remoting.invoker.async.CallbackFuture;
import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import com.github.wenbo2018.fox.remoting.invoker.handler.Filter;
import com.github.wenbo2018.fox.remoting.invoker.api.Client;
import com.github.wenbo2018.fox.remoting.invoker.handler.ServiceInvocationHandler;
import com.github.wenbo2018.fox.remoting.invoker.ClientManager;
import com.github.wenbo2018.fox.remoting.invoker.RemoteServiceCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class RemoteCallInvokeFilter implements Filter {


    private static final Logger logger = LoggerFactory.getLogger(RemoteCallInvokeFilter.class);


    @Override
    public InvokeResponse invoke(ServiceInvocationHandler handler, InvokeContext invokeContext) throws Throwable {
        InvokeRequest request = createInvokeRequest(invokeContext);
        Client client = ClientManager.getInstance().getClient(invokeContext.getInvokerConfig());
        CallbackFuture callback = new CallbackFuture();
        InvokeResponse response = null;
        RemoteServiceCall.requestInvoke(request, callback, client);
        response = callback.get();
        if (response == null) {
            response = callback.get();
        }
        logger.debug("response:{}", response);
        long time = System.currentTimeMillis();
        return response;
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
