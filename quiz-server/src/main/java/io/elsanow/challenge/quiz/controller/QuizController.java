package io.elsanow.challenge.quiz.controller;

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
    public ResponseEntity<?> signIn(@RequestParam("userName") String userMame, @RequestParam("quizId") String quizId) {
        return ResponseEntity.ok("");
    }

    @PostMapping("/{quizId}/start")
    public ResponseEntity<?> signIn(@PathVariable("quizId") String quizId) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/{quizId}/question/next")
    public ResponseEntity<?> nextQuestion(@PathVariable("quizId") String quizId) {
        return ResponseEntity.ok("");
    }

    @PostMapping("/{quizId}/question/answer")
    public ResponseEntity<?> answerQuestion(@PathVariable("quizId") String quizId) {
        return ResponseEntity.ok("");
    }
}
