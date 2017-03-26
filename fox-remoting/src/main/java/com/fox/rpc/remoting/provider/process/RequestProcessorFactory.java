package com.fox.rpc.remoting.provider.process;

import com.fox.rpc.common.extension.UserServiceLoader;
import com.fox.rpc.remoting.invoker.process.ResponseProcessor;
import com.fox.rpc.remoting.invoker.process.ThreadPoolResponseProcessor;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public class RequestProcessorFactory {

    public static RequestProcessor selectProcessor() {
        RequestProcessor requestProcessor = UserServiceLoader.getExtension(RequestProcessor.class);
        if (requestProcessor != null) {
            return requestProcessor;
        } else {
            return new ThreadPoolRequestProcessor();
        }
    }

    public static ResponseProcessor selectResponseProcessor() {
        ResponseProcessor responseProcessor = UserServiceLoader.getExtension(ResponseProcessor.class);
        if (responseProcessor != null) {
            return responseProcessor;
        } else {
            return new ThreadPoolResponseProcessor();
        }
    }

}
