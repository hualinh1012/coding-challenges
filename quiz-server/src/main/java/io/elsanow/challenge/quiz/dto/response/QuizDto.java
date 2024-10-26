package io.elsanow.challenge.quiz.dto.response;

import io.elsanow.challenge.quiz.domain.enumeration.QuizStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QuizDto {
    private String quizId;
    private String title;
    private String description;
    private QuizStatus status;
    private List<String> participants;
}
