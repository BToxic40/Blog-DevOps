package ru.learn.learnSpring.api.response.singlePost;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponse {

    private int id;
    private int timestamp;
    private String text;
    private UserCommentResponse user;

}
