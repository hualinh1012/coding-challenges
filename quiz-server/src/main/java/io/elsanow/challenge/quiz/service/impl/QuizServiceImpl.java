package io.elsanow.challenge.quiz.service.impl;

import io.elsanow.challenge.exception.UserException;
import io.elsanow.challenge.quiz.domain.entity.Quiz;
import io.elsanow.challenge.quiz.domain.enumeration.QuizStatus;
import io.elsanow.challenge.quiz.dto.request.SignInDto;
import io.elsanow.challenge.quiz.dto.response.QuizDto;
import io.elsanow.challenge.quiz.repository.QuizRepository;
import io.elsanow.challenge.quiz.service.IQuizService;
import io.elsanow.challenge.quiz.service.IKafkaService;
import io.elsanow.challenge.quiz.service.IRedisService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements IQuizService {

    private static final String DEFAULT_QUIZ_ID = "DEFAULT";

    private static final String QUIZ = "quiz-";
    private static final String QUIZ_PARTICIPANTS = "participants-";
    private static final String LEADER_BOARD = "leaderboard-";

    private final QuizRepository quizRepository;
    private final IRedisService redisService;
    private final IKafkaService kafkaService;

    public QuizServiceImpl(QuizRepository quizRepository, IRedisService redisService, IKafkaService kafkaService) {
        this.quizRepository = quizRepository;
        this.redisService = redisService;
        this.kafkaService = kafkaService;
    }

    @Override
    public QuizDto signIn(SignInDto signInDto) {
        if (signInDto.getQuizId() == null || signInDto.getQuizId().isEmpty()
        || signInDto.getUserName() == null || signInDto.getUserName().isEmpty()) {
            throw new UserException("QuizId and UserName are required");
        }
        QuizStatus quizStatus = getQuizStatus(signInDto.getQuizId());
        if (QuizStatus.STARTED.equals(quizStatus)) {
            throw new UserException("Quiz already started!");
        }
        setQuizStatus(signInDto.getQuizId(), QuizStatus.WAITING);

        if (isUserNameDuplicated(signInDto.getQuizId(), signInDto.getUserName())) {
            throw new UserException("Username already exists!");
        }
        addQuizParticipants(signInDto.getQuizId(), signInDto.getUserName());

        Quiz quiz = quizRepository.findByReferenceId(signInDto.getQuizId());
        if (quiz == null) {
            quiz = quizRepository.findByReferenceId(DEFAULT_QUIZ_ID);
        }

        return QuizDto.builder()
                .quizId(signInDto.getQuizId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .status(getQuizStatus(signInDto.getQuizId()))
                .participants(getQuizParticipants(signInDto.getQuizId()))
                .build();
    }

    @Override
    public QuizDto getQuiz(String quizId) {
        kafkaService.sendMessage("quizId");
        Quiz quiz = quizRepository.findByReferenceId(quizId);
        if (quiz == null) {
            quiz = quizRepository.findByReferenceId(DEFAULT_QUIZ_ID);
        }
        return QuizDto.builder()
                .quizId(quizId)
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .status(getQuizStatus(quizId))
                .participants(getQuizParticipants(quizId))
                .build();
    }

    @Override
    public void startQuiz(String quizId) {
        if (!getQuizParticipants(quizId).isEmpty()) {
            setQuizStatus(quizId, QuizStatus.WAITING);
        }
        //TODO add more event to start
    }

    private QuizStatus getQuizStatus(String quizId) {
        String quizStatus = redisService.getValue(QUIZ + quizId);
        if (quizStatus == null) {
            return null;
        }
        return QuizStatus.valueOf(quizStatus);
    }

    private void setQuizStatus(String quizId, QuizStatus quizStatus) {
        redisService.setValue(QUIZ + quizId, quizStatus.name());
    }

    private boolean isUserNameDuplicated(String quizId, String userName) {
        return redisService.isElementInList(QUIZ_PARTICIPANTS + quizId, userName);
    }

    private void addQuizParticipants(String quizId, String userName) {
        redisService.addToList(QUIZ_PARTICIPANTS + quizId, userName);
    }

    private List<String> getQuizParticipants(String quizId) {
        return redisService.getList(QUIZ_PARTICIPANTS + quizId);
    }
}
