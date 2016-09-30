package com.fox.rpc.registry;

/**
 * 服务注册接口
 */
public interface RemotingServiceRegistry {

    /**
     * 注册服务名称与服务地址com.fox.rpc.registry.RemotingServiceRegistry
     *
     * @param serviceName    服务名称
     * @param serviceAddress 服务地址
     */
    void register(String serviceName, String serviceAddress);

    void setContext(RegistryConfig cfg);


}