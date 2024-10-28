package io.elsanow.challenge.quiz.adapter;

import io.elsanow.challenge.quiz.domain.bo.Question;
import io.elsanow.challenge.quiz.domain.entity.QuestionEntity;
import io.elsanow.challenge.quiz.domain.entity.QuizEntity;
import io.elsanow.challenge.quiz.domain.enumeration.QuestionType;
import io.elsanow.challenge.quiz.dto.response.QuestionDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuizQuestionAdapterTest {

    @Test
    void testToBO_NullEntity() {
        Question question = QuizQuestionAdapter.toBO(null);
        assertNull(question);
    }

    @Test
    void testToBO_ValidEntity() {
        QuestionEntity entity = new QuestionEntity();
        entity.setId(1L);
        entity.setQuestionText("Sample Question?");
        entity.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        entity.setOption(Collections.emptyList());

        QuizEntity quiz = new QuizEntity();
        quiz.setId(1L);
        entity.setQuiz(quiz);

        Question question = QuizQuestionAdapter.toBO(entity);

        assertNotNull(question);
        assertEquals(entity.getQuiz().getId(), question.getQuizId());
        assertEquals(entity.getId(), question.getQuestionId());
        assertEquals(entity.getQuestionText(), question.getQuestionText());
        assertEquals(entity.getQuestionType(), question.getQuestionType());
        assertEquals(0, question.getOptions().size());
    }

    @Test
    void testToBOMap_NullList() {
        Map<Long, Question> questionMap = QuizQuestionAdapter.toBOMap(null);
        assertNotNull(questionMap);
        assertTrue(questionMap.isEmpty());
    }

    @Test
    void testToBOMap_ValidList() {
        QuizEntity quiz = new QuizEntity();
        quiz.setId(1L);

        QuestionEntity entity1 = new QuestionEntity();
        entity1.setId(1L);
        entity1.setQuiz(quiz);
        entity1.setQuestionText("Question 1?");
        entity1.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        entity1.setOption(Collections.emptyList());

        QuestionEntity entity2 = new QuestionEntity();
        entity2.setId(2L);
        entity2.setQuiz(quiz);
        entity2.setQuestionText("Question 2?");
        entity2.setQuestionType(QuestionType.TRUE_FALSE);
        entity2.setOption(Collections.emptyList());

        List<QuestionEntity> questionEntities = Arrays.asList(entity1, entity2);
        Map<Long, Question> questionMap = QuizQuestionAdapter.toBOMap(questionEntities);

        assertNotNull(questionMap);
        assertEquals(2, questionMap.size());
        assertNotNull(questionMap.get(1L));
        assertNotNull(questionMap.get(2L));
    }

    @Test
    void testToDTO_NullQuestion() {
        QuestionDto dto = QuizQuestionAdapter.toDTO(null, 30);
        assertNull(dto);
    }

    @Test
    void testToDTO_ValidQuestion() {
        Question question = new Question();
        question.setQuizId(1L);
        question.setQuestionId(1L);
        question.setQuestionText("Sample Question?");
        question.setQuestionType(QuestionType.SHORT_ANSWER);
        question.setOptions(Collections.emptyList());

        QuestionDto dto = QuizQuestionAdapter.toDTO(question, 30);

        assertNotNull(dto);
        assertEquals(question.getQuizId(), dto.getQuizId());
        assertEquals(question.getQuestionId(), dto.getQuestionId());
        assertEquals(question.getQuestionText(), dto.getQuestionText());
        assertEquals(question.getQuestionType(), dto.getQuestionType());
        assertEquals(30, dto.getDuration());
        assertEquals(0, dto.getOptions().size());
    }
}