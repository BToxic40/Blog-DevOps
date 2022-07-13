package ru.learn.learnSpring.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeProfileWithoutPhotoRequest {

    private String name;
    private String email;
    private String password;
    private int removePhoto = 0;
}

