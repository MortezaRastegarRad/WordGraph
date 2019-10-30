package cs.sbu.prossesing;


import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

public class Processig implements Runnable {
    KafkaConsumer consumerText;
    KafkaProducer producerWord;

    public Processig(KafkaProducer producerWord, KafkaConsumer consumerText) {
        this.producerWord = producerWord;
        this.consumerText = consumerText;
    }

    @Override
    public void run() {

    }

}
