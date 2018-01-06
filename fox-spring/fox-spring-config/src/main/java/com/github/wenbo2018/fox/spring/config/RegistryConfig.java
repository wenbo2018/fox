package com.github.wenbo2018.fox.spring.config;


import com.github.wenbo2018.fox.registry.api.RegistryManager;

/**
 * Created by wenbo.shen on 2018/1/6.
 */
public class RegistryConfig {

    // 注册中心地址
    private String address;
    // 注册中心缺省端口
    private Integer port;

    void init() {
        RegistryManager.getInstance();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
