package io.elsanow.challenge.quiz.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NextOptionDto {
    private Long optionId;
    private String optionText;
    private Boolean isCorrect;
}
