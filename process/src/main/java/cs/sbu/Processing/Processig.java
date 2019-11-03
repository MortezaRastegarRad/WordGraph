package cs.sbu.Processing;

import cs.sbu.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import smile.nlp.NGram;
import smile.nlp.keyword.CooccurrenceKeywordExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Processig implements Runnable {

    KafkaConsumer consumerText;
    KafkaProducer producerWord;
    KafkaConfig kafkaConfig;

    public Processig(KafkaProducer producerWord, KafkaConsumer consumerText) throws IOException {
        this.kafkaConfig = new KafkaConfig();
        this.producerWord = producerWord;
        this.consumerText = consumerText;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Long, Text> records = consumerText.poll(100);
            Text text = null;
            for (Object record : records) {
                text = (Text) record;
            }
            assert text != null;
            process(text.getText(), producerWord, text.getId());
        }
    }

    void process(String text, KafkaProducer producerWord, long id) {
        CooccurrenceKeywordExtractor ex = new CooccurrenceKeywordExtractor();
        ArrayList<NGram> extract = ex.extract(text);
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < extract.size(); i++) {
            words.add(Arrays.toString(extract.get(i).words));
        }
        Word word = new Word(words, id);
        producerWord.send(new ProducerRecord<Long, Word>(this.kafkaConfig.getProperty("TOPIC_NAME_WORD"), word));
    }
}