package io.elsanow.challenge.quiz.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.elsanow.challenge.quiz.dto.event.RealtimeEventDto;
import io.elsanow.challenge.quiz.dto.event.payload.BaseEventDto;
import io.elsanow.challenge.quiz.dto.event.payload.ParticipantJoinedDto;
import io.elsanow.challenge.quiz.service.IKafkaService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements IKafkaService {

    @Value("${kafka.producer.topic}")
    private String topic;

    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper;

    public KafkaServiceImpl(KafkaProducer<String, String> kafkaProducer) {
        this.producer = kafkaProducer;
        this.objectMapper = new ObjectMapper();
    }

    public void sendMessage(String message) {
        producer.send(new ProducerRecord<>(topic, message));
    }

    public void sendRealtimeEvent(RealtimeEventDto<? extends BaseEventDto> dto) {
        producer.send(new ProducerRecord<>(topic, toJson(dto)));
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            return null;
        }
    }
}
