package cs.sbu;

import cs.sbu.config.AppConfig;
import cs.sbu.config.KafkaConfig;
import cs.sbu.connection.ConsumerCreator;
import cs.sbu.connection.ProducerCreator;
import cs.sbu.connection.RedisConnection;
import cs.sbu.crawl.Crawler;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;


public class AppCrawler {

    public static void main(String[] args) throws IOException {
        AppConfig appConfig = new AppConfig();
        KafkaConfig kafkaConfig = new KafkaConfig();
        // for save all urls that crawl i use Kafka
        KafkaProducer<Long, String> ProducerUrls = ProducerCreator.createProducer();
        ProducerUrls.send(new ProducerRecord<>(kafkaConfig.getProperty("TOPIC_NAME"), "https://en.wikipedia.org/wiki/Main_Page"));
        ProducerUrls.send(new ProducerRecord<>(kafkaConfig.getProperty("TOPIC_NAME"), "https://www.amazon.com/gp/prime"));
        ProducerUrls.flush();
        KafkaProducer<Long, String> ProducerText = ProducerCreator.createProducer();
        KafkaConsumer<Long, String> ConsumerUrls = ConsumerCreator.createConsumer();
        // for save urls that checked i use Redis
        RedisConnection redisConnection = new RedisConnection();

        for (int i = 0; i < Integer.parseInt(appConfig.getProperty("threadnumber")); i++) {
            Thread thread = new Thread(new Crawler(ProducerUrls, ConsumerUrls, redisConnection, ProducerText));
            thread.start();
        }
    }
}
