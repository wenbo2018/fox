package com.fox.rpc.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class RegisterCfg {

    private String port;

    private String address;

    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Object> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }
}
