package io.elsanow.challenge.quiz.controller;

import io.elsanow.challenge.quiz.dto.request.AnswerDto;
import io.elsanow.challenge.quiz.dto.request.SignInDto;
import io.elsanow.challenge.quiz.dto.response.AnswerResultDto;
import io.elsanow.challenge.quiz.dto.response.NextQuestionDto;
import io.elsanow.challenge.quiz.dto.response.QuizDto;
import io.elsanow.challenge.quiz.service.IQuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final IQuizService quizService;

    public QuizController(IQuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<QuizDto> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(quizService.signIn(signInDto));
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDto> getQuiz(@PathVariable("quizId") String quizId) {
        return ResponseEntity.ok(quizService.getQuiz(quizId));
    }

    @PostMapping("/{quizId}/start")
    public ResponseEntity<Void> startQuiz(@PathVariable("quizId") String quizId) {
        quizService.startQuiz(quizId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{quizId}/question/next")
    public ResponseEntity<NextQuestionDto> nextQuestion(@PathVariable("quizId") String quizId) {
        return ResponseEntity.ok(quizService.nextQuestion(quizId));
    }

    @PostMapping("/{quizId}/question/answer")
    public ResponseEntity<AnswerResultDto> answerQuestion(@PathVariable("quizId") String quizId, @RequestBody AnswerDto answer) {
        return ResponseEntity.ok(quizService.answerQuestion(quizId, answer));
    }
}
