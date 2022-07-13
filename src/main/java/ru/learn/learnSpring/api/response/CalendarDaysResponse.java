package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class CalendarDaysResponse {

    private int[] years;

    private Map<String, Integer> posts;
}
