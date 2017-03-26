package com.fox.rpc.remoting.invoker.async;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;

/**
 * Created by shenwenbo on 2017/3/25.
 */
public interface Callback extends Runnable {

    void callback(InvokeResponse response);

    void setRequest(InvokeRequest request);

    void dispose();

}
