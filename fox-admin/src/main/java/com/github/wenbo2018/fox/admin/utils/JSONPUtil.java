package com.github.wenbo2018.fox.admin.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Created by wenbo.shen on 2017/6/25.
 */
@ControllerAdvice(basePackages = {"com.github.wenbo2018.jconf.web"})
public class JSONPUtil extends AbstractJsonpResponseBodyAdvice {
    public JSONPUtil(){
        super("callback","jsonp");
    }
}
