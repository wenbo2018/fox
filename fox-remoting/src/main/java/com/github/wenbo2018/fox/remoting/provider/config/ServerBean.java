package com.github.wenbo2018.fox.remoting.provider.config;

import com.github.wenbo2018.fox.remoting.ServiceFactory;

/**
 * Created by shenwenbo on 2017/3/9.
 */
public class ServerBean {

    private int port;
    private volatile ServerConfig serverConfig;

    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }

    public ServerConfig init() throws Exception {
        if (serverConfig == null) {
            synchronized (this) {
                if (serverConfig == null) {
                    serverConfig = new ServerConfig();
                    serverConfig.setPort(port);
                    ServiceFactory.startUpServer(serverConfig);
                }
            }
        }
        return serverConfig;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
}
