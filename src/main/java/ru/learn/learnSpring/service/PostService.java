package ru.learn.learnSpring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.PostListResponse;
import ru.learn.learnSpring.api.response.PostPreviewResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {

    public PostListResponse postList(String mode, int offset, int limit) {
        PostListResponse postListResponse = new PostListResponse();
        postListResponse.setCount(10);

        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        postPreviewResponseList.add(new PostPreviewResponse());
        postListResponse.setPosts(postPreviewResponseList);
        //log.info(postListResponse.toString());
        return postListResponse;
    }

}
