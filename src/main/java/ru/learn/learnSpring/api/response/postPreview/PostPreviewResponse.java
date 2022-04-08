package ru.learn.learnSpring.api.response.postPreview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPreviewResponse {

    private int id;
    private long timestamp;
    private UserResponse user;
    //private UserResponse userResponse;
    //private TagListResponse tags;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private boolean active;
    private String text;

}
