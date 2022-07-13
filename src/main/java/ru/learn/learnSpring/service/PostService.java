package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.dto.PostSearchParameters;
import ru.learn.learnSpring.api.request.CommentRequest;
import ru.learn.learnSpring.api.request.NewPostRequest;
import ru.learn.learnSpring.api.request.PostListRequest;
import ru.learn.learnSpring.api.request.PostVoteRequest;
import ru.learn.learnSpring.api.response.*;
import ru.learn.learnSpring.api.response.postPreview.PostPreviewResponse;
import ru.learn.learnSpring.api.response.postPreview.UserResponse;
import ru.learn.learnSpring.api.response.singlePost.CommentResponse;
import ru.learn.learnSpring.api.response.singlePost.SinglePostResponse;
import ru.learn.learnSpring.api.response.singlePost.UserCommentResponse;
import ru.learn.learnSpring.exception.CurrentUserNotFoundException;
import ru.learn.learnSpring.model.*;
import ru.learn.learnSpring.model.repository.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    public static final int LIKE_VALUE = 1;
    public static final int DISLIKE_VALUE = -1;
    public static final int MAX_ANNOUNCE_LENGTH = 150;
    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;
    private final TagsRepository tagsRepository;
    private final AuthService authService;
    private final SettingsService settingsService;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;

    public PostListResponse postList(String mode, int offset, int limit) {

        int pageNumber = offset / limit;

        Pageable pageable = PageRequest.of(pageNumber, limit);
        Page<Post> page;

        switch (mode) {
            case "popular":
                page = postRepository.findByPopular(pageable);
                break;
            case "early":
                page = postRepository.findByEarly(pageable);
                break;
            case "best":
                page = postRepository.findByBest(pageable);
                break;
            case "recent":
                page = postRepository.findByRecent(pageable);
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

    private SinglePostResponse convertToSinglePostResponse(Post post) {
        SinglePostResponse singlePostResponse = new SinglePostResponse();
        singlePostResponse.setId(post.getId());
        singlePostResponse.setUser(new UserResponse(post.getUser().getId(), post.getUser().getName()));
        singlePostResponse.setAnnounce(createAnnounce(post.getText()));
        singlePostResponse.setLikeCount(postVotesRepository.countVotesByPost(post.getId(), LIKE_VALUE));
        singlePostResponse.setDislikeCount(postVotesRepository.countVotesByPost(post.getId(), DISLIKE_VALUE));
        singlePostResponse.setText(post.getText());
        singlePostResponse.setTitle(post.getTitle());
        singlePostResponse.setTimestamp(getUnixTimestamp(post));
        singlePostResponse.setViewCount(post.getViewCount());

        List<String> tags = post.getTag()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        singlePostResponse.setTags(tags);
        List<CommentResponse> comment = post.getPostComments()
                .stream()
                .map(this::convertToCommentResponse)
                .collect(Collectors.toList());

        singlePostResponse.setComments(comment);
        return singlePostResponse;
    }


    private CommentResponse convertToCommentResponse(PostComments postComments) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(postComments.getId());
        commentResponse.setText(postComments.getText());
        commentResponse.setTimestamp(postComments.getTime().getTime() / 1000);

        User user = postComments.getUser();
        UserCommentResponse userCommentResponse =
                new UserCommentResponse(user.getId(), user.getName(), user.getPhoto());
        commentResponse.setUser(userCommentResponse);
        return commentResponse;
    }

    public PostListResponse search(PostSearchParameters postSearchParameters) {
        int pageNumber = postSearchParameters.getOffset() / postSearchParameters.getLimit();

        Pageable pageable = PageRequest.of(pageNumber, postSearchParameters.getLimit());
        Page<Post> page = postRepository.search(pageable, postSearchParameters.getQuery());
        return convertToListPostResponse(page);
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
        int pageNumber = offset / limit;

        Pageable pageable = PageRequest.of(pageNumber, limit);
        Integer tagId = tagsRepository.findTag(tag).orElseThrow();
        List<Integer> postsIds = tagsRepository.findIdsPostsByTagId(tagId);

        List<Post> posts = postRepository.findAllById(postsIds);

        PostListResponse postListResponse = new PostListResponse();
        postListResponse.setCount(posts.size());
        List<PostPreviewResponse> postPreviewResponseList = new ArrayList<>();
        for (Post post : posts) {
            PostPreviewResponse previewResponse = convertToPostPreviewResponse(post);
            postPreviewResponseList.add(previewResponse);
        }
        postListResponse.setPosts(postPreviewResponseList);
        return postListResponse;
    }

    public SinglePostResponse findPostById(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow();
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
        return convertToSinglePostResponse(post);
    }

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

    public PostListResponse getPostsByModeration(int offset, int limit, String status) {
        PostListResponse postsResponse = new PostListResponse();

        List<PostPreviewResponse> dto = getPostsModeration(offset, limit, status);

        int countPost = getPostsForModerationCount(status);
        postsResponse.setCount(countPost);
        postsResponse.setPosts(dto);

        return postsResponse;
    }

    public int getPostsForModerationCount(String status) {
        return postRepository.getPostsForModerationCount(ModerationStatus.valueOf(status.toUpperCase()));
    }

    public List<PostPreviewResponse> getPostsModeration(int offset, int limit, String status) {
        List<PostPreviewResponse> result;
        result = (postRepository.findForModerationByStatus(status, limit, offset))
                .stream()
                .map(this::convertToPostPreviewResponse)
                .collect(Collectors.toList());

        return result;
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
        postPreviewResponse.setLikeCount(postVotesRepository.countVotesByPost(post.getId(), LIKE_VALUE));
        postPreviewResponse.setDislikeCount(postVotesRepository.countVotesByPost(post.getId(), DISLIKE_VALUE));
        postPreviewResponse.setUser(new UserResponse(post.getUser().getId(), post.getUser().getName()));
        postPreviewResponse.setTimestamp(getUnixTimestamp(post));
        postPreviewResponse.setViewCount(post.getViewCount());
        postPreviewResponse.setCommentCount(post.getPostComments().size());
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

    @Transactional
    public ResponseEntity<BaseResponse> createPost(NewPostRequest newPostRequest) {
        User currUser = authService.getCurrentUser().orElseThrow();
        Post newPost = new Post();

        newPost.setTime(newPostRequest.getTimestamp() == null ? LocalDateTime.now() : convertToLocalDateTime(newPostRequest.getTimestamp()));
        newPost.setText(newPostRequest.getText());
        newPost.setTitle(newPostRequest.getTitle());
        newPost.setIsActive(newPostRequest.getActive());
        newPost.setUser(currUser);

        Boolean isPremoderationOn = settingsService.getGlobalSettings().get("POST_PREMODERATION");

        newPost.setModerationStatus(isPremoderationOn ? ModerationStatus.NEW : ModerationStatus.ACCEPTED);

        List<Tag> tagsForNewPost = new ArrayList<>();
        for (String tagName : newPostRequest.getTags()) {
            Optional<Tag> tagOptional = tagsRepository.findByName(tagName);
            if (tagOptional.isPresent()) {
                tagsForNewPost.add(tagOptional.get());
            } else {
                Tag tag = new Tag();
                tag.setName(tagName);
                tagsRepository.save(tag);
                tagsForNewPost.add(tag);
            }
        }

        newPost.setTag(tagsForNewPost);
        newPost = postRepository.save(newPost);
        log.info("id of new post= {}", newPost.getId());
        return ResponseEntity.ok(BaseResponse.successResponse);
    }

    private LocalDateTime convertToLocalDateTime(long timeInSeconds) {
        return Instant.ofEpochSecond(timeInSeconds)
                .atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public PostVoteResponse like(PostVoteRequest request) {
        return vote(request, LIKE_VALUE);
    }

    public PostVoteResponse dislike(PostVoteRequest request) {
        return vote(request, DISLIKE_VALUE);
    }

    private PostVoteResponse vote(PostVoteRequest request, int voteValue) {
        Post post = postRepository.findById(request.getPostId()).orElse(null);
        if (post == null) {
            return PostVoteResponse.builder().result(false).build();
        }

        Optional<User> currentUserOpt = authService.getCurrentUser();

        if (currentUserOpt.isEmpty()) {
            return PostVoteResponse.builder().result(false).build();
        }

        User currentUser = currentUserOpt.get();

        PostVote postVote = postVotesRepository.findByPostAndUser(post, currentUser).orElse(null);
        if (postVote == null) {
            PostVote newPostVote = new PostVote();
            newPostVote.setPost(post);
            newPostVote.setTime(new Date());
            newPostVote.setUser(currentUser);
            newPostVote.setValue((byte) voteValue);
            postVotesRepository.save(newPostVote);
            return PostVoteResponse.builder().result(true).build();
        }

        if (postVote.getValue() == voteValue) {
            return PostVoteResponse.builder().result(false).build();
        }

        postVote.setValue((byte) voteValue);
        postVotesRepository.save(postVote);
        return PostVoteResponse.builder().result(true).build();
    }

    public ResponseEntity<Response> comment(CommentRequest request, Principal principal) {
        if (request.getText().length() < 2) {
            return new ResponseEntity<>(ContentAddResponse.builder()
                    .errors(ContentAddErrors.builder().text("Текст комментария не задан или слишком короткий").build())
                    .build(), HttpStatus.BAD_REQUEST);
        }
        int postId = request.getPostId();
        if (postId < 0) {
            return new ResponseEntity<>(ContentAddResponse.builder()
                    .errors(ContentAddErrors.builder().text("Неверный формат id поста").build())
                    .build(), HttpStatus.BAD_REQUEST);
        }


        if (request.getParentId() != null && !request.getParentId().matches("\\d+")) {
            return new ResponseEntity<>(ContentAddResponse.builder()
                    .errors(ContentAddErrors.builder().text("Неверный формат id родительского комментария").build())
                    .build(), HttpStatus.BAD_REQUEST);
        }

        Integer parentId = request.getParentId() == null ? null : Integer.parseInt(request.getParentId());


        PostComments postComments = new PostComments();
        postComments.setPost(postRepository.findPostById(postId));
        postComments.setUser(userRepository.findByEmail(principal.getName()).orElseThrow());
        postComments.setTime(new Date());

        if (parentId != null) {
            postComments.setParentId(postCommentRepository.findOneById(parentId).orElseThrow().getId());
        }

        postComments.setText(request.getText());
        int id = postCommentRepository.save(postComments).getId();
        return new ResponseEntity<>(new PublishCommentResponse(id), HttpStatus.OK);
    }

    private long getUnixTimestamp(Post post) {
        ZoneId zoneId = ZoneId.systemDefault();
        return post.getTime().atZone(zoneId).toEpochSecond();
    }
}
