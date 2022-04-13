package ru.learn.learnSpring.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    private boolean result;
    @JsonProperty("user")
    private UserCheckResponse userLoginResponse;
}
