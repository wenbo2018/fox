package com.fox.rpc.remoting.provider;

import com.fox.rpc.common.codec.SerializerFactory;
import com.fox.rpc.config.ConfigManagerLoader;
import com.fox.rpc.registry.RegistryManager;

/**
 * Created by shenwenbo on 2016/9/27.
 */
public class ProviderBootStrap {

    static volatile boolean isInitialized = false;

    public static void init() {
        if (!isInitialized) {
            //初始化配置
            ConfigManagerLoader.init();
            //系列化工厂初始化
            SerializerFactory.init();
            //注册管理初始化
            RegistryManager.getInstance();
            isInitialized = true;
        }
    }
}
