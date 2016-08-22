package com.fox.rpc.remoting.invoker.proxy;

import com.fox.rpc.SpiServiceLoader;
import com.fox.rpc.common.bean.RpcRequest;
import com.fox.rpc.common.bean.RpcResponse;
import com.fox.rpc.common.util.StringUtil;
import com.fox.rpc.registry.RemotingServiceDiscovery;
import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by shenwenbo on 16/8/22.
 */
public class RemotingServiceProxyHandler<T> implements InvocationHandler,Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemotingServiceProxyHandler.class);

    private String serviceAddress;

    private RemotingServiceDiscovery serviceDiscovery;

    private InvokerConfig<T> invokerConfig;

    private String serviceVersion;

    public RemotingServiceProxyHandler(RemotingServiceDiscovery serviceDiscovery,String serviceAddress) {
        this.serviceAddress=serviceAddress;
        this.serviceDiscovery=serviceDiscovery;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 创建 RPC 请求对象并设置请求属性
         return null;
    }
}
