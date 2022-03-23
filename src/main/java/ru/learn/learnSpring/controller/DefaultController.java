package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.api.response.Response;
import ru.learn.learnSpring.service.UserService;

@RestController
@RequiredArgsConstructor
public class DefaultController {


    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<Response> test() {

//        @PathVariable int id,
//        @RequestParam(required = false, defaultValue = "John") String name,
//        @RequestBody User user){
//            return userService.getUser(id);
//        }
        return null;
    }
}
