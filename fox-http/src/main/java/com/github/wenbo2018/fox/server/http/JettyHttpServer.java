package com.github.wenbo2018.fox.server.http;

import com.github.wenbo2018.fox.remoting.provider.AbstractServer;
import com.github.wenbo2018.fox.remoting.provider.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mortbay.jetty.Server;
/**
 * Created by wenbo.shen on 2017/5/12.
 */
public class JettyHttpServer extends AbstractServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyHttpServer.class);

    private Server server;

    @Override
    public void star() throws Exception {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    protected void doStart(ServerConfig serverConfig) {

    }

    @Override
    protected void doStop() {

    }
}
