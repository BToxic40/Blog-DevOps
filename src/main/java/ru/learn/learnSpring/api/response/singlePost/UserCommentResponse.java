package ru.learn.learnSpring.api.response.singlePost;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserCommentResponse {
    private final int id;
    private final String name;
    private final String photo;
}
