package cs.sbu.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;




import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gaurav.kafka.pojo.CustomObject;

class CustomSerializer implements Serializer<Long> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Long data) {
        return new byte[0];
    }

    @Override
    public byte[] serialize(String topic, Long data) {
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
