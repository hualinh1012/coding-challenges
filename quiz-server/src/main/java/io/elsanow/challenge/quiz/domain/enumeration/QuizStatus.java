package io.elsanow.challenge.quiz.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum QuizStatus {
    WAITING,
    STARTED,
    FINISHED
    ;

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
