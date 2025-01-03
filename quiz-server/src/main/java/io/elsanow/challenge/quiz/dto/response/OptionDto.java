package io.elsanow.challenge.quiz.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OptionDto {
    private Long optionId;
    private String optionText;
    private Boolean isCorrect;
}
