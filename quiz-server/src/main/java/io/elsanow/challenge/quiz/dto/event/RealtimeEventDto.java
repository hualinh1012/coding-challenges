package io.elsanow.challenge.quiz.dto.event;

import io.elsanow.challenge.quiz.dto.event.payload.BaseEventDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealtimeEventDto<T extends BaseEventDto> {
    private String type;
    private T payload;

    public RealtimeEventDto(T payload) {
        this.type = payload.getEventId();
        this.payload = payload;
    }
}
