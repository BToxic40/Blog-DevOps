package ru.learn.learnSpring.api.response;

import lombok.ToString;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@ToString
public class PostListResponse {
    private int count;
    private List<PostPreviewResponse> posts;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PostPreviewResponse> getPosts() {
        return posts;
    }

    public void setPosts(List<PostPreviewResponse> posts) {
        this.posts = posts;
    }
}