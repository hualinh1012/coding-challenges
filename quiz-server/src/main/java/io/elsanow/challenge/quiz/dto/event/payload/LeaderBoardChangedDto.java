package io.elsanow.challenge.quiz.dto.event.payload;

import io.elsanow.challenge.quiz.dto.response.LeaderBoardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LeaderBoardChangedDto extends BaseEventDto {

    private String quizId;

    private LeaderBoardDto leaderBoard;

    public LeaderBoardChangedDto(String quizId, LeaderBoardDto leaderBoard) {
        super("leader-board-changed");
        this.quizId = quizId;
        this.leaderBoard = leaderBoard;
    }
}
