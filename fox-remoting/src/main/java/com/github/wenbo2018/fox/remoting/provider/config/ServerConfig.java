package com.github.wenbo2018.fox.remoting.provider.config;

import com.github.wenbo2018.fox.remoting.ServiceFactory;
import com.github.wenbo2018.fox.remoting.common.Constants;

/**
 * Created by shenwenbo on 2017/3/9.
 */
public class ServerConfig {

    private int corePoolSize = Constants.PROVIDER_POOL_CORE_SIZE;
    private int maxPoolSize = Constants.PROVIDER_POOL_MAX_SIZE;
    private int workQueueSize = Constants.PROVIDER_POOL_QUEUE_SIZE;
    //服务端口号，默认在4080发布
    private int port = Constants.DEFAULT_PORT;

    //服务发布机器ip
    private String ip = "127.0.0.1";

    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }

    public ServerConfig init() throws Exception {
        ServiceFactory.startUpServer(this);
        return this;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getWorkQueueSize() {
        return workQueueSize;
    }

    public void setWorkQueueSize(int workQueueSize) {
        this.workQueueSize = workQueueSize;
    }
}
