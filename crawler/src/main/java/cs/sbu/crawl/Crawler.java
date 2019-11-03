package cs.sbu.crawl;

import cs.sbu.config.KafkaConfig;
import cs.sbu.connection.RedisConnection;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
import java.util.ArrayList;

public class Crawler implements Runnable {

    RedisConnection redisConnection;
    KafkaConsumer consumerUrl;
    KafkaProducer producerUrl;
    KafkaProducer producerText;
    ArrayList<Long> relations;
    private static long relation = 2;
    private static final Logger LOGGER = Logger.getLogger(Crawler.class);
    KafkaConfig kafkaConfig = new KafkaConfig();

    public Crawler(KafkaProducer producer, KafkaConsumer consumer, RedisConnection redisConnection, KafkaProducer producerText) throws IOException {
        this.relations = new ArrayList<>();
        this.producerUrl = producer;
        this.producerText = producerText;
        this.redisConnection = redisConnection;
        this.consumerUrl = consumer;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords records = consumerUrl.poll(100);
            Url url = null;
            for (Object record : records) {
                url = (Url) record;
            }
            try {
                assert url != null;
                if (!redisConnection.isExist(getDomainName(url.getUrl()))) {
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(url.getUrl()).get();
                        Elements links = doc.select("a[href]");
                        synchronized (this) {
                            this.relations.clear();
                            for (Element link : links) {
                                relations.add(relation);
                                Url urls = new Url(link.attr("abs:href"), relation);
                                this.producerUrl.send(new ProducerRecord<Long, Url>(kafkaConfig.getProperty("TOPIC_NAME_url"), urls));
                                relation++;
                            }
                            Text instance = new Text(doc.text(), url.getId(), relations);
                            this.producerText.send(new ProducerRecord<Long, Text>(kafkaConfig.getProperty("TOPIC_NAME_text"), instance));
                            producerUrl.flush();
                            producerText.flush();
                        }
                        redisConnection.setEntity(getDomainName(url.getUrl()));
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            System.out.println("url : " + url.getUrl());
            System.out.println("_____________________________________________________________________________________");
        }
    }

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}