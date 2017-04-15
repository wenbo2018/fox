package com.github.wenbo2018.fox.registry.exception;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public class RegistryException extends  Exception {

    private static final long serialVersionUID = -277294587317829825L;

    public RegistryException(String msg) {
        super(msg);
    }

    public RegistryException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RegistryException(Throwable cause) {
        super(cause);
    }

}
