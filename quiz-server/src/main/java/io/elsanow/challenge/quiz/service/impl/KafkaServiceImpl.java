package io.elsanow.challenge.quiz.service.impl;

import io.elsanow.challenge.quiz.service.IKafkaService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements IKafkaService {

    private final KafkaProducer<String, String> producer;

    public KafkaServiceImpl(KafkaProducer<String, String> kafkaProducer) {
        this.producer = kafkaProducer;
    }

    public void sendMessage(String message) {
        producer.send(new ProducerRecord<>("quiz-topic", message));
    }
}
