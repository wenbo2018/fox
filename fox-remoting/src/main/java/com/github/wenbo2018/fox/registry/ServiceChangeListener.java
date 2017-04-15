package com.github.wenbo2018.fox.registry;

import java.util.List;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public interface ServiceChangeListener {


    void  onServiceHostChange(String serviceName, List<String[]> hostList);

}
