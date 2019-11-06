package cs.sbu.Processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;


public class UrlSerializer implements Serializer<Url> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Url data) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception exception) {
            System.out.println("Error in serializing object" + data);
        }
        return retVal;
    }

    @Override
    public void close() {
    }
}