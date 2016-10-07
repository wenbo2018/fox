package com.fox.rpc.registry;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by shenwenbo on 2016/10/4.
 */
public class ConfigManager {

    private static Logger LOGGER=Logger.getLogger(ConfigManager.class);

    private static  final String ZK_FILE="/data/zk";



    static volatile boolean isInitialized = false;

    /*
     * Service config can be stored in /data/webapps/appenv or Registery Center.
     * The priority of /data/webapps/appenv is higher than Register Center.
     * Service config will be published to ConfigManager. Other module can use
     * ConfigManager to get service configs.
     */
    public synchronized static void init() {
        if (!isInitialized) {
            // Properties config = loadDefaultConfig();
            Properties config = new Properties();

            try {
                Properties props = loadFromFile();
                config.putAll(props);
            } catch (IOException e) {
                logger.error("Failed to load config from " + ENV_FILE, e);
            }

            config = normalizeConfig(config);
            ConfigManagerLoader.getConfigManager().init(config);
            // RegistryManager.getInstance().init(config);
            isInitialized = true;
        }
    }


    private static Properties loadFromFile() throws IOException {
        Properties props = new Properties();
        InputStream in = null;

        try {
            in = new FileInputStream(ZK_FILE);
            props.load(in);
        } catch (FileNotFoundException e) {
            LOGGER.error(ZK_FILE + " does not exist");
        } finally {
            if (in != null)
                in.close();
        }
        return props;
    }


}
