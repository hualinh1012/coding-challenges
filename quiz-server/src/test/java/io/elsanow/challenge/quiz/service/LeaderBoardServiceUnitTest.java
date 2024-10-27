package io.elsanow.challenge.quiz.service;

import io.elsanow.challenge.quiz.dto.event.RealtimeEventDto;
import io.elsanow.challenge.quiz.dto.event.payload.LeaderBoardChangedDto;
import io.elsanow.challenge.quiz.dto.response.LeaderBoardDto;
import io.elsanow.challenge.quiz.dto.response.UserScoreDto;
import io.elsanow.challenge.quiz.service.impl.LeaderBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LeaderBoardServiceUnitTest {
    @Mock
    private IRedisService redisService;

    @Mock
    private IKafkaService kafkaService;

    @InjectMocks
    private LeaderBoardServiceImpl leaderBoardService;

    private final String quizId = "quiz123";
    private final String userName = "testUser";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        // Act
        leaderBoardService.addUser(quizId, userName);

        // Assert
        verify(redisService).addZSetScore("leaderboard-" + quizId, userName, 0);
    }

    @Test
    void testAddScore() {
        // Arrange
        int score = 10;

        // Act
        leaderBoardService.addScore(quizId, userName, score);

        // Assert
        verify(redisService).increaseZSetScore("leaderboard-" + quizId, userName, score);

        // Verify that sendRealtimeEvent was called with correct parameters
        ArgumentCaptor<RealtimeEventDto<LeaderBoardChangedDto>> captor = ArgumentCaptor.forClass(RealtimeEventDto.class);
        verify(kafkaService).sendRealtimeEvent(captor.capture());

        RealtimeEventDto<LeaderBoardChangedDto> event = captor.getValue();
        assertEquals(quizId, event.getPayload().getQuizId());
        assertEquals(leaderBoardService.getLeaderBoard(quizId).getLeaderboard().size(), event.getPayload().getLeaderBoard().getLeaderboard().size());
    }

    @Test
    void testGetLeaderBoard() {
        // Arrange
        Set<String> members = new HashSet<>();
        members.add(userName);
        when(redisService.getZSet("leaderboard-" + quizId, 0, -1)).thenReturn(members);
        when(redisService.getScore("leaderboard-" + quizId, userName)).thenReturn(100.0);

        // Act
        LeaderBoardDto leaderBoard = leaderBoardService.getLeaderBoard(quizId);

        // Assert
        List<UserScoreDto> expectedScores = List.of(new UserScoreDto(userName, 100));
        assertEquals(expectedScores.size(), leaderBoard.getLeaderboard().size());
        assertEquals(expectedScores.getFirst().getUserName(), leaderBoard.getLeaderboard().getFirst().getUserName());
    }

    @Test
    void testGetLeaderBoardWithNullMembers() {
        // Arrange
        when(redisService.getZSet("leaderboard-" + quizId, 0, -1)).thenReturn(null);

        // Act
        LeaderBoardDto leaderBoard = leaderBoardService.getLeaderBoard(quizId);

        // Assert
        assertEquals(List.of(), leaderBoard.getLeaderboard());
    }

}

