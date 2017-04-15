package com.github.wenbo2018.fox.remoting.provider;

import com.github.wenbo2018.fox.common.codec.SerializerFactory;
import com.github.wenbo2018.fox.config.ConfigManagerLoader;
import com.github.wenbo2018.fox.registry.RegistryManager;

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
