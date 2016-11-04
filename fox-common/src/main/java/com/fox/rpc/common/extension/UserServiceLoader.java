package com.fox.rpc.common.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class UserServiceLoader {

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


    public static <T> List<T> getExtensionList(Class<T> clazz) {
        List<T> extensions = (List<T>) extensionServices.get(clazz);
        if (extensions == null) {
            extensions = newExtensionList(clazz);
            if (!extensions.isEmpty()) {
                extensionServices.put(clazz, extensions);
            }
        }
        return extensions;
    }


    public static <T> List<T> newExtensionList(Class<T> clazz) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        List<T> extensions = new ArrayList<T>();
        for (T service : serviceLoader) {
            extensions.add(service);
        }
        return extensions;
    }
}
