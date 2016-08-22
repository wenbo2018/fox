package com.fox.rpc.remoting.provider.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenwenbo on 16/8/7.
 */
public class ProviderCfg<T> {

    private Map<String, Object> handlerMap = new HashMap<>();
    private String port;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Map<String, Object> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }
}
