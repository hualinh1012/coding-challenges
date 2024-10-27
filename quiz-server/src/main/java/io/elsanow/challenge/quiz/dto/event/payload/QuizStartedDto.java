package io.elsanow.challenge.quiz.dto.event.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuizStartedDto extends BaseEventDto {

    private String quizId;

    public QuizStartedDto(String quizId) {
        super("quiz-started");
        this.quizId = quizId;
    }
}
