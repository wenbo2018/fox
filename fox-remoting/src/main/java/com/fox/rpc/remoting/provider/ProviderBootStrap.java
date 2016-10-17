package com.fox.rpc.remoting.provider;

import com.fox.rpc.common.codec.SerializerFactory;

/**
 * Created by shenwenbo on 2016/9/27.
 */
public class ProviderBootStrap {

    static volatile boolean isInitialized = false;

    public static void init() {
        if (!isInitialized) {
            //init SerializerFactory
            SerializerFactory.init();
            isInitialized = true;
        }
    }
}
