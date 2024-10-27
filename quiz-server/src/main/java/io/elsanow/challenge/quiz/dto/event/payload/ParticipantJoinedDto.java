package io.elsanow.challenge.quiz.dto.event.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParticipantJoinedDto extends BaseEventDto {

    private String quizId;

    private List<String> participants;

    public ParticipantJoinedDto(String quizId, List<String> participants) {
        super("new-participant-joined");
        this.quizId = quizId;
        this.participants = participants;
    }
}
