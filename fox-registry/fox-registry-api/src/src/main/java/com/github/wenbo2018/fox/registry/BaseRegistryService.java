package com.github.wenbo2018.fox.registry;

/**
 * Created by wenbo.shen on 2017/7/17.
 */
public interface BaseRegistryService {

    void registerService(URL url);

    void unregisterService(URL url) throws Exception;

}
