package ru.learn.learnSpring.api.request;

import lombok.Data;

@Data
public class ChangeProfileRequest {

    private String name;
    private String email;
    private String password;
    private Integer removePhoto = 0;

}
