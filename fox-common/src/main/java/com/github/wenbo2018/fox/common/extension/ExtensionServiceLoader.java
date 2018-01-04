package com.github.wenbo2018.fox.common.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by shenwenbo on 16/8/6.
 */
public class ExtensionServiceLoader<T> {

    private static Map<Class<?>, Object> extensionServices = new ConcurrentHashMap<Class<?>, Object>();

    private static Map<String, Object> cachedInstances = new ConcurrentHashMap<String, Object>();

    private static final ConcurrentMap<Class<?>, ExtensionServiceLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<Class<?>, ExtensionServiceLoader<?>>();

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


    /**
     * 根据实现类名获取指定扩展节点
     *
     * @param classType
     * @param <T>
     * @return
     */
    public <T> T getExtension(String classType) {
        T extension = (T) cachedInstances.get(classType);
        if (extension != null)
            return extension;
        Class<T> clazz = null;
        try {
            clazz = (Class<T>) Class.forName(classType);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("classType type(" + classType + ") is not class!");
        }
        extension = (T) cachedInstances.get(classType);
        if (extension == null) {
            try {
                extension = (T) clazz.newInstance();
            } catch (InstantiationException e) {
                throw new IllegalArgumentException("classType instance type(" + classType + ") is not be created!");
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("classType instance type(" + classType + ") is not be created!");
            }
            if (extension != null) {
                cachedInstances.put(classType, extension);
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

    /***
     * 获取某个接口的扩展，参考dubbo
     * @param type
     * @param <T>
     * @return
     */
    public static <T> ExtensionServiceLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null)
            throw new IllegalArgumentException("Extension type == null");
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
        }
        ExtensionServiceLoader<T> loader = (ExtensionServiceLoader<T>) EXTENSION_LOADERS.get(type);
        if (loader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionServiceLoader<T>());
            loader = (ExtensionServiceLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }


}
