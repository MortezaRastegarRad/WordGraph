package cs.sbu.connection;

import cs.sbu.Processing.Text;
import cs.sbu.Processing.TextDeSerializer;
import cs.sbu.Processing.Url;
import cs.sbu.Processing.UrlDeSerializer;
import cs.sbu.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;


public class ConsumerCreator {
    public static KafkaConsumer createConsumer(String topic, String s) throws IOException {
        Properties properties = new Properties();
        KafkaConfig conf = new KafkaConfig();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getProperty("KAFKA_BROKERS"));
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, conf.getProperty("GROUP_ID_CONFIG"));
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Integer.parseInt(conf.getProperty("MAX_POLL_RECORDS")));
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, conf.getProperty("OFFSET_RESET_EARLIER"));
        switch (s) {
            case "url":
                properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
                properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UrlDeSerializer.class.getName());
                KafkaConsumer<Long, Url> consumer1 = new KafkaConsumer<>(properties);
                consumer1.subscribe(Collections.singletonList(topic));
                return consumer1;
            case "text":
                properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
                properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TextDeSerializer.class.getName());
                KafkaConsumer<Long, Text> consumer2 = new KafkaConsumer<>(properties);
                consumer2.subscribe(Collections.singletonList(topic));
                return consumer2;
            default:
                properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
                properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(properties);
                consumer.subscribe(Collections.singletonList(topic));
                return consumer;
        }
    }
}