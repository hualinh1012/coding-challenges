package io.elsanow.challenge.quiz.service;

import io.elsanow.challenge.quiz.dto.request.SignInDto;
import io.elsanow.challenge.quiz.dto.response.QuizDto;

public interface IQuizService {
    QuizDto signIn(SignInDto signInDto);

    QuizDto getQuiz(String quizId);

    void startQuiz(String quizId);
}
