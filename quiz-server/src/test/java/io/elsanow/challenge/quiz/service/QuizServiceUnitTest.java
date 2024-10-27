package io.elsanow.challenge.quiz.service;

import io.elsanow.challenge.exception.UserException;
import io.elsanow.challenge.quiz.domain.bo.Option;
import io.elsanow.challenge.quiz.domain.bo.Question;
import io.elsanow.challenge.quiz.domain.entity.QuizEntity;
import io.elsanow.challenge.quiz.domain.enumeration.QuizStatus;
import io.elsanow.challenge.quiz.dto.event.RealtimeEventDto;
import io.elsanow.challenge.quiz.dto.event.payload.ParticipantJoinedDto;
import io.elsanow.challenge.quiz.dto.request.AnswerDto;
import io.elsanow.challenge.quiz.dto.request.SignInDto;
import io.elsanow.challenge.quiz.dto.response.AnswerResultDto;
import io.elsanow.challenge.quiz.dto.response.QuestionDto;
import io.elsanow.challenge.quiz.dto.response.QuizDto;
import io.elsanow.challenge.quiz.repository.QuizRepository;
import io.elsanow.challenge.quiz.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizServiceUnitTest {
    @Mock
    private QuizRepository quizRepository;

    @Mock
    private IRedisService redisService;

    @Mock
    private IKafkaService kafkaService;

    @Mock
    private ILeaderBoardService leaderBoardService;

    @InjectMocks
    private QuizServiceImpl quizService;

    private final String quizId = "quiz123";
    private final String userName = "testUser";
    private final SignInDto signInDto = new SignInDto(quizId, userName);
    private final QuizEntity quizEntity = new QuizEntity(quizId, "Quiz Title", "Quiz Description", List.of());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignIn_Success() {
        when(quizRepository.findByReferenceId(quizId)).thenReturn(quizEntity);
        when(redisService.getValue("quiz-" + quizId)).thenReturn(QuizStatus.WAITING.name());
        when(redisService.isElementInList("participants-" + quizId, userName)).thenReturn(false);

        QuizDto result = quizService.signIn(signInDto);

        assertEquals(quizId, result.getQuizId());
        assertEquals("Quiz Title", result.getTitle());
        assertEquals("Quiz Description", result.getDescription());
        assertEquals(QuizStatus.WAITING, result.getStatus());
        assertTrue(result.getParticipants().isEmpty());

        // Verify that the Kafka event was sent
        ArgumentCaptor<RealtimeEventDto<ParticipantJoinedDto>> captor = ArgumentCaptor.forClass(RealtimeEventDto.class);
        verify(kafkaService).sendRealtimeEvent(captor.capture());
        assertEquals(quizId, captor.getValue().getPayload().getQuizId());
    }

    @Test
    void testSignIn_QuizAlreadyStarted() {
        when(redisService.getValue("quiz-" + quizId)).thenReturn(QuizStatus.STARTED.name());

        UserException exception = assertThrows(UserException.class, () -> quizService.signIn(signInDto));
        assertEquals("Quiz already started!", exception.getMessage());
    }

    @Test
    void testGetQuiz_Success() {
        when(quizRepository.findByReferenceId(quizId)).thenReturn(quizEntity);
        when(redisService.getValue("quiz-"+quizId)).thenReturn("WAITING");

        QuizDto result = quizService.getQuiz(quizId);

        assertEquals(quizId, result.getQuizId());
        assertEquals("Quiz Title", result.getTitle());
        assertEquals("Quiz Description", result.getDescription());
        assertEquals(QuizStatus.WAITING, result.getStatus());
        assertTrue(result.getParticipants().isEmpty());
    }

    @Test
    void testStartQuiz() {
        when(redisService.getValue("quiz-" + quizId)).thenReturn(QuizStatus.WAITING.name());
        when(redisService.getList("participants-" + quizId)).thenReturn(Collections.singletonList("any"));
        when(quizRepository.findByReferenceId(quizId)).thenReturn(quizEntity);

        quizService.startQuiz(quizId);

        verify(redisService).setValue("quiz-" + quizId, QuizStatus.STARTED.name());
        verify(kafkaService).sendRealtimeEvent(any(RealtimeEventDto.class));
    }

    @Test
    void testStartQuiz_NoParticipants() {
        when(redisService.getValue("quiz-" + quizId)).thenReturn(QuizStatus.WAITING.name());
        when(redisService.getList("participants-" + quizId)).thenReturn(Collections.emptyList());

        quizService.startQuiz(quizId);

        verify(redisService, never()).setValue("quiz-" + quizId, QuizStatus.STARTED.name());
    }

    @Test
    void testGetQuestion_QuizNotStarted() {
        when(redisService.getValue("quiz-" + quizId)).thenReturn(QuizStatus.WAITING.name());

        UserException exception = assertThrows(UserException.class, () -> quizService.getQuestion(quizId));
        assertEquals("Quiz has not started yet!", exception.getMessage());
    }

    @Test
    void testGetQuestion_FinishedQuiz() {
        when(redisService.getValue("quiz-" + quizId)).thenReturn(QuizStatus.FINISHED.name());

        QuestionDto result = quizService.getQuestion(quizId);
        assertTrue(result.getIsFinished());
    }

    @Test
    void testAnswerQuestion_InvalidParameters() {
        AnswerDto answerDto = new AnswerDto("", 1L, 2L);
        UserException exception = assertThrows(UserException.class, () -> quizService.answerQuestion(quizId, answerDto));
        assertEquals("Invalid parameters", exception.getMessage());
    }
}
