package com.github.wenbo2018.fox.registry.api.listener;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public interface ServerInfoListener {
    /***
     * 服务版本发生变化时监听事件
     * @param serverAddress
     * @param version
     */
    void onServerVersionChange(String serverAddress, String version);

}
