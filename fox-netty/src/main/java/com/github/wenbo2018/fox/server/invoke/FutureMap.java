package com.github.wenbo2018.fox.server.invoke;

import com.github.wenbo2018.fox.remoting.invoker.api.CallFuture;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class FutureMap {

    private static ConcurrentHashMap<String, CallFuture> pendingRPC = new ConcurrentHashMap<String, CallFuture>();

    public static void putFuture(String requestId, CallFuture callFuture) {
        pendingRPC.put(requestId, callFuture);
    }

    public static CallFuture getFuture(String requestId) {
        return pendingRPC.get(requestId);
    }

    public static void removeFuture(String requestId) {
        pendingRPC.remove(requestId);
    }

}
