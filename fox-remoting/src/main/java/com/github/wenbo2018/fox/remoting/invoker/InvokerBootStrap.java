package com.github.wenbo2018.fox.remoting.invoker;

import com.github.wenbo2018.fox.common.codec.SerializerFactory;
import com.github.wenbo2018.fox.config.ConfigManagerLoader;
import com.github.wenbo2018.fox.remoting.invoker.filter.InvokerFilterWrapper;
import com.github.wenbo2018.fox.registry.RegistryManager;

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
