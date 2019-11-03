package cs.sbu;

import cs.sbu.config.AppConfig;
import cs.sbu.config.KafkaConfig;
import cs.sbu.connection.ConsumerCreator;
import cs.sbu.connection.ProducerCreator;
import cs.sbu.connection.RedisConnection;
import cs.sbu.crawl.Crawler;
import cs.sbu.crawl.Text;
import cs.sbu.crawl.Url;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;


public class AppCrawler {

    public static void main(String[] args) throws IOException {
        AppConfig appConfig = new AppConfig();
        KafkaConfig kafkaConfig = new KafkaConfig();
        // for save all urls that crawl i use Kafka
        KafkaProducer<Long, Url> ProducerUrls = ProducerCreator.createProducer("url");
        Url one = new Url("https://en.wikipedia.org/wiki/Main_Page", 0);
        Url two = new Url("https://www.amazon.com/gp/prime", 1);
        ProducerUrls.send(new ProducerRecord<>(kafkaConfig.getProperty("TOPIC_NAME_url"), one));
        ProducerUrls.send(new ProducerRecord<>(kafkaConfig.getProperty("TOPIC_NAME_url"), two));
        ProducerUrls.flush();
        KafkaProducer<Long, Text> ProducerText = ProducerCreator.createProducer("text");
        KafkaConsumer<Long, Url> ConsumerUrls = ConsumerCreator.createConsumer(kafkaConfig.getProperty("TOPIC_NAME_url"), "url");
        // for save urls that checked i use Redis
        RedisConnection redisConnection = new RedisConnection();
        for (int i = 0; i < Integer.parseInt(appConfig.getProperty("threadnumber")); i++) {
            Thread thread = new Thread(new Crawler(ProducerUrls, ConsumerUrls, redisConnection, ProducerText));
            thread.start();
        }
    }
}