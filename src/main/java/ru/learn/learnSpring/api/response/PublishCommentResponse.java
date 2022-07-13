package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PublishCommentResponse implements Response {

    private final int id;
}
