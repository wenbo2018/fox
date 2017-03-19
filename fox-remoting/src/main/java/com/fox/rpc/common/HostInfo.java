package com.fox.rpc.common;

/**
 * Created by shenwenbo on 2017/3/19.
 */
public class HostInfo {

    private String host;
    private int port;

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

    public HostInfo() {
    }

    public HostInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
