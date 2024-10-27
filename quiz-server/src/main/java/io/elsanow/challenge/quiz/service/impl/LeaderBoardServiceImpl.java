package io.elsanow.challenge.quiz.service.impl;

import io.elsanow.challenge.quiz.dto.event.RealtimeEventDto;
import io.elsanow.challenge.quiz.dto.event.payload.LeaderBoardChangedDto;
import io.elsanow.challenge.quiz.dto.response.LeaderBoardDto;
import io.elsanow.challenge.quiz.dto.response.UserScoreDto;
import io.elsanow.challenge.quiz.service.IKafkaService;
import io.elsanow.challenge.quiz.service.ILeaderBoardService;
import io.elsanow.challenge.quiz.service.IQuizService;
import io.elsanow.challenge.quiz.service.IRedisService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeaderBoardServiceImpl implements ILeaderBoardService {

    private static final String LEADER_BOARD = "leaderboard-";

    private final IRedisService redisService;
    private final IKafkaService kafkaService;

    public LeaderBoardServiceImpl(IRedisService redisService, IKafkaService kafkaService) {
        this.redisService = redisService;
        this.kafkaService = kafkaService;
    }

    @Override
    public void addUser(String quizId, String userName) {
        redisService.addZSetScore(LEADER_BOARD + quizId, userName, 0);
    }

    @Override
    public void addScore(String quizId, String userName, int score) {
        redisService.increaseZSetScore(LEADER_BOARD + quizId, userName, score);
        kafkaService.sendRealtimeEvent(new RealtimeEventDto<>(new LeaderBoardChangedDto(quizId, getLeaderBoard(quizId))));
    }

    @Override
    public LeaderBoardDto getLeaderBoard(String quizId) {
        List<UserScoreDto> scores = new ArrayList<>();
        Set<String> members = redisService.getZSet(LEADER_BOARD + quizId, 0, -1);
        if (members != null) {
            for (String member : members) {
                Double score = redisService.getScore(LEADER_BOARD + quizId, member);
                if (score != null) {
                    scores.add(new UserScoreDto(member, score.intValue()));
                }
            }
        }
        return new LeaderBoardDto(scores);
    }

}
