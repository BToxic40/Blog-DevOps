package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.model.repository.PostRepository;
import ru.learn.learnSpring.model.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ModerationService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    //private final AuthService authService;


//    public BaseResponse decisionModeration(int postId, String decision) {
//        BaseResponse authCheckResponse = new BaseResponse();

        //Optional<User> id = authService.getCurrentUser();
//        if (userRepository.findUserInfo(id).getIsModerator() == 0) {
//            authCheckResponse.setSuccess(false);
//        } else if (decision.equals("accept")){
//            postRepository.changeStatus("ACCEPTED", postId);
//            authCheckResponse.setSuccess(true);
//        } else if (decision.equals("decline")) {
//            authCheckResponse.setSuccess(true);
//            postRepository.changeStatus("DECLINED", postId);
//        } else {
//            authCheckResponse.setSuccess(false);
//        }
//
//        return authCheckResponse;
//    }

    public int getPostsForModerationCount() {
        return postRepository.getPostsForModerationCount();
    }
}
