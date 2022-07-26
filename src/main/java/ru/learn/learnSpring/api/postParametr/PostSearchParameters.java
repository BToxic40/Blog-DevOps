package ru.learn.learnSpring.api.postParametr;

import lombok.Data;

@Data
public class PostSearchParameters {

    private int offset = 0;
    private int limit = 10;
    private String query;
    private String date;
    private String tag;

}
