package io.elsanow.challenge.quiz.dto.response;

import io.elsanow.challenge.quiz.domain.enumeration.QuestionType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NextQuestionDto {
    private Long quizId;
    private Long questionId;
    private String questionText;
    private QuestionType questionType;
    private List<NextOptionDto> options;
    private Integer duration;
}
