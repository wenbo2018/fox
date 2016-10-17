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
public class RegistryManager {

    private static Logger LOGGER=Logger.getLogger(RegistryManager.class);

    private static final String ENV_FILE = "/data/webapps/appenv";

    static volatile boolean isInitialized = false;

    public synchronized static void init() {
        if (!isInitialized) {
            // Properties config = loadDefaultConfig();
            Properties config = new Properties();
            try {
                Properties props = loadFromFile();
                config.putAll(props);
            } catch (IOException e) {
                LOGGER.error("Failed to load config from " + ENV_FILE, e);
            }

            config = normalizeConfig(config);
            ConfigManagerLoader.getConfigManager().init(config);
            isInitialized = true;
        }
    }

    private static Properties normalizeConfig(Properties props) {
        // Strip trailing whitespace in property values
        Properties newProps = new Properties();
        for (String key : props.stringPropertyNames()) {
            String value = props.getProperty(key);
            newProps.put(key, value.trim());
        }
        return newProps;
    }


    private static Properties loadFromFile() throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(ENV_FILE);
            props.load(in);
        } catch (FileNotFoundException e) {
            LOGGER.warn(ENV_FILE + " does not exist");
        } finally {
            if (in != null)
                in.close();
        }
        return props;
    }

}
