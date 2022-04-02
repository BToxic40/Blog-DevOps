package ru.learn.learnSpring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.api.dto.PostSearchParameters;
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
    public ResponseEntity<PostListResponse> postListResponse(@RequestParam(required = false, defaultValue = "recent") String mode,
                                                              @RequestParam(required = false, defaultValue = "0") int offset,
                                                              @RequestParam(required = false, defaultValue = "10") int limit){
        return ResponseEntity.ok(postService.postList(mode, offset, limit));
    }

    @GetMapping("/post/search")
    public ResponseEntity<PostListResponse> searchPosts(@RequestParam String query,
                                                        @RequestParam(required = false, defaultValue = "0") int offset,
                                                        @RequestParam(required = false, defaultValue = "10") int limit) {
        PostSearchParameters postSearchParameters = new PostSearchParameters();
        postSearchParameters.setQuery(query);
        postSearchParameters.setLimit(limit);
        postSearchParameters.setOffset(offset);
        return ResponseEntity.ok(postService.search(postSearchParameters));
    }

}
