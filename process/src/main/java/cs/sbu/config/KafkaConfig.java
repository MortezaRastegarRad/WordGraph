package cs.sbu.config;

import java.io.IOException;
import java.util.Properties;

public class KafkaConfig extends Config {

    public KafkaConfig() throws IOException {
        properties.load(KafkaConfig.class.getClassLoader().getResourceAsStream("kafka.properties"));
    }
}
