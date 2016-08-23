package com.fox.rpc.remoting.provider.api;

import com.fox.rpc.registry.RemotingServiceRegistry;
import com.fox.rpc.remoting.provider.config.ProviderCfg;

import java.util.Map;

/**
 * Created by shenwenbo on 16/8/10.
 */
public interface Server {

    public void star() throws Exception;

    public void  setContext(ProviderCfg cfg);

}
