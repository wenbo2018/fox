package com.github.wenbo2018.fox.registry;

/**
 * Created by wenbo.shen on 2017/6/10.
 */
public enum RegistryEnum {

    DEFAULT_REGISTRY("default","默认注册中心"),
    ZOOKPEEPER_REGISTRY("zk","zk注册中心");

    String type;
    String desc;

    RegistryEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
