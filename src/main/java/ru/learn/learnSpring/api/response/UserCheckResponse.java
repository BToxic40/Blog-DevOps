package ru.learn.learnSpring.api.response;

import lombok.Data;

@Data
public class UserCheckResponse {

    private int id;

    private String name;

    private String photo;

    private String email;

    private boolean moderation;

    private int moderationCount;

    private boolean settings;
}
