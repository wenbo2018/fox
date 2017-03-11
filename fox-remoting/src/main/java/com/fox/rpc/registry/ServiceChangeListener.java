package com.fox.rpc.registry;

import java.util.List;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public interface ServiceChangeListener {
    // 当服务新增机器、修改机器。 String[]默认1维数组，ip+port
    void onServiceHostChange(String serviceName, List<String[]> hostList);
}
