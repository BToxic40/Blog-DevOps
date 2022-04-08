package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.dto.PostSearchParameters;
import ru.learn.learnSpring.api.response.PostListResponse;
import ru.learn.learnSpring.api.response.postPreview.PostPreviewResponse;
import ru.learn.learnSpring.api.response.postPreview.UserResponse;
import ru.learn.learnSpring.api.response.singlePost.CommentResponse;
import ru.learn.learnSpring.api.response.singlePost.SinglePostResponse;
import ru.learn.learnSpring.api.response.singlePost.UserCommentResponse;
import ru.learn.learnSpring.dao.PostRepository;
import ru.learn.learnSpring.model.Post;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public PostListResponse postList(String mode, int offset, int limit) {
        List<Post> allPosts = postRepository.findAll();

        PostListResponse postListResponse = new PostListResponse();
        postListResponse.setCount(allPosts.size());

        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        for (Post post: allPosts) {
            PostPreviewResponse previewResponse = convertToPostPreviewResponse(post);
            postPreviewResponseList.add(previewResponse);
        }

        postListResponse.setPosts(postPreviewResponseList);

        log.info(postListResponse.toString());
        return postListResponse;
    }

    private PostPreviewResponse convertToPostPreviewResponse(Post post){
        PostPreviewResponse postPreviewResponse = new PostPreviewResponse();
        postPreviewResponse.setId(post.getId());
        postPreviewResponse.setAnnounce(createAnnounce(post.getText()));
        postPreviewResponse.setLikeCount(5); // TODO: получить из таблицы лайков
        postPreviewResponse.setUser(new UserResponse(post.getUser().getId(), post.getUser().getName()));
      return postPreviewResponse;
    }

    private String createAnnounce(String text){
        //TODO: убрать ... для коротких постов
        return text.substring(0, Math.min(150, text.length())) + "...";
    }

    public PostListResponse search(PostSearchParameters postSearchParameters) {
        List<Post> allPosts = postRepository.findAll();

        PostListResponse postListResponse = new PostListResponse();
        postListResponse.setCount(allPosts.size());

        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        for (Post post: allPosts) {
            PostPreviewResponse previewResponse = convertToPostPreviewResponse(post);
            postPreviewResponseList.add(previewResponse);
        }

        //List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        postPreviewResponseList.add(new PostPreviewResponse());
        postListResponse.setPosts(postPreviewResponseList);
        log.info(postSearchParameters.toString());
        return postListResponse;
    }

    public PostListResponse byDate(String date, int offset, int limit) {
        PostListResponse postByDateListResponse = new PostListResponse();
        postByDateListResponse.setCount(10);

        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        PostPreviewResponse postPreviewResponse = new PostPreviewResponse();
        postPreviewResponse.setUser(new UserResponse(3, "user"));
        postPreviewResponse.setId(3);
        postPreviewResponse.setLikeCount(6);
        postPreviewResponse.setAnnounce("new post");
        postPreviewResponse.setTitle("POSTS");
        postPreviewResponseList.add(postPreviewResponse);
        postByDateListResponse.setPosts(postPreviewResponseList);
        log.info(postByDateListResponse.toString());
        return postByDateListResponse;
    }

    public PostListResponse byTag(String tag, int offset, int limit) {
        PostListResponse postByTagListResponse = new PostListResponse();
        postByTagListResponse.setCount(10);

        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        PostPreviewResponse postPreviewResponse = new PostPreviewResponse();
        postPreviewResponse.setUser(new UserResponse(5, "user2"));
        postPreviewResponse.setId(4);
        postPreviewResponse.setLikeCount(8);
        postPreviewResponse.setAnnounce("new post2");
        postPreviewResponse.setTitle("PostByTag");
        postPreviewResponseList.add(postPreviewResponse);
        postByTagListResponse.setPosts(postPreviewResponseList);
        log.info(postByTagListResponse.toString());
        return postByTagListResponse;
    }

    public SinglePostResponse byId(int id) {
        UserCommentResponse user1 = new UserCommentResponse(34, "Ivan", "/avatars/ab/cd/ef/52461.jpg");
        UserCommentResponse user2 = new UserCommentResponse(35, "Anton", null);

        CommentResponse comment1 = new CommentResponse();
        comment1.setId(5);
        comment1.setTimestamp(1649421741);
        comment1.setText("comment1");
        comment1.setUser(user1);

        CommentResponse comment2 = new CommentResponse();
        comment2.setId(5);
        comment2.setTimestamp(1649021741);
        comment2.setText("comment2");
        comment2.setUser(user2);

        SinglePostResponse singlePostResponse = new SinglePostResponse();
        singlePostResponse.setId(id);
        singlePostResponse.setText("text <b>bold</b>");
        singlePostResponse.setViewCount(5);
        singlePostResponse.setTimestamp(1649421741);
        singlePostResponse.setUser(new UserResponse(55, "Masha"));
        singlePostResponse.setComments(List.of(comment1, comment2));

        return singlePostResponse;

    }


}
