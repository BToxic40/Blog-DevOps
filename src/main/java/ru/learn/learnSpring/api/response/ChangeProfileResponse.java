package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ChangeProfileResponse {
    private boolean result;
    private Map<String, String> errors;

}
