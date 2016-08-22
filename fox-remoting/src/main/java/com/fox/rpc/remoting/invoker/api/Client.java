package com.fox.rpc.remoting.invoker.api;

import com.fox.rpc.common.bean.RpcRequest;
import com.fox.rpc.common.bean.RpcResponse;

/**
 * Created by shenwenbo on 16/8/19.
 */
public interface Client {

    public RpcResponse send(RpcRequest request) throws Exception;

    public void setContext(String host, int port);

}
