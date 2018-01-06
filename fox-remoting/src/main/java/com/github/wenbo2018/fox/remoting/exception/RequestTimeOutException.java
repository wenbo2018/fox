package com.github.wenbo2018.fox.remoting.exception;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class RequestTimeOutException extends RuntimeException {
    public RequestTimeOutException() {
        super();
    }

    public RequestTimeOutException(String message) {
        super(message);
    }

    public RequestTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestTimeOutException(Throwable cause) {
        super(cause);
    }

    protected RequestTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
