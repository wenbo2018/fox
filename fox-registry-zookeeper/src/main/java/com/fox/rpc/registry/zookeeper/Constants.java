package com.fox.rpc.registry.zookeeper;

/**
 * 常量
 */
public interface Constants {

    public static int ZK_SESSION_TIMEOUT = 5000;
    public static int ZK_CONNECTION_TIMEOUT = 2000;
    public static  String ZK_REGISTRY_PATH ="/fox/registry";
    public static final String KEY_REGISTRY_ADDRESS = "pigeon.registry.address";
}