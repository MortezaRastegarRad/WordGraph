package cs.sbu.Processing;

import cs.sbu.config.KafkaConfig;
import cs.sbu.connection.SqlConnection;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import smile.nlp.NGram;
import smile.nlp.keyword.CooccurrenceKeywordExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.sql.*;


public class Processig implements Runnable {

    KafkaConsumer consumerText;
    KafkaProducer producerWord;
    KafkaConfig kafkaConfig;
    Connection conn = null;
    SqlConnection sql = new SqlConnection(conn);


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
            try {
                process(text.getText(), producerWord, text.getId(), text.getRelations());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void process(String text, KafkaProducer producerWord, long id, ArrayList<Long> relations) throws SQLException {
        CooccurrenceKeywordExtractor ex = new CooccurrenceKeywordExtractor();
        ArrayList<NGram> extract_words = ex.extract(text);
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < extract_words.size(); i++) {
            words.add(Arrays.toString(extract_words.get(i).words));
        }
        Word word = new Word(words, id, relations);
        synchronized (sql) {
            sql.Insert(words , id, relations, conn);
        }
    }
}