package com.fox.rpc.remoting.invoker.process;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.provider.process.ServiceProviderChannel;

/**
 * Created by shenwenbo on 2017/3/25.
 */
public interface ResponseProcessor{

    void processRequest(InvokeResponse invokeResponse, ServiceProviderChannel channel);

}
