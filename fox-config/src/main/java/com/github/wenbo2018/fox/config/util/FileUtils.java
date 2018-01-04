package com.github.wenbo2018.fox.config.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public class FileUtils {


    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static Properties readFile(InputStream is) {
        Properties properties = new Properties();
        BufferedReader br = null;
        if (is != null) {
            try {
                br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    int idx = line.indexOf("=");
                    if (idx != -1) {
                        String key = line.substring(0, idx);
                        String value = line.substring(idx + 1);
                        properties.put(key.trim(), value.trim());
                    }
                }
            } catch (Throwable e) {
                logger.error("read file error:{}", e);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return properties;
    }

}
