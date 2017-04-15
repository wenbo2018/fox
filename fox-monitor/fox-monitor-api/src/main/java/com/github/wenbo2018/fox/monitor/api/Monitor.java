package com.github.wenbo2018.fox.monitor.api;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public interface Monitor {

    void logREvent(String msg);

    void logError(String msg);

    void logInfo(String msg);

    void logWarn(String msg);

}
