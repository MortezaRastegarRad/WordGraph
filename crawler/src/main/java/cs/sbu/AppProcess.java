package cs.sbu;

import cs.sbu.config.KafkaConfig;
import cs.sbu.connection.ConsumerCreator;
import cs.sbu.connection.ProducerCreator;
import cs.sbu.prossesing.Processig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.IOException;

public class AppProcess {
    public static void main(String args[]) throws IOException {
        KafkaConfig kafkaConfig = new KafkaConfig();
        KafkaConsumer<Long, String> consumerText = ConsumerCreator.createConsumer(kafkaConfig.getProperty("TOPIC_NAME_text"));
        KafkaProducer<Long, String> producerWorld = ProducerCreator.createProducer(kafkaConfig.getProperty("TOPIC_NAME_WORD"));
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Processig(producerWorld, consumerText));
            thread.start();
        }

    }
}