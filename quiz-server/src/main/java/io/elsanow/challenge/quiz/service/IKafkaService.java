package io.elsanow.challenge.quiz.service;

import io.elsanow.challenge.quiz.dto.event.RealtimeEventDto;
import io.elsanow.challenge.quiz.dto.event.payload.BaseEventDto;

public interface IKafkaService {

    void sendMessage(String message);

    void sendNewParticipantJoined(RealtimeEventDto<? extends BaseEventDto> dto);
}
