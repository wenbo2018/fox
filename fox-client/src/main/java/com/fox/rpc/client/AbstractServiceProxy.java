package com.fox.rpc.client;

import com.fox.rpc.common.bean.RpcRequest;
import com.fox.rpc.common.bean.RpcResponse;
import com.fox.rpc.common.util.StringUtil;
import com.fox.rpc.registry.ServiceDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 16/8/6.
 */
public abstract class AbstractServiceProxy implements  ServiceProxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceProxy.class);
    /***缓存服务***/
    protected static Map<InvokerConfig<?>, Object> services = new ConcurrentHashMap<InvokerConfig<?>, Object>();

    private String serviceAddress;

    private ServiceDiscovery serviceDiscovery;


    @Override
    public void init() {

    }

    @Override
    public <T> T getProxy(InvokerConfig<T> invokerConfig) {
        return create(invokerConfig);
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final InvokerConfig<T> invokerConfig) {

        Object service = null;
        /****从缓存中拿服务***/
        service=services.get(invokerConfig);
        if (service==null) {
            // 创建动态代理对象
            final Class<T> interfaceClass = invokerConfig.getServiceInterface();
            final String serviceVersion = "";
            return (T) Proxy.newProxyInstance(
                    interfaceClass.getClassLoader(),
                    new Class<?>[]{interfaceClass},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            // 创建 RPC 请求对象并设置请求属性
                            RpcRequest request = new RpcRequest();
                            request.setRequestId(UUID.randomUUID().toString());
                            request.setInterfaceName(method.getDeclaringClass().getName());
                            request.setServiceVersion(serviceVersion);
                            request.setMethodName(method.getName());
                            request.setParameterTypes(method.getParameterTypes());
                            request.setParameters(args);
                            // 获取 RPC 服务地址
                            if (serviceDiscovery != null) {
                                String serviceName = interfaceClass.getName();
                                if (StringUtil.isNotEmpty(serviceVersion)) {
                                    serviceName += "-" + serviceVersion;
                                }
                                serviceAddress = serviceDiscovery.discover(serviceName);
                                LOGGER.debug("discover service: {} => {}", serviceName, serviceAddress);
                            }
                            if (StringUtil.isEmpty(serviceAddress)) {
                                throw new RuntimeException("server address is empty");
                            }
                            // 从 RPC 服务地址中解析主机名与端口号
                            String[] array = StringUtil.split(serviceAddress, ":");
                            String host = array[0];
                            int port = Integer.parseInt(array[1]);
                            // 创建 RPC 客户端对象并发送 RPC 请求
                            RpcClient client = new RpcClient(host, port);
                            long time = System.currentTimeMillis();
                            RpcResponse response = client.send(request);
                            LOGGER.debug("time: {}ms", System.currentTimeMillis() - time);
                            if (response == null) {
                                throw new RuntimeException("response is null");
                            }
                            // 返回 RPC 响应结果
                            if (response.hasException()) {
                                throw response.getException();
                            } else {
                                return response.getResult();
                            }
                        }
                    }
            );
        }
        return (T) service;
    }

}
