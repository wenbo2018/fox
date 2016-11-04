package com.fox.rpc.remoting.provider.process;

import com.fox.rpc.common.extension.UserServiceLoader;

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

}
