package io.elsanow.challenge.quiz.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QuestionType {
    MULTIPLE_CHOICE("Multiple choice"),
    TRUE_FALSE("True or False"),
    SHORT_ANSWER("Short answer");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}