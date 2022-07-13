package ru.learn.learnSpring.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.LoginResponse;
import ru.learn.learnSpring.api.response.UserCheckResponse;
import ru.learn.learnSpring.model.ModerationStatus;
import ru.learn.learnSpring.model.repository.PostRepository;
import ru.learn.learnSpring.model.repository.UserRepository;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CaptchaService captchaService;

    public LoginResponse getLogin(String email) {
        ru.learn.learnSpring.model.User currentUser = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        UserCheckResponse userResponse = new UserCheckResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setModeration(currentUser.getIsModerator() == 1);
        userResponse.setSettings(currentUser.getIsModerator() == 1);
        userResponse.setId(currentUser.getId());
        userResponse.setName(currentUser.getName());
        userResponse.setPhoto(currentUser.getPhoto());
        userResponse.setModerationCount(postRepository.getPostsForModerationCount(ModerationStatus.NEW));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userResponse);
        return loginResponse;
    }
}
