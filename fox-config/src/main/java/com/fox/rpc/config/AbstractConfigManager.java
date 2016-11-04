package com.fox.rpc.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public abstract class AbstractConfigManager implements ConfigManager {

    protected Map<String, Object> localCache = new ConcurrentHashMap<String, Object>();

    private static final Object NULL = new Object();


    public void setLocalValue(String key, Object value) {
        if (key != null) {
            if (value != null) {
                localCache.put(key, value);
            } else {
                localCache.put(key, NULL);
            }
        }
    }

    @Override
    public String getStringValue(String key, String defaultValue) {
        return getStringValue(key)==null?defaultValue:getStringValue(key);
    }

    @Override
    public String getStringValue(String key) {
        return (String) localCache.get(key);
    }
}
