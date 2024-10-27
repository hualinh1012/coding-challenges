package io.elsanow.challenge.quiz.domain.bo;

import io.elsanow.challenge.quiz.domain.enumeration.QuestionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Question {
    private Long quizId;
    private Long questionId;
    private String questionText;
    private QuestionType questionType;
    private List<Option> options;

    public Question(Long questionId, String questionText, List<Option> options) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = options;
    }
}
