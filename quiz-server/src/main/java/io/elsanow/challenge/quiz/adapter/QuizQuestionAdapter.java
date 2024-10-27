package io.elsanow.challenge.quiz.adapter;

import io.elsanow.challenge.quiz.domain.bo.Question;
import io.elsanow.challenge.quiz.domain.entity.QuestionEntity;
import io.elsanow.challenge.quiz.dto.response.NextQuestionDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizQuestionAdapter {
    public static Question toBO(QuestionEntity entity) {
        if (entity == null) {
            return null;
        }
        Question question = new Question();
        question.setQuizId(entity.getQuiz().getId());
        question.setQuestionId(entity.getId());
        question.setQuestionText(entity.getQuestionText());
        question.setQuestionType(entity.getQuestionType());
        question.setOptions(QuizOptionAdapter.toBOs(entity.getOption()));
        return question;
    }

    public static Map<Long, Question> toBOMap(List<QuestionEntity> questionEntities) {
        if (questionEntities == null) {
            return new HashMap<>();
        }
        Map<Long, Question> questions = new HashMap<>();
        for (QuestionEntity questionEntity : questionEntities) {
            questions.put(questionEntity.getId(), toBO(questionEntity));
        }
        return questions;
    }

    public static NextQuestionDto toDTO(Question question, Integer duration) {
        if (question == null) {
            return null;
        }
        return NextQuestionDto.builder()
                .quizId(question.getQuizId())
                .questionId(question.getQuestionId())
                .questionText(question.getQuestionText())
                .questionType(question.getQuestionType())
                .options(QuizOptionAdapter.toDTOs(question.getOptions()))
                .duration(duration)
                .build();
    }
}
