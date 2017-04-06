package com.fox.rpc.remoting.invoker;

import com.fox.rpc.remoting.invoker.process.ResponseProcessor;
import com.fox.rpc.remoting.invoker.api.CallFuture;
import com.fox.rpc.remoting.invoker.async.CallbackFuture;
import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.provider.process.RequestProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shenwenbo on 2017/3/26.
 */
public abstract class AbstractClient implements Client {

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractClient.class);

    ResponseProcessor responseProcessor = RequestProcessorFactory.selectResponseProcessor();

    protected abstract InvokeResponse doWrite(InvokeRequest request, CallbackFuture callbackFuture);

    protected abstract void doConnect();

    @Override
    public void connect() {
        this.doConnect();
    }

    @Override
    public CallFuture send(InvokeRequest request) throws Exception {
        return null;
    }

    @Override
    public InvokeResponse send(InvokeRequest request, CallbackFuture callbackFuture) throws Exception {
        return doWrite(request, callbackFuture);
    }

    @Override
    public void processResponse(InvokeResponse invokeResponse) {
        this.responseProcessor.processRequest(invokeResponse, null);
    }

}
