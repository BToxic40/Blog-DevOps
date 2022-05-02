package ru.learn.learnSpring.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostListRequest {

    private int id;

    private String title;

    private String text;

    private List<String> tags;
}
