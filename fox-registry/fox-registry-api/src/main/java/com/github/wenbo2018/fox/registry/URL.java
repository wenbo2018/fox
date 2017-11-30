package com.github.wenbo2018.fox.registry;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wenbo.shen on 2017/6/10.
 */
public class URL implements Serializable {

    private static final long serialVersionUID = -2383018305613753463L;
    private String host;
    private int port;
    private String path;
    private Map<String, String> parameters;
    private RegistryEnum registryEnum;



    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public RegistryEnum getRegistryEnum() {
        return registryEnum;
    }

    public void setRegistryEnum(RegistryEnum registryEnum) {
        this.registryEnum = registryEnum;
    }
}
