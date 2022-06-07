package ru.learn.learnSpring.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModerationRequest {
    @JsonProperty("post_id")
    private Integer postId;

    private String decision;
}
