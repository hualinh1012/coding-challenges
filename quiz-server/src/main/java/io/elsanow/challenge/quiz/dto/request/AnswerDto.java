package io.elsanow.challenge.quiz.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {
    private String userName;
    private Long questionId;
    private Long optionId;
}
