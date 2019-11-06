package cs.sbu.Processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class UrlDeSerializer implements Deserializer<Url> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Url deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        Url object = null;
        try {
            object = mapper.readValue(data, Url.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes " + exception);
        }
        return object;
    }

    @Override
    public void close() {
    }
}