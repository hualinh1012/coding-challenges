package io.elsanow.challenge.quiz.service;

public interface IKafkaService {
    void sendMessage(String message);
}
