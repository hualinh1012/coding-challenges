package io.elsanow.challenge.quiz.adapter;

import io.elsanow.challenge.quiz.domain.bo.Option;
import io.elsanow.challenge.quiz.domain.bo.Question;
import io.elsanow.challenge.quiz.domain.entity.OptionEntity;
import io.elsanow.challenge.quiz.domain.entity.QuestionEntity;
import io.elsanow.challenge.quiz.dto.response.NextOptionDto;

import java.util.ArrayList;
import java.util.List;

public class QuizOptionAdapter {
    public static Option toBO (OptionEntity entity) {
        Option option = new Option();
        option.setOptionId(entity.getId());
        option.setOptionText(entity.getOptionText());
        option.setIsCorrect(entity.isCorrect());
        return option;
    }

    public static List<Option> toBOs(List<OptionEntity> entities) {
        List<Option> options = new ArrayList<>();
        if (entities != null && !entities.isEmpty()) {
            entities.forEach(entity -> options.add(toBO(entity)));
        }
        return options;
    }

    public static NextOptionDto toDTO(Option option) {
        if (option == null) {
            return null;
        }
        NextOptionDto dto = new NextOptionDto();
        dto.setOptionId(option.getOptionId());
        dto.setOptionText(option.getOptionText());
        dto.setIsCorrect(option.getIsCorrect());
        return dto;
    }

    public static List<NextOptionDto> toDTOs(List<Option> options) {
        List<NextOptionDto> dtos = new ArrayList<>();
        if (options != null && !options.isEmpty()) {
            options.forEach(option -> dtos.add(toDTO(option)));
        }
        return dtos;
    }
}
