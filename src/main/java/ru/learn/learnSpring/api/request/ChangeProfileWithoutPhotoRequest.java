package ru.learn.learnSpring.api.request;

import lombok.Data;

@Data
public class ChangeProfileWithoutPhotoRequest {
    private String name;
    private String email;
    private String password;
    private int removePhoto = 0;
}

