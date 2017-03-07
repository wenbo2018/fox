package com.fox.rpc.config;

import com.fox.rpc.config.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public class PropertiesFileConfigManager extends AbstractConfigManager  {

    private static Logger LOGGER=Logger.getLogger(PropertiesFileConfigManager.class);

    private static final String ENV_FILE = "C:/data/app/appkeys.properties";

    private  Properties properties;

    private String env = null;
    private String group = null;


    @Override
    public void init() {
        try {
            properties=FileUtils.readFile(new FileInputStream(ENV_FILE));
            loadProperties(localCache,properties);
        } catch (Throwable e) {
            LOGGER.error("error when loadProperties:"+e);
        }
        env = (String) getStringValue("environment");
        if (StringUtils.isBlank(env)) {
            LOGGER.warn("the config 'environment' is undefined in " + ENV_FILE);
        }
        group = (String) getStringValue("group");
    }


    @Override
    public Properties getRegistryConfig() {
        return this.properties;
    }

    /**
     * 将Properties文件中的KV值保存在内存中；
     * @param properties
     */
    @Override
    public void init(Properties properties) {
        this.properties=properties;
        for (Iterator ir = properties.keySet().iterator(); ir.hasNext();) {
            String key = ir.next().toString();
            String value = properties.getProperty(key);
            setLocalValue(key, value);
        }
    }




    public static void loadProperties(Map<String, Object> results, Properties properties) {
        for (Iterator ir = properties.keySet().iterator(); ir.hasNext();) {
            String key = ir.next().toString();
            if (key.startsWith("#")) {
                continue;
            }
            String value = properties.getProperty(key);
            value = value.trim();
            results.put(key, value.trim());
        }
    }

}
