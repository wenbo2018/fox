package com.fox.rpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class SpiServiceLoader {

    private static Map<Class<?>, Object> extensionServices = new ConcurrentHashMap<Class<?>, Object>();






    public static <T> T getExtension(Class<T> clazz) {
        T extension = (T) extensionServices.get(clazz);
        if (extension == null) {
            extension = newExtension(clazz);
            if (extension != null) {
                extensionServices.put(clazz, extension);
            }
        }
        return extension;
    }


    public static <T> T newExtension(Class<T> clazz) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        for (T service : serviceLoader) {
            return service;
        }
        return null;
    }
}
