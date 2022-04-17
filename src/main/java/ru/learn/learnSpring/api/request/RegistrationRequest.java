package ru.learn.learnSpring.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {

    @JsonProperty("e_mail")
    private String email;

    private String password1;

    private String password2;

    private String name;

    @JsonProperty("captcha")
    private String userInputCaptcha;

    @JsonProperty("captcha_secret")
    private String captchaSecretCode;

}
