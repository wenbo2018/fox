package com.github.wenbo2018.fox.remoting.provider;

import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import com.github.wenbo2018.fox.remoting.provider.api.Server;
import com.github.wenbo2018.fox.remoting.provider.config.ServerConfig;
import com.github.wenbo2018.fox.remoting.provider.process.RequestProcessor;
import com.github.wenbo2018.fox.remoting.provider.process.RequestProcessorFactory;
import com.github.wenbo2018.fox.remoting.provider.process.ServiceProviderChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shenwenbo on 2016/9/27.
 */
public abstract class AbstractServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServer.class);

    private RequestProcessor requestProcessor;

    private ServerConfig serverConfig = null;

    private int port;

    protected abstract void doStart(ServerConfig serverConfig);

    protected abstract void doStop();

    @Override
    public RequestProcessor star(ServerConfig serverConfig) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("server config:" + serverConfig);
        }
        doStart(serverConfig);
        this.port=serverConfig.getPort();
        this.serverConfig=serverConfig;
        this.requestProcessor= RequestProcessorFactory.selectProcessor();
        return this.requestProcessor;
    }

    public void stop() {
        doStop();
    }


    public void processRequest(InvokeRequest request, ServiceProviderChannel serviceProviderChannel){
        this.requestProcessor.processRequest(request,serviceProviderChannel);
    }

    @Override
    public RequestProcessor getRequestProcessor() {
        return this.requestProcessor;
    }

    @Override
    public int getPort() {
        return this.port;
    }
}
