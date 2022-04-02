package ru.learn.learnSpring.api.dto;

import lombok.Data;

@Data
public class PostSearchParameters {

    private int offset;
    private int limit;
    private String query;

}
