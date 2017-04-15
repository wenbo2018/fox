package com.github.wenbo2018.fox.remoting.provider.api;

import com.github.wenbo2018.fox.remoting.provider.config.ServerConfig;
import com.github.wenbo2018.fox.remoting.provider.process.RequestProcessor;

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
