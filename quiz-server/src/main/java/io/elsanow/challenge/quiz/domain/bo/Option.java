package io.elsanow.challenge.quiz.domain.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Option {
    private Long optionId;
    private String optionText;
    private Boolean isCorrect;
}
