package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public  class Response{
    private final UserResponse user = new UserResponse("lsdfjlksdjf");
    private final List<UserResponse> users = List.of(new UserResponse("ddd"), new UserResponse("ccc"));
    private final String title = "Our blog";
    private final int id;
    private final String name;
}
