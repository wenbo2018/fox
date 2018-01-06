package com.github.wenbo2018.fox.registry.api.exception;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public class RegistryException extends RuntimeException {

    public RegistryException() {
        super();
    }

    public RegistryException(String message) {
        super(message);
    }

    public RegistryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryException(Throwable cause) {
        super(cause);
    }

    protected RegistryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
