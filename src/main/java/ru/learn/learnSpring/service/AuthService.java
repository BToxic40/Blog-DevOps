package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.request.RegistrationRequest;
import ru.learn.learnSpring.api.response.BaseResponse;
import ru.learn.learnSpring.api.response.ErrorResponse;
import ru.learn.learnSpring.model.User;
import ru.learn.learnSpring.model.repository.UserRepository;
import ru.learn.learnSpring.utils.EmailValidator;
import ru.learn.learnSpring.utils.Validators;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final CaptchaService captchaService;

    public BaseResponse register(RegistrationRequest request) {
        Map<String, String> errors = collectErrors(request);

        if (!errors.isEmpty()) {
            return new ErrorResponse(errors);
        }

        User userForReg = createUser(request);
        userRepository.save(userForReg);

        return BaseResponse.successResponse;
    }

    public Optional<User> getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email);
    }

    public String encryptPassword(String password) {
        return encoder.encode(password);
    }

    private Map<String, String> collectErrors(RegistrationRequest request) {
        Map<String, String> errors = new HashMap<>();
        if (!captchaService.isCaptchaValid(request)) {
            errors.put("captcha", "Код с картинки введён неверно");
        }

        if (!EmailValidator.isValid(request.getEmail())) {
            errors.put("email", "Неверный формат email");
        }

        Validators.password(request.getPassword())
                .ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));

        Validators.name(request.getName())
                .ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }

        return errors;
    }

    private User createUser(RegistrationRequest request) {
        User userForReg = new User();
        userForReg.setName(request.getName());
        String encodedPassword = encoder.encode(request.getPassword());
        userForReg.setPassword(encodedPassword);
        userForReg.setEmail(request.getEmail());
        userForReg.setIsModerator(0);
        userForReg.setRegTime(LocalDateTime.now());
        return userForReg;
    }
}
