package ru.learn.learnSpring.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostVoteResponse {

    private Boolean result;
}
