package com.github.wenbo2018.fox.monitor.api;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public interface MonitorFactory {

    Monitor getMonitor(String address);

}
