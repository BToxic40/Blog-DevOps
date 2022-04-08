package ru.learn.learnSpring.api.response.singlePost;

import lombok.Getter;
import lombok.Setter;
import ru.learn.learnSpring.api.response.postPreview.UserResponse;

import java.util.List;

@Getter
@Setter
public class SinglePostResponse {

    private int id;
    private long timestamp;
    private UserResponse user;
    private List<String> tags;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private boolean active;
    private String text;
    private List<CommentResponse> comments;

}
