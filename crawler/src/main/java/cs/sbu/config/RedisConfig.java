package cs.sbu.config;

import java.io.IOException;

public class RedisConfig extends Config {


    RedisConfig() throws IOException {
        properties.load(RedisConfig.class.getClassLoader().getResourceAsStream("redis.properties"));
    }

}
