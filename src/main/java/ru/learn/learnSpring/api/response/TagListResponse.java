package ru.learn.learnSpring.api.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class TagListResponse {

    private final List<TagResponse> tags;
}
