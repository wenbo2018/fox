package com.fox.rpc.remoting.provider.process;

import com.fox.rpc.common.bean.InvokeResponse;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public interface ServiceProviderChannel {

    public void write(final InvokeResponse invokeResponse);

}
