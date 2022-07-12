package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.learn.learnSpring.api.request.ChangeProfileWithoutPhotoRequest;
import ru.learn.learnSpring.api.response.BaseResponse;
import ru.learn.learnSpring.api.response.ErrorResponse;
import ru.learn.learnSpring.model.User;
import ru.learn.learnSpring.model.repository.UserRepository;
import ru.learn.learnSpring.utils.Validators;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public BaseResponse changeWithoutPhoto(ChangeProfileWithoutPhotoRequest request) {
        log.info("{}", request);
        User user = authService.getCurrentUser().orElseThrow();

        Map<String, String> errors = new HashMap<>();

        if (Objects.nonNull(request.getPassword())) {
            Validators.password(request.getPassword())
                    .ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));
            user.setPassword(authService.encryptPassword(request.getPassword()));
        }

        Validators.name(request.getName())
                .ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));

        if (!errors.isEmpty()) {
            return new ErrorResponse(errors);
        }

        user.setName(request.getName());

        if (request.getRemovePhoto() == 1) {
            user.setPhoto(null);
        }

        userRepository.save(user);

        return BaseResponse.successResponse;
    }

    public BaseResponse change(MultipartFile photo, String name, String password) {
        ChangeProfileWithoutPhotoRequest withoutPhotoRequest = new ChangeProfileWithoutPhotoRequest();
        withoutPhotoRequest.setPassword(password);
        withoutPhotoRequest.setName(name);
        withoutPhotoRequest.setRemovePhoto(0);
        changeWithoutPhoto(withoutPhotoRequest);

        User user = authService.getCurrentUser().orElseThrow();
        String urlPhoto = imageService.addImage(photo);
        user.setPhoto(urlPhoto);
        userRepository.save(user);

        return BaseResponse.successResponse;
    }
}
