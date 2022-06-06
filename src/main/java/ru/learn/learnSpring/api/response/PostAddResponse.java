package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PostAddResponse {

    private boolean result;
    private Map<String, String> errors;

}
