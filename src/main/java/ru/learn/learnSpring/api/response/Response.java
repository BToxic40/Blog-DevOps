package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public  class Response{
    private final UserResponse user = new UserResponse();
    private final List<UserResponse> users = List.of();
    //private final String title = "Our blog";
    private final int id;
    private final String name;
}
