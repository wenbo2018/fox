package com.github.wenbo2018.fox.remoting.exception;

/**
 * Created by shenwenbo on 2017/3/25.
 */
public class HeartBeatExcepion extends RuntimeException {
    private static final long serialVersionUID = 2484428054978954524L;

    public HeartBeatExcepion() {
        super();
    }

    public HeartBeatExcepion(String message) {
        super(message);
    }

    public HeartBeatExcepion(String message, Throwable cause) {
        super(message, cause);
    }

    public HeartBeatExcepion(Throwable cause) {
        super(cause);
    }

    protected HeartBeatExcepion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
