package ru.learn.learnSpring.api.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TagResponse {

    private final String name;

    private final double weight;
}
