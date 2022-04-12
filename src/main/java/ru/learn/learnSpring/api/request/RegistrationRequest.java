package ru.learn.learnSpring.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {

    @JsonProperty("e_mail")
    private String email;

    private String password;

    private String name;

    @JsonProperty("captcha")
    private String userInputCaptcha;

    @JsonProperty("captcha_secret")
    private String captchaSecretCode;

}
