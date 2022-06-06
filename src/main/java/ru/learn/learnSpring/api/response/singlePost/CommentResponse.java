package ru.learn.learnSpring.api.response.singlePost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Integer id;
    private Long timestamp;
    private String text;
    private UserCommentResponse user;
}
