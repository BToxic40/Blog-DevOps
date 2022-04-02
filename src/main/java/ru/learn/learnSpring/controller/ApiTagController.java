package ru.learn.learnSpring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.api.response.TagListResponse;
import ru.learn.learnSpring.service.TagService;

@RestController
@RequestMapping("/api")
public class ApiTagController {

    private final TagService tagService;

    public ApiTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tag")
    public ResponseEntity<TagListResponse> tagListResponse(            @RequestParam(required = false) String query    ) {
        return ResponseEntity.ok(tagService.getTagList(query));
    }
}
