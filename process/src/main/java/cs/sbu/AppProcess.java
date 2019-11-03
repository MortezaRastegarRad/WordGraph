package cs.sbu;

import cs.sbu.Processing.Processig;
import cs.sbu.Processing.Text;
import cs.sbu.Processing.Word;
import cs.sbu.config.KafkaConfig;
import cs.sbu.connection.ConsumerCreator;
import cs.sbu.connection.ProducerCreator;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.IOException;
import java.util.ArrayList;

public class AppProcess {
    public static void main(String[] args) throws IOException {
        KafkaConfig kafkaConfig = new KafkaConfig();
        KafkaConsumer<Long, Text> consumerText = ConsumerCreator.createConsumer(kafkaConfig.getProperty("TOPIC_NAME_text"),"text");
        KafkaProducer<Long, Word> producerWorld = ProducerCreator.createProducer("word");
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Processig(producerWorld, consumerText));
            thread.start();
        }
    }
}