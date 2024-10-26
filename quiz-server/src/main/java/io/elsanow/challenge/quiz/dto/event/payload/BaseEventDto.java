package io.elsanow.challenge.quiz.dto.event.payload;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BaseEventDto.class, name = "child")
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEventDto {
    private String eventId;
}
