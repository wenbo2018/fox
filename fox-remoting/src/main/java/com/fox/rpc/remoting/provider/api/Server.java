package com.fox.rpc.remoting.provider.api;

import com.fox.rpc.remoting.provider.config.ServerConfig;
import com.fox.rpc.remoting.provider.process.RequestProcessor;

/**
 * Created by shenwenbo on 16/8/10.
 */
public interface Server {

    public void star() throws Exception;

    public boolean isStarted();

    public RequestProcessor star(ServerConfig serverConfig) throws Exception;

    public RequestProcessor getRequestProcessor();

    int getPort();

}
