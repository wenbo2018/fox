package com.github.wenbo2018.fox.remoting.invoker.async;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.common.bean.InvokeRequest;

/**
 * Created by shenwenbo on 2017/3/25.
 */
public interface Callback extends Runnable {

    void callback(InvokeResponse response);

    void setRequest(InvokeRequest request);

    void dispose();

}
