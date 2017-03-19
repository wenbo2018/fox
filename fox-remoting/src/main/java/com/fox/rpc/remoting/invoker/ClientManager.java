package com.fox.rpc.remoting.invoker;

import com.fox.rpc.registry.RegistryEventListener;
import com.fox.rpc.registry.listener.ServiceProviderChangeEvent;
import com.fox.rpc.registry.listener.ServiceProviderChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class ClientManager {

    private static Logger LOGGER=LoggerFactory.getLogger(ClientManager.class);

    private ServiceProviderChangeListener providerChangeListener = new InnerServiceProviderChangeListener();

    private static ClientManager instance = new ClientManager();

    public static ClientManager getInstance() {
        return instance;
    }

    private ClientManager() {
        //添加监听事件
        RegistryEventListener.addListener(providerChangeListener);
    }

    public void registerClient() {

    }

    class InnerServiceProviderChangeListener implements  ServiceProviderChangeListener{

        @Override
        public void serviceProviderAdded(ServiceProviderChangeEvent event) {

        }

        @Override
        public void serviceProviderRemoved(ServiceProviderChangeEvent event) {

        }
    }

}
