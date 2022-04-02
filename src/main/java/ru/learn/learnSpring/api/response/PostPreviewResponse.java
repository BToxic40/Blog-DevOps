package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPreviewResponse {

    private int id;
    private long timestamp;
    private UserPostResponse user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;

}
