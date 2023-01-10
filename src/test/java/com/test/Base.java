package com.test;

import java.io.FileInputStream;
import java.util.Properties;

public class Base {
    private static final String PROPERTY_FILE_PATH = "./globalProps.properties";
    private Properties props;

    private synchronized void readProperties() {
        if (props == null) {
            props = new Properties();
            try {
                props.load(new FileInputStream(PROPERTY_FILE_PATH));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected Object getPropertyValue(String property) {
        readProperties();
        return props.get(property);
    }
}
