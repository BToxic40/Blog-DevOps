package ru.learn.learnSpring.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NewPostRequest {
    private Long timestamp;
    private int active;
    private String title;
    private List<String> tags;
    private String text;
}
