package io.elsanow.challenge.quiz.adapter;

import io.elsanow.challenge.quiz.domain.bo.Option;
import io.elsanow.challenge.quiz.domain.entity.OptionEntity;
import io.elsanow.challenge.quiz.dto.response.OptionDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizOptionAdapterTest {

    @Test
    void testToBO() {
        OptionEntity entity = new OptionEntity();
        entity.setId(1L);
        entity.setOptionText("Option Text");
        entity.setCorrect(true);

        Option option = QuizOptionAdapter.toBO(entity);

        assertNotNull(option);
        assertEquals(entity.getId(), option.getOptionId());
        assertEquals(entity.getOptionText(), option.getOptionText());
        assertEquals(entity.isCorrect(), option.getIsCorrect());
    }

    @Test
    void testToBOs() {
        OptionEntity entity1 = new OptionEntity();
        entity1.setId(1L);
        entity1.setOptionText("Option 1");
        entity1.setCorrect(true);

        OptionEntity entity2 = new OptionEntity();
        entity2.setId(2L);
        entity2.setOptionText("Option 2");
        entity2.setCorrect(false);

        List<OptionEntity> entities = Arrays.asList(entity1, entity2);
        List<Option> options = QuizOptionAdapter.toBOs(entities);

        assertNotNull(options);
        assertEquals(2, options.size());

        assertEquals(entity1.getId(), options.get(0).getOptionId());
        assertEquals(entity1.getOptionText(), options.get(0).getOptionText());
        assertEquals(entity1.isCorrect(), options.get(0).getIsCorrect());

        assertEquals(entity2.getId(), options.get(1).getOptionId());
        assertEquals(entity2.getOptionText(), options.get(1).getOptionText());
        assertEquals(entity2.isCorrect(), options.get(1).getIsCorrect());
    }

    @Test
    void testToDTO() {
        Option option = new Option();
        option.setOptionId(1L);
        option.setOptionText("Option Text");
        option.setIsCorrect(true);

        OptionDto dto = QuizOptionAdapter.toDTO(option);

        assertNotNull(dto);
        assertEquals(option.getOptionId(), dto.getOptionId());
        assertEquals(option.getOptionText(), dto.getOptionText());
        assertEquals(option.getIsCorrect(), dto.getIsCorrect());
    }

    @Test
    void testToDTOs() {
        Option option1 = new Option();
        option1.setOptionId(1L);
        option1.setOptionText("Option 1");
        option1.setIsCorrect(true);

        Option option2 = new Option();
        option2.setOptionId(2L);
        option2.setOptionText("Option 2");
        option2.setIsCorrect(false);

        List<Option> options = Arrays.asList(option1, option2);
        List<OptionDto> dtos = QuizOptionAdapter.toDTOs(options);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(option1.getOptionId(), dtos.get(0).getOptionId());
        assertEquals(option1.getOptionText(), dtos.get(0).getOptionText());
        assertEquals(option1.getIsCorrect(), dtos.get(0).getIsCorrect());

        assertEquals(option2.getOptionId(), dtos.get(1).getOptionId());
        assertEquals(option2.getOptionText(), dtos.get(1).getOptionText());
        assertEquals(option2.getIsCorrect(), dtos.get(1).getIsCorrect());
    }

    @Test
    void testToDTO_NullOption() {
        OptionDto dto = QuizOptionAdapter.toDTO(null);
        assertNull(dto);
    }

    @Test
    void testToBOs_EmptyList() {
        List<Option> options = QuizOptionAdapter.toBOs(null);
        assertNotNull(options);
        assertTrue(options.isEmpty());
    }

    @Test
    void testToDTOs_EmptyList() {
        List<OptionDto> dtos = QuizOptionAdapter.toDTOs(null);
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }
}