package ru.learn.learnSpring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private TagListResponse tagListResponse() {
        return tagService.getTag();
    }
}
