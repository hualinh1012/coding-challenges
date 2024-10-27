package io.elsanow.challenge.quiz.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.elsanow.challenge.exception.UserException;
import io.elsanow.challenge.quiz.adapter.QuizQuestionAdapter;
import io.elsanow.challenge.quiz.domain.bo.Option;
import io.elsanow.challenge.quiz.domain.bo.Question;
import io.elsanow.challenge.quiz.domain.entity.QuizEntity;
import io.elsanow.challenge.quiz.domain.enumeration.QuizStatus;
import io.elsanow.challenge.quiz.dto.event.RealtimeEventDto;
import io.elsanow.challenge.quiz.dto.event.payload.ParticipantJoinedDto;
import io.elsanow.challenge.quiz.dto.event.payload.QuizStartedDto;
import io.elsanow.challenge.quiz.dto.request.AnswerDto;
import io.elsanow.challenge.quiz.dto.request.SignInDto;
import io.elsanow.challenge.quiz.dto.response.AnswerResultDto;
import io.elsanow.challenge.quiz.dto.response.QuestionDto;
import io.elsanow.challenge.quiz.dto.response.QuizDto;
import io.elsanow.challenge.quiz.repository.QuizRepository;
import io.elsanow.challenge.quiz.service.ILeaderBoardService;
import io.elsanow.challenge.quiz.service.IQuizService;
import io.elsanow.challenge.quiz.service.IKafkaService;
import io.elsanow.challenge.quiz.service.IRedisService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements IQuizService {

    private static final String DEFAULT_QUIZ_ID = "DEFAULT";

    private static final String QUIZ = "quiz-";
    private static final String QUIZ_PARTICIPANTS = "participants-";
    private static final String QUIZ_QUESTION = "question-";
    private static final String CURRENT_QUESTION = "current-question-";
    private static final String CURRENT_QUESTION_COUNTER = "current-question-counter";

    private final QuizRepository quizRepository;
    private final IRedisService redisService;
    private final IKafkaService kafkaService;
    private final ILeaderBoardService leaderBoardService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public QuizServiceImpl(
            QuizRepository quizRepository,
            IRedisService redisService,
            IKafkaService kafkaService,
            ILeaderBoardService leaderBoardService) {
        this.quizRepository = quizRepository;
        this.redisService = redisService;
        this.kafkaService = kafkaService;
        this.leaderBoardService = leaderBoardService;
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

        QuizEntity quizEntity = quizRepository.findByReferenceId(signInDto.getQuizId());
        if (quizEntity == null) {
            quizEntity = quizRepository.findByReferenceId(DEFAULT_QUIZ_ID);
        }

        leaderBoardService.addUser(signInDto.getQuizId(), signInDto.getUserName());
        RealtimeEventDto<ParticipantJoinedDto> event = new RealtimeEventDto<>(
                new ParticipantJoinedDto(signInDto.getQuizId(), getQuizParticipants(signInDto.getQuizId()))
        );
        kafkaService.sendRealtimeEvent(event);

        return QuizDto.builder()
                .quizId(signInDto.getQuizId())
                .title(quizEntity.getTitle())
                .description(quizEntity.getDescription())
                .status(getQuizStatus(signInDto.getQuizId()))
                .participants(getQuizParticipants(signInDto.getQuizId()))
                .build();
    }

    @Override
    public QuizDto getQuiz(String quizId) {
        QuizEntity quizEntity = quizRepository.findByReferenceId(quizId);
        if (quizEntity == null) {
            quizEntity = quizRepository.findByReferenceId(DEFAULT_QUIZ_ID);
        }
        return QuizDto.builder()
                .quizId(quizId)
                .title(quizEntity.getTitle())
                .description(quizEntity.getDescription())
                .status(getQuizStatus(quizId))
                .participants(getQuizParticipants(quizId))
                .build();
    }

    @Override
    public void startQuiz(String quizId) {
        if (!getQuizParticipants(quizId).isEmpty()) {
            QuizEntity quizEntity = quizRepository.findByReferenceId(quizId);
            if (quizEntity == null) {
                quizEntity = quizRepository.findByReferenceId(DEFAULT_QUIZ_ID);
            }

            setQuizStatus(quizId, QuizStatus.STARTED);
            setCurrentQuizQuestionId(quizId, 1);
            setQuizQuestions(quizId, QuizQuestionAdapter.toBOMap(quizEntity.getQuestions()));

            kafkaService.sendRealtimeEvent(new RealtimeEventDto<>(new QuizStartedDto(quizId)));
        }
    }

    @Override
    public QuestionDto getQuestion(String quizId) {
        QuizStatus status = getQuizStatus(quizId);
        if (QuizStatus.WAITING.equals(status)) {
            throw new UserException("Quiz has not started yet!");
        }
        if (QuizStatus.FINISHED.equals(status)) {
            return QuestionDto.builder().isFinished(true).build();
        }
        int currentQuestionId = getCurrentQuizQuestionId(quizId);
        Map<Long, Question> questions = getQuizQuestions(quizId);
        int duration = getCurrentQuizQuestionCounter(quizId);

        if (duration <= 0) {
            ++currentQuestionId;
            setCurrentQuizQuestionId(quizId, currentQuestionId);
        }

        Question question = questions.get((long) currentQuestionId);
        duration = getCurrentQuizQuestionCounter(quizId);
        if (question == null) {
            setQuizStatus(quizId, QuizStatus.FINISHED);
            return QuestionDto.builder().isFinished(true).build();
        }

        return QuizQuestionAdapter.toDTO(question, duration);
    }

    @Override
    public AnswerResultDto answerQuestion(String quizId, AnswerDto answer) {
        String userName = answer.getUserName();
        Long questionId = answer.getQuestionId();
        Long optionId = answer.getOptionId();
        if (quizId == null || quizId.isEmpty()
                || userName == null || userName.isEmpty()
                || questionId == null || optionId == null) {
            throw new UserException("Invalid parameters");
        }

        Map<Long, Question> questions = getQuizQuestions(quizId);
        if (questions.isEmpty() || !questions.containsKey(questionId)) {
            throw new UserException("This quiz does not have any questions");
        }

        List<Option> options = questions.get(questionId).getOptions();
        Option userOption = options.stream().filter(o -> o.getOptionId().equals(optionId)).findFirst().orElse(null);
        Option correctOption = options.stream().filter(Option::getIsCorrect).findFirst().orElseThrow(() -> new UserException("Question is corrupted!"));
        boolean isCorrect = userOption != null && correctOption.getOptionId().equals(optionId);

        int currentQuestionId = getCurrentQuizQuestionId(quizId);
        if (currentQuestionId == questionId.intValue()) {
            if (isCorrect) {
                leaderBoardService.addScore(quizId, userName, 1);
            }
            return new AnswerResultDto(questionId, correctOption.getOptionId(), isCorrect);
        }
        return new AnswerResultDto(questionId, correctOption.getOptionId(), false);
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

    private void setQuizQuestions(String quizId, Map<Long, Question> questions) {
        try {
            String questionAsString = objectMapper.writeValueAsString(questions);
            redisService.setValue(QUIZ_QUESTION + quizId, questionAsString);
        } catch (JsonProcessingException e) {
            throw new UserException(e.getMessage());
        }
    }

    public Map<Long, Question> getQuizQuestions(String quizId) {
        try {
            String questionAsString = redisService.getValue(QUIZ_QUESTION + quizId);
            return objectMapper.readValue(questionAsString, new TypeReference<Map<Long, Question>>() {
            });
        } catch (JsonProcessingException e) {
            throw new UserException(e.getMessage());
        }
    }

    private void setCurrentQuizQuestionId(String quizId, int questionId) {
        redisService.setValue(CURRENT_QUESTION + quizId, String.valueOf(questionId));
        redisService.setValueWithExpire(CURRENT_QUESTION_COUNTER + quizId, String.valueOf(questionId), 10);
    }

    public int getCurrentQuizQuestionId(String quizId) {
        String id = redisService.getValue(CURRENT_QUESTION + quizId);
        if (id == null) {
            return 0;
        }
        return Integer.parseInt(id);
    }

    private int getCurrentQuizQuestionCounter(String quizId) {
        Long ttl = redisService.getTTL(CURRENT_QUESTION_COUNTER + quizId);
        return ttl.intValue();
    }
}
