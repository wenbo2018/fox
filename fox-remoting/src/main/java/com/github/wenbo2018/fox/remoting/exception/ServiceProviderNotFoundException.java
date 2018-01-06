package com.github.wenbo2018.fox.remoting.exception;

/**
 * Created by wenbo.shen on 2018/1/4.
 */
public class ServiceProviderNotFoundException  extends RuntimeException {

    public ServiceProviderNotFoundException() {
        super();
    }

    public ServiceProviderNotFoundException(String message) {
        super(message);
    }

    public ServiceProviderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceProviderNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ServiceProviderNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
