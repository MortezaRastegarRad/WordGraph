package cs.sbu.connection;

import cs.sbu.config.KafkaConfig;
import cs.sbu.crawl.Text;
import cs.sbu.crawl.Url;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;


import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
//import com.gaurav.kafka.constants.IKafkaConstants;


public class ConsumerCreator {
    public static KafkaConsumer createConsumer(String topic, String s) throws IOException {
        Properties properties = new Properties();
        KafkaConfig conf = new KafkaConfig();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getProperty("KAFKA_BROKERS"));
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, conf.getProperty("GROUP_ID_CONFIG"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Integer.parseInt(conf.getProperty("MAX_POLL_RECORDS")));
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, conf.getProperty("OFFSET_RESET_EARLIER"));
        switch (s) {
            case "url":
                KafkaConsumer<Long, Url> consumer1 = new KafkaConsumer<>(properties);
                consumer1.subscribe(Collections.singletonList(topic));
                return consumer1;
            case "text":
                KafkaConsumer<Long, Text> consumer2 = new KafkaConsumer<>(properties);
                consumer2.subscribe(Collections.singletonList(topic));
                return consumer2;
            default:
                KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(properties);
                consumer.subscribe(Collections.singletonList(topic));
                return consumer;
        }
    }
}