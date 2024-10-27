package io.elsanow.challenge.quiz.dto.response;

import io.elsanow.challenge.quiz.domain.enumeration.QuestionType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionDto {
    private Long quizId;
    private Long questionId;
    private String questionText;
    private QuestionType questionType;
    private List<OptionDto> options;
    private Integer duration;
    private AnswerResultDto userAnswer;
    private Boolean isFinished = false;
}
