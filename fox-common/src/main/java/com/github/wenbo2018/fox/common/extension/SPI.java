package com.github.wenbo2018.fox.common.extension;

import java.lang.annotation.*;

/**
 * Created by shenwenbo on 2017/4/15.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    String value() default "";

}

