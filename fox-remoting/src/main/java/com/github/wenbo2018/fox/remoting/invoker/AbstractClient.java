package com.github.wenbo2018.fox.remoting.invoker;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.remoting.invoker.api.CallFuture;
import com.github.wenbo2018.fox.remoting.invoker.async.CallbackFuture;
import com.github.wenbo2018.fox.remoting.invoker.process.ResponseProcessor;
import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import com.github.wenbo2018.fox.remoting.invoker.api.Client;
import com.github.wenbo2018.fox.remoting.provider.process.RequestProcessorFactory;
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

    protected abstract void doClose();

    @Override
    public void connect() {
        this.doConnect();
    }

    @Override
    public void close() {
        this.doClose();
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
