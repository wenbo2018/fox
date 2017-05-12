package com.github.wenbo2018.fox.config;

import com.github.wenbo2018.fox.config.util.FileUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public class PropertiesFileConfigManager extends AbstractConfigManager  {

    private static Logger LOGGER=Logger.getLogger(PropertiesFileConfigManager.class);

    private static final String ENV_FILE_WIN = "C:/data/app/appkeys.properties";
    private static final String ENV_FILE_LINUX = "/data/app/appkeys.properties";

    private  Properties properties;

    private String ip=null;


    @Override
    public void init() {
        LOGGER.info("ConfigManager init");
        try {
            String os = System.getProperty("os.name");
            if(os.toLowerCase().startsWith("win")){
                properties= FileUtils.readFile(new FileInputStream(ENV_FILE_WIN));
            }else {
                properties=FileUtils.readFile(new FileInputStream(ENV_FILE_LINUX));
            }

            loadProperties(localCache,properties);
        } catch (Throwable e) {
            LOGGER.error("error when loadProperties:"+e);
        }
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
