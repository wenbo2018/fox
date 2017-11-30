package com.github.wenbo2018.fox.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wenbo.shen on 2017/6/10.
 */
public abstract class AbstractRegistryFactory implements RegistryFactory {

    private static final Map<String, Registry> registryMap = new ConcurrentHashMap<String, Registry>();

    @Override
    public Registry getRegistry(URL url) {
        RegistryEnum registryEnum = url.getRegistryEnum();
        String key = registryEnum.getType();
        Registry registry = registryMap.get(key);
        if (registry != null) {
            return registry;
        }
        registry = createRegistry(url);
        if (registry == null) {
            throw new IllegalStateException("Can not create registry " + url);
        }
        registryMap.put(key, registry);
        return registry;
    }

    protected abstract Registry createRegistry(URL url);

}
