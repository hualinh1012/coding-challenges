package io.elsanow.challenge.quiz.service;

import io.elsanow.challenge.quiz.dto.response.LeaderBoardDto;

public interface ILeaderBoardService {

    void addUser(String quizId, String userName);

    void addScore(String quizId, String userName, int score);

    LeaderBoardDto getLeaderBoard(String quizId);
}
