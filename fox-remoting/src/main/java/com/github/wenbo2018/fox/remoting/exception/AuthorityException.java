package com.github.wenbo2018.fox.remoting.exception;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class AuthorityException extends RuntimeException {
    public AuthorityException() {
        super();
    }

    public AuthorityException(String message) {
        super(message);
    }

    public AuthorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorityException(Throwable cause) {
        super(cause);
    }

    protected AuthorityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
