package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.dto.PostSearchParameters;
import ru.learn.learnSpring.api.request.NewPostRequest;
import ru.learn.learnSpring.api.request.PostListRequest;
import ru.learn.learnSpring.api.response.BaseResponse;
import ru.learn.learnSpring.api.response.PostDeleteResponse;
import ru.learn.learnSpring.api.response.PostListResponse;
import ru.learn.learnSpring.api.response.postPreview.PostPreviewResponse;
import ru.learn.learnSpring.api.response.postPreview.UserResponse;
import ru.learn.learnSpring.api.response.singlePost.SinglePostResponse;
import ru.learn.learnSpring.exception.CurrentUserNotFoundException;
import ru.learn.learnSpring.model.ModerationStatus;
import ru.learn.learnSpring.model.Post;
import ru.learn.learnSpring.model.User;
import ru.learn.learnSpring.model.repository.PostRepository;
import ru.learn.learnSpring.model.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;
    private final AuthService authService;
    private final SettingsService settingsService;

    public PostListResponse postList(String mode, int offset, int limit) {


        // номер страницы
        // 100, 10 = десять первых постов, страница 10
        // 0, 10 = десять первых постов, страница 1

        // получить из бд посты с фильтрами mode
        // использовать пагинацию
        // заполнить response и вернуть

        // 0 10 -> 0/10 = 0
        // 10/10 = 1

        int pageNumber = offset / limit;

        Pageable pageable = PageRequest.of(pageNumber, limit);
        Page<Post> page;

        switch (mode) {
//            case "popular":
//                // колво комментов, join таблицы комменты или подзапрос SELECT с COUNT
//                page = postRepository.activeAcceptedPostsOrderByDateDesc(pageable);
//                break;
            case "early":
                page = postRepository.activeAcceptedPostsOrderByDate(pageable);
                break;
//            case "best":
//                // количеству лайков - колво дизлайков + кол-во комментов * 0.5
//                page = postRepository.activeAcceptedPostsOrderByDateDesc(pageable);
//                break;
            case "recent":
                page = postRepository.activeAcceptedPostsOrderByDateDesc(pageable);
                break;
            default:
                throw new IllegalArgumentException("Выбран несуществующий mode!");
        }

        return convertToListPostResponse(page);
    }

    private PostListResponse convertToListPostResponse(Page<Post> postPage) {
        List<Post> allPosts = postPage.getContent();

        PostListResponse postListResponse = new PostListResponse();
        postListResponse.setCount(postPage.getTotalElements());

        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        for (Post post : allPosts) {
            PostPreviewResponse previewResponse = convertToPostPreviewResponse(post);
            postPreviewResponseList.add(previewResponse);
        }

        postListResponse.setPosts(postPreviewResponseList);

        log.info(postListResponse.toString());
        return postListResponse;
    }


    public PostListResponse search(PostSearchParameters postSearchParameters) {
        List<Post> allPosts = postRepository.findAll();

        PostListResponse postListResponse = new PostListResponse();
        postListResponse.setCount(allPosts.size());

        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        for (Post post : allPosts) {
            PostPreviewResponse previewResponse = convertToPostPreviewResponse(post);
            postPreviewResponseList.add(previewResponse);
        }

        postPreviewResponseList.add(new PostPreviewResponse());
        postListResponse.setPosts(postPreviewResponseList);
        log.info(postSearchParameters.toString());
        return postListResponse;
    }

    public PostListResponse delete(int id) {
        PostDeleteResponse postDeleteResponse = new PostDeleteResponse();
        Optional<Post> post = postRepository.findById(id);
        postRepository.deleteById(post);
        postDeleteResponse.setId(id);
        return new PostListResponse();
    }

    public PostListResponse byDate(String date, int offset, int limit) {
        int pageNumber = offset / limit;

        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Формат даты не подходит!");
        }

        Pageable pageable = PageRequest.of(pageNumber, limit);
        Page<Post> page = postRepository.findPostsByDate(pageable, date);

        return convertToListPostResponse(page);
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

    public SinglePostResponse findPostById(int id) {

        Post post = postRepository.findById(id)
                .orElseThrow();


        return convertToSinglePostResponse(post);
    }

    private SinglePostResponse convertToSinglePostResponse(Post post) {
        // TODO
        return null;
    }
//        Optional<User> userId = getCurrentUserId();
//        return (Post) postRepository.findAllById(userId);


//        UserCommentResponse user1 = new UserCommentResponse(34, "Ivan", "/avatars/ab/cd/ef/52461.jpg");
//        UserCommentResponse user2 = new UserCommentResponse(35, "Anton", null);
//
//        CommentResponse comment1 = new CommentResponse();
//        comment1.setId(5);
//        comment1.setTimestamp(1649421741);
//        comment1.setText("comment1");
//        comment1.setUser(user1);
//
//        CommentResponse comment2 = new CommentResponse();
//        comment2.setId(5);
//        comment2.setTimestamp(1649021741);
//        comment2.setText("comment2");
//        comment2.setUser(user2);
//
//        SinglePostResponse singlePostResponse = new SinglePostResponse();
//        singlePostResponse.setId(id);
//        singlePostResponse.setText("text <b>bold</b>");
//        singlePostResponse.setViewCount(5);
//        singlePostResponse.setTimestamp(1649421741);
//        singlePostResponse.setUser(new UserResponse(55, "Masha"));
//        singlePostResponse.setComments(List.of(comment1, comment2));
//
//        return singlePostResponse;



    public PostListResponse my(String status, int offset, int limit) {

        int pageNumber = offset / limit;
        Pageable pageable = PageRequest.of(pageNumber, limit);
        Page<Post> page;
        int idCurrentUser = getCurrentUserId();

        switch (status) {
            case "inactive":
                page = postRepository.findHiddenPostMy(pageable, idCurrentUser);
                break;
            case "pending":
                page = postRepository.findIsActivePostMy(pageable, idCurrentUser);
                break;
            case "declined":
                page = postRepository.findRejectedPostMy(pageable, idCurrentUser);
                break;
            case "published":
                page = postRepository.findAcceptedPostMy(pageable, idCurrentUser);
                break;
            default:
                throw new IllegalArgumentException("Выбран несуществующий status!");
        }

        return convertToListPostResponse(page);
    }

    public PostListResponse edit(int id, PostListRequest postListRequest, LocalDateTime time) {
        Post post = postRepository.getOne(id);
        post.setTitle(postListRequest.getTitle());
        post.setText(postListRequest.getText());
        post.setTime(time);
        postRepository.saveAndFlush(id);
        return new PostListResponse();
    }


    private int getCurrentUserId() {
        return authService.getCurrentUser()
                .orElseThrow(CurrentUserNotFoundException::new)
                .getId();

    }

    private PostPreviewResponse convertToPostPreviewResponse(Post post) {
        PostPreviewResponse postPreviewResponse = new PostPreviewResponse();
        postPreviewResponse.setId(post.getId());
        postPreviewResponse.setAnnounce(createAnnounce(post.getText()));
        postPreviewResponse.setLikeCount(5); // TODO: получить из таблицы лайков
        postPreviewResponse.setUser(new UserResponse(post.getUser().getId(), post.getUser().getName()));
        return postPreviewResponse;
    }

    private String createAnnounce(String text) {
        int TEXT_TO_PREVIEW = 150;
        if (text.length() <= TEXT_TO_PREVIEW) {
            return text;
        } else {
            return text.substring(0, TEXT_TO_PREVIEW) + "...";
        }
    }

    public ResponseEntity<BaseResponse> createPost(NewPostRequest newPostRequest) {
        // 1 - получаем текущего пользователя
        // 2 - создаем Post модель и сохраняем в бд
        // 3 - сохраняем теги этого постав (если тега нет в бд, создаем новые, если тег есть, то добавляем указатель на него)


        User currUser  = authService.getCurrentUser().orElseThrow();

        Post newPost = new Post();

        // если timestamp == null, то сегодняшняя у поста, в обратном случае - преобразуем timestamp в LocalDateTime
        newPost.setTime(newPostRequest.getTimestamp() == null ? LocalDateTime.now() : convertToLocalDateTime(newPostRequest.getTimestamp()));
        newPost.setText(newPostRequest.getText());
        newPost.setTitle(newPostRequest.getTitle());
        newPost.setIsActive(newPost.getIsActive());
        newPost.setUser(currUser);

        Boolean isPremoderationOn = settingsService.getGlobalSettings().get("POST_PREMODERATION");

        newPost.setModerationStatus(isPremoderationOn ? ModerationStatus.NEW : ModerationStatus.ACCEPTED);

        newPost = postRepository.save(newPost);

        log.info("id of new post= {}", newPost.getId());

        return ResponseEntity.ok(BaseResponse.successResponse);

    }

    private LocalDateTime convertToLocalDateTime(long timeInSeconds) {
    return Instant.ofEpochSecond(timeInSeconds)
            .atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}
