package cs.sbu.crawl;

import cs.sbu.config.KafkaConfig;
import cs.sbu.connection.RedisConnection;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Crawler implements Runnable {

    RedisConnection redisConnection;
    KafkaConsumer consumer;
    KafkaProducer producer;
    KafkaProducer producerText;
    private static final Logger LOGGER = Logger.getLogger(Crawler.class);
    KafkaConfig kafkaConfig = new KafkaConfig();

    public Crawler(KafkaProducer producer, KafkaConsumer consumer, RedisConnection redisConnection, KafkaProducer producerText) throws IOException {
        this.producer = producer;
        this.producerText = producerText;
        this.redisConnection = redisConnection;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName());
            ConsumerRecords record = record = consumer.poll(100);
            String url = record.toString();
            try {
                if (!redisConnection.isExist(getDomainName(url))) {
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(url).get();
                        Elements links = doc.select("a[href]");
                        for (Element link : links) {
                            this.producer.send(new ProducerRecord<Long, String>(kafkaConfig.getProperty("TOPIC_NAME"), link.attr("abs:href")));
                        }
                        producer.flush();
                        redisConnection.setEntity(getDomainName(url));
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    this.producerText.send(new ProducerRecord<Long, String>(kafkaConfig.getProperty("TOPIC_NAME_text"), doc.text()));
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            System.out.println("url : " + url);
            System.out.println("_____________________________________________________________________________________");
        }
    }

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}


