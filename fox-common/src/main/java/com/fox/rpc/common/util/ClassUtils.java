package com.fox.rpc.common.util;

/**
 * Created by shenwenbo on 16/8/7.
 */
public class ClassUtils {
    public static Class loadClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        return org.apache.commons.lang3.ClassUtils.getClass(classLoader, className);
    }
}
