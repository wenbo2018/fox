package com.github.wenbo2018.fox.remoting.provider.process;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public interface ServiceProviderChannel {

    public void write(final InvokeResponse invokeResponse);

}
