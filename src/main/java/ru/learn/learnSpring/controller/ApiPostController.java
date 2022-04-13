package ru.learn.learnSpring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.learn.learnSpring.api.dto.PostSearchParameters;
import ru.learn.learnSpring.api.response.PostListResponse;
import ru.learn.learnSpring.api.response.singlePost.SinglePostResponse;
import ru.learn.learnSpring.service.PostService;


@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostListResponse> postListResponse(@RequestParam(required = false, defaultValue = "recent") String mode,
                                                              @RequestParam(required = false, defaultValue = "0") int offset,
                                                              @RequestParam(required = false, defaultValue = "10") int limit){
        return ResponseEntity.ok(postService.postList(mode, offset, limit));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<PostListResponse> searchPosts(@RequestParam String query,
                                                        @RequestParam(required = false, defaultValue = "0") int offset,
                                                        @RequestParam(required = false, defaultValue = "10") int limit) {
        PostSearchParameters postSearchParameters = new PostSearchParameters();
        postSearchParameters.setQuery(query);
        postSearchParameters.setLimit(limit);
        postSearchParameters.setOffset(offset);
        return ResponseEntity.ok(postService.search(postSearchParameters));
    }

    @GetMapping("/byDate")
    public ResponseEntity<PostListResponse> postByDate(@RequestParam String date,
                                                       @RequestParam(required = false, defaultValue = "0") int offset,
                                                       @RequestParam(required = false, defaultValue = "10") int limit) {
        PostSearchParameters postSearchByDate = new PostSearchParameters();
        postSearchByDate.setDate(date);
        postSearchByDate.setOffset(offset);
        postSearchByDate.setLimit(limit);
        return ResponseEntity.ok(postService.byDate(date, offset, limit));
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostListResponse> postByTag(@RequestParam String tag,
                                                       @RequestParam(required = false, defaultValue = "0") int offset,
                                                       @RequestParam(required = false, defaultValue = "10") int limit) {
        PostSearchParameters postSearchByTag = new PostSearchParameters();
        postSearchByTag.setTag(tag);
        postSearchByTag.setOffset(offset);
        postSearchByTag.setLimit(limit);
        return ResponseEntity.ok(postService.byDate(tag, offset, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinglePostResponse> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(postService.byId(id));
    }
}
