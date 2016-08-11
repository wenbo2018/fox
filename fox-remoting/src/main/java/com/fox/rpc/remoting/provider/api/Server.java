package com.fox.rpc.remoting.provider.api;

/**
 * Created by shenwenbo on 16/8/10.
 */
public interface Server {
    /**
     * 启动服务
     */
    public void star() throws Exception;

    /**
     * 关闭服务
     */
    public void close();
}
