package com.fox.rpc.remoting.invoker.config;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class ConnectInfo {

    private String hostIp;

    private int hostPort;

    public ConnectInfo(String hostIp,int hostPort) {
        this.hostIp=hostIp;
        this.hostPort=hostPort;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public int getHostPort() {
        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }
}
