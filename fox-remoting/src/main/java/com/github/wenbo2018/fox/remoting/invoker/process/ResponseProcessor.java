package com.github.wenbo2018.fox.remoting.invoker.process;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.remoting.provider.process.ServiceProviderChannel;

/**
 * Created by shenwenbo on 2017/3/25.
 */
public interface ResponseProcessor{

    void processRequest(InvokeResponse invokeResponse, ServiceProviderChannel channel);

}
