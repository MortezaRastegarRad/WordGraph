package cs.sbu.connection;

import cs.sbu.config.KafkaConfig;
import cs.sbu.crawl.Text;
import cs.sbu.crawl.TextSerializer;
import cs.sbu.crawl.Url;
import cs.sbu.crawl.UrlSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;


import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
//import com.gaurav.kafka.constants.IKafkaConstants;


public class ProducerCreator {

    public static KafkaProducer createProducer(String s) throws IOException {
        Properties properties = new Properties();
        KafkaConfig conf = new KafkaConfig();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getProperty("KAFKA_BROKERS"));
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, conf.getProperty("CLIENT_ID"));
        switch (s) {
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
