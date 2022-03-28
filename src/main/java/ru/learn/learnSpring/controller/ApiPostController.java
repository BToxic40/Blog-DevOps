package ru.learn.learnSpring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.api.response.PostListResponse;
import ru.learn.learnSpring.service.PostService;


@RestController
@RequestMapping("/api")
public class ApiPostController {

    private final PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    private PostListResponse postListResponse(@RequestParam(required = false, defaultValue = "recent") String mode,
                                              @RequestParam(required = false, defaultValue = "0") int offset,
                                              @RequestParam(required = false, defaultValue = "10") int limit){
        return postService.postList(mode, offset, limit);
    }

}
