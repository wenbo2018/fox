package com.fox.rpc.remoting.invoker.api;

/**
 * Created by shenwenbo on 16/8/23.
 */
public interface AsyncProxyCallback<T> {

    void onSuccess(T result);

    void onException(Exception e);

}
