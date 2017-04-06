package com.fox.rpc.remoting.invoker;

import com.fox.rpc.common.codec.SerializerFactory;
import com.fox.rpc.config.ConfigManagerLoader;
import com.fox.rpc.remoting.invoker.filter.InvokerFilterWrapper;
import com.fox.rpc.registry.RegistryManager;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class InvokerBootStrap {

    private static volatile boolean isStartup = false;

    public static boolean isStartup() {
        return isStartup;
    }

    public static void startup() {
        if (!isStartup) {
            synchronized (InvokerBootStrap.class) {
                if (!isStartup) {
                    //初始化配置
                    ConfigManagerLoader.init();
                    InvokerFilterWrapper.init();
                    //系列化工厂初始化
                    SerializerFactory.init();
                    //注册管理初始化
                    RegistryManager.getInstance();
                    ClientManager.getInstance();
                    isStartup=true;
                }
            }
        }
    }

}
