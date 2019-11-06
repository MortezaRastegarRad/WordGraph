package cs.sbu.Processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

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