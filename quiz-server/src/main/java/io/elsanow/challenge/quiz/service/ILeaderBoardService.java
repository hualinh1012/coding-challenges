package io.elsanow.challenge.quiz.service;

public interface ILeaderBoardService {

    void addUser(String userName);

    void addScore(String userName, int score);
}
