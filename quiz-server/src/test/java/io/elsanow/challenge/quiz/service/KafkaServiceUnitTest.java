package io.elsanow.challenge.quiz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.elsanow.challenge.quiz.dto.event.RealtimeEventDto;
import io.elsanow.challenge.quiz.dto.event.payload.BaseEventDto;
import io.elsanow.challenge.quiz.dto.event.payload.QuizStartedDto;
import io.elsanow.challenge.quiz.service.impl.KafkaServiceImpl;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class KafkaServiceUnitTest {

    @Mock
    private KafkaProducer<String, String> producer;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaServiceImpl kafkaService;

    private final String topic = "test-topic";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(kafkaService, "topic", topic);
    }

    @Test
    void testSendMessage() {
        // Arrange
        String message = "test message";

        // Act
        kafkaService.sendMessage(message);

        // Assert
        ArgumentCaptor<ProducerRecord<String, String>> recordCaptor = ArgumentCaptor.forClass(ProducerRecord.class);
        verify(producer).send(recordCaptor.capture());

        ProducerRecord<String, String> record = recordCaptor.getValue();
        assertEquals(topic, record.topic());
        assertEquals(message, record.value());
    }

    @Test
    void testSendRealtimeEvent() throws JsonProcessingException {
        // Arrange
        RealtimeEventDto<BaseEventDto> dto = new RealtimeEventDto<>(new QuizStartedDto("quiz"));
        String dtoJson = "{\"type\":\"quiz-started\",\"payload\":{\"class\":\"QuizStartedDto\",\"eventId\":\"quiz-started\",\"quizId\":\"quiz\"}}";

        when(objectMapper.writeValueAsString(dto)).thenReturn(dtoJson);

        // Act
        kafkaService.sendRealtimeEvent(dto);

        // Assert
        ArgumentCaptor<ProducerRecord<String, String>> recordCaptor = ArgumentCaptor.forClass(ProducerRecord.class);
        verify(producer).send(recordCaptor.capture());

        ProducerRecord<String, String> record = recordCaptor.getValue();
        assertEquals(topic, record.topic());
        assertEquals(dtoJson, record.value());
    }

}
