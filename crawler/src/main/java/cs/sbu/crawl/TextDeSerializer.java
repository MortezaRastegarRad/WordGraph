package cs.sbu.crawl;

import java.util.Map;

import cs.sbu.crawl.Text;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TextDeSerializer implements Deserializer<Text> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Text deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        Text object = null;
        try {
            object = mapper.readValue(data, Text.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes " + exception);
        }
        return object;
    }

    @Override
    public void close() {
    }
}