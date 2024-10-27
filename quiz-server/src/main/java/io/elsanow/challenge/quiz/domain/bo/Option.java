package io.elsanow.challenge.quiz.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private Long optionId;
    private String optionText;
    private Boolean isCorrect;
}
