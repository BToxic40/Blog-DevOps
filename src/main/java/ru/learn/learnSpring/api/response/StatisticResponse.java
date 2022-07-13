package ru.learn.learnSpring.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class StatisticResponse {

    private final int postsCount;

    private final int likesCount;

    private final int dislikesCount;

    private final int viewsCount;

    private final long firstPublication;
}
