package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends BaseResponse{
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}
