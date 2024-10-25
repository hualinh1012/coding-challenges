package io.elsanow.challenge.quiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hc")
public class HealthcheckController {

    @GetMapping
    public String healthcheck() {
        return "OK";
    }
}
