package com.fox.rpc.remoting.exception;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class RpcException extends RuntimeException {


    public RpcException(String msg) {
        super(msg);
    }

    public RpcException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

}
