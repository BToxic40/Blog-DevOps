package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.api.request.CommentRequest;
import ru.learn.learnSpring.api.response.Response;
import ru.learn.learnSpring.service.PostService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
private final PostService postService;

    @PostMapping("/comment")
    public ResponseEntity<Response> comment(@RequestBody CommentRequest request, Principal principal){
        return postService.comment(request, principal);
    }
}
