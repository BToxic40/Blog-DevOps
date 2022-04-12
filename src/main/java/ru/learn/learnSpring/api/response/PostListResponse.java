package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import ru.learn.learnSpring.api.response.postPreview.PostPreviewResponse;

import java.util.List;

@Getter
@Setter
@Component
@ToString
public class PostListResponse {

    private long count;
    private List<PostPreviewResponse> posts;

}