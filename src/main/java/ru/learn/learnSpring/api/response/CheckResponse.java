package ru.learn.learnSpring.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckResponse extends BaseResponse{
    private UserCheckResponse user;
}
