package cs.sbu.connection;

import cs.sbu.Processing.*;
import cs.sbu.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public class ProducerCreator {

    public static KafkaProducer createProducer(String s) throws IOException {
        Properties properties = new Properties();
        KafkaConfig conf = new KafkaConfig();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getProperty("KAFKA_BROKERS"));
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, conf.getProperty("CLIENT_ID"));
        switch (s) {
            case "word":
                return new KafkaProducer<Long, Word>(properties);
            case "url":
                properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
                properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UrlSerializer.class.getName());
                return new KafkaProducer<Long, Url>(properties);
            case "text":
                properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
                properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TextSerializer.class.getName());
                return new KafkaProducer<Long, Text>(properties);
            default:
                properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
                properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
                return new KafkaProducer<Long, String>(properties);
        }

    }
}
