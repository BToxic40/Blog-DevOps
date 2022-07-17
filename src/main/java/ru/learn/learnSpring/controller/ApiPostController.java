package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.learn.learnSpring.api.dto.PostSearchParameters;
import ru.learn.learnSpring.api.request.NewPostRequest;
import ru.learn.learnSpring.api.request.PostListRequest;
import ru.learn.learnSpring.api.request.PostVoteRequest;
import ru.learn.learnSpring.api.response.BaseResponse;
import ru.learn.learnSpring.api.response.PostListResponse;
import ru.learn.learnSpring.api.response.PostVoteResponse;
import ru.learn.learnSpring.api.response.singlePost.SinglePostResponse;
import ru.learn.learnSpring.service.PostService;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class ApiPostController {

    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<PostListResponse> postListResponse(@RequestParam(required = false, defaultValue = "recent") String mode,
                                                             @RequestParam(required = false, defaultValue = "0") int offset,
                                                             @RequestParam(required = false, defaultValue = "10") int limit) {
        return ResponseEntity.ok(postService.postList(mode, offset, limit));
    }

    @GetMapping("/search")
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
        return ResponseEntity.ok(postService.byDate(date, offset, limit));
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostListResponse> postByTag(@RequestParam String tag,
                                                      @RequestParam(required = false, defaultValue = "0") int offset,
                                                      @RequestParam(required = false, defaultValue = "10") int limit) {
        return ResponseEntity.ok(postService.byTag(tag));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinglePostResponse> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(postService.findPostById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<PostListResponse> getPostMy(@RequestParam String status,
                                                      @RequestParam(required = false, defaultValue = "0") int offset,
                                                      @RequestParam(required = false, defaultValue = "10") int limit) {
        return ResponseEntity.ok(postService.my(status, offset, limit));
    }

    @PostMapping("/{id}")
    public ResponseEntity<PostListResponse> editPostById(
            @RequestBody PostListRequest postListRequest,
            @PathVariable(value = "id") int id,
            @DateTimeFormat LocalDateTime time) {
        return ResponseEntity.ok(postService.edit(id, postListRequest, time));
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> createPost(@RequestBody NewPostRequest newPostRequest) {
        return postService.createPost(newPostRequest);
    }

//    @DeleteMapping("/{id}")
//    public void deletePost(@PathVariable(name = "id") int id) {
//        postService.delete(id);
//    }

    @PostMapping("/like")
    private ResponseEntity<PostVoteResponse> like(@RequestBody PostVoteRequest request) {

        return ResponseEntity.ok(postService.like(request));
    }

    @PostMapping("/dislike")
    private ResponseEntity<PostVoteResponse> dislike(@RequestBody PostVoteRequest request) {

        return ResponseEntity.ok(postService.dislike(request));
    }

    @GetMapping("/moderation")
    private PostListResponse postByModeration(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "status") String status
    ) {
        return postService.getPostsByModeration(offset, limit, status);
    }
}
