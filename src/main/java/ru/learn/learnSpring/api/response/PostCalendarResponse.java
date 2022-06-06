package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Setter
@Getter
public class PostCalendarResponse {

    private List<Integer> years;
    private Map<String, Integer> posts;
}
