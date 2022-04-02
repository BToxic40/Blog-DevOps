package ru.learn.learnSpring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.dto.PostSearchParameters;
import ru.learn.learnSpring.api.response.PostListResponse;
import ru.learn.learnSpring.api.response.PostPreviewResponse;
import ru.learn.learnSpring.api.response.UserPostResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {

    private PostListResponse postListResponse;

    public PostListResponse postList(String mode, int offset, int limit) {
        PostListResponse postListResponse = new PostListResponse();
        postListResponse.setCount(10);


        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        PostPreviewResponse postPreviewResponse = new PostPreviewResponse();
        postPreviewResponse.setId(2);
        postPreviewResponse.setAnnounce("announce");
        postPreviewResponse.setLikeCount(5);
        postPreviewResponse.setUser(new UserPostResponse(2, "qwerty"));
        postPreviewResponseList.add(postPreviewResponse);
        postListResponse.setPosts(postPreviewResponseList);
        //log.info(postListResponse.toString());
        return postListResponse;
    }

    public PostListResponse search(PostSearchParameters postSearchParameters) {
        log.info(postSearchParameters.toString());
        PostListResponse postListResponse = new PostListResponse();
        postListResponse.setCount(10);

        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        postPreviewResponseList.add(new PostPreviewResponse());
        postListResponse.setPosts(postPreviewResponseList);
        return postListResponse;
    }

}
