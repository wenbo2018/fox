package com.fox.rpc.remoting.provider;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.remoting.provider.api.Server;
import com.fox.rpc.remoting.provider.config.ServerConfig;
import com.fox.rpc.remoting.provider.process.RequestProcessor;
import com.fox.rpc.remoting.provider.process.RequestProcessorFactory;
import com.fox.rpc.remoting.provider.process.ServiceProviderChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shenwenbo on 2016/9/27.
 */
public abstract class AbstractServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServer.class);

    private RequestProcessor requestProcessor;

    private ServerConfig serverConfig = null;

    protected abstract void doStart(ServerConfig serverConfig);

    protected abstract void doStop();

    @Override
    public RequestProcessor star(ServerConfig serverConfig) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("server config:" + serverConfig);
        }
        doStart(serverConfig);
        this.serverConfig=serverConfig;
        this.requestProcessor= RequestProcessorFactory.selectProcessor();
        return this.requestProcessor;
    }

    public void stop() {
        doStop();
    }


    public void processRequest(InvokeRequest request,ServiceProviderChannel serviceProviderChannel){
        this.requestProcessor.processRequest(request,serviceProviderChannel);
    }

}
