package io.elsanow.challenge.quiz.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    private String userName;
    private String quizId;
}
