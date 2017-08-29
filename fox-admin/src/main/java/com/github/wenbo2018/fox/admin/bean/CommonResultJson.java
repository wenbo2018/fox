package com.github.wenbo2018.fox.admin.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenbo.shen on 2017/5/1.
 */
@Getter
@Setter
public class CommonResultJson {
    private int code;
    private String message;
    private Map<String,Object> datas=new HashMap<String, Object>();

    public void setDataObject(String key,Object value){
        datas.put(key,value);
    }


}
