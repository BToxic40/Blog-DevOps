package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterErrorResponse {

    private String e_mail;

    private String password;

    private String name;

    private String captcha;

    private String captcha_code;
}
