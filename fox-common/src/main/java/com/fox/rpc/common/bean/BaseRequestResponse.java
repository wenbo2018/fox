package com.fox.rpc.common.bean;

import java.io.Serializable;

/**
 * Created by shenwenbo on 2017/3/24.
 */
public class BaseRequestResponse implements Serializable{
    private static final long serialVersionUID = 8781436104726038229L;
    private int type;
    private InvokeRequest invokeRequest;
    private InvokeResponse invokeResponse;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public InvokeRequest getInvokeRequest() {
        return invokeRequest;
    }

    public void setInvokeRequest(InvokeRequest invokeRequest) {
        this.invokeRequest = invokeRequest;
    }

    public InvokeResponse getInvokeResponse() {
        return invokeResponse;
    }

    public void setInvokeResponse(InvokeResponse invokeResponse) {
        this.invokeResponse = invokeResponse;
    }
}
