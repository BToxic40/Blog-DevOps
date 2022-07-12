package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.request.ChangeProfileWithoutPhotoRequest;
import ru.learn.learnSpring.api.response.BaseResponse;
import ru.learn.learnSpring.api.response.ErrorResponse;
import ru.learn.learnSpring.model.User;
import ru.learn.learnSpring.model.repository.UserRepository;
import ru.learn.learnSpring.utils.Validators;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final AuthService authService;
    private final UserRepository userRepository;

    public BaseResponse changeWithoutPhoto(ChangeProfileWithoutPhotoRequest request){
        log.info("{}",request);

        Map<String,String> errors = new HashMap<>();

        Validators.password(request.getPassword())
                .ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));

        Validators.name(request.getName())
                .ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));

        if(!errors.isEmpty()) {
            return new ErrorResponse(errors);
        }

        User user = authService.getCurrentUser().orElseThrow();
        user.setName(request.getName());
        user.setPassword(authService.encryptPassword(request.getPassword()));
        if(request.getRemovePhoto() == 1){
            user.setPhoto(null);
        }

        userRepository.save(user);

        return BaseResponse.successResponse;


    }
}
