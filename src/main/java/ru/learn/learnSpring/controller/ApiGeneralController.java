package ru.learn.learnSpring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.api.response.InitResponse;

@RestController
public class ApiGeneralController {

    private final InitResponse initResponse;

    public ApiGeneralController(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    @GetMapping("/api/init")
    private InitResponse init(){
        return initResponse;
    }
}
