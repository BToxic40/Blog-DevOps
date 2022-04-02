package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ToString
public class PostListResponse {

    private int count;
    private List<PostPreviewResponse> posts;

}