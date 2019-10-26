package cs.sbu.config;

import java.util.Properties;

public class Config {

    Properties properties;
    Config(){
        properties = new Properties();
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
