package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorResponse extends BaseResponse{
    private Map<String, String> errors;

    public ErrorResponse(Map<String, String> errors) {
        this.errors = errors;
        this.isSuccess = false;
    }
}
