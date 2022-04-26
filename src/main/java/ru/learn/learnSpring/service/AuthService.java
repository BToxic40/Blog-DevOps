package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learn.learnSpring.api.request.RegistrationRequest;
import ru.learn.learnSpring.api.response.BaseResponse;
import ru.learn.learnSpring.api.response.ErrorResponse;
import ru.learn.learnSpring.model.CaptchaCode;
import ru.learn.learnSpring.model.User;
import ru.learn.learnSpring.model.repository.CaptchaRepository;
import ru.learn.learnSpring.model.repository.UserRepository;
import ru.learn.learnSpring.utils.EmailValidator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {

    public static final long UPDATE_FOR_CAPTCHA = 3600;
    private final CaptchaRepository captchaRepository;
    private final CaptchaService captchaService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    public BaseResponse register(RegistrationRequest request) {
        Map<String, String> errors = collectErrors(request);

        if (!errors.isEmpty()) {
            return new ErrorResponse(errors);
        }

        User userForReg = createUser(request);
        userRepository.save(userForReg);
        deleteOldCaptcha();

        return BaseResponse.successResponse;
    }

    private Map<String, String> collectErrors(RegistrationRequest request) {
        Map<String, String> errors = new HashMap<>();
        if (!isCaptchaValid(request)) {
            errors.put("captcha", "Код с картинки введён неверно");
        }

        if (!EmailValidator.isValid(request.getEmail())) {
            errors.put("email", "Неверный формат email");
        }

        if (request.getPassword().length() < 6) {
            errors.put("password", "Пароль короче 6-ти символов");
        }

        if (request.getName().length() > 20) {
            errors.put("name", "Имя не должно превышать 20 символов");
        }

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

    boolean isCaptchaValid(RegistrationRequest request) {

        List<CaptchaCode> captchaCodes =
                new ArrayList<>(captchaRepository.findBySecretCode(request.getCaptchaSecretCode()));

        if (captchaCodes.isEmpty()) {
            return false;
        }

        CaptchaCode captchaCode = captchaCodes.get(0);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = captchaCode.getTime();
        long hour = ChronoUnit.HOURS.between(startTime, endTime);

        if (hour >= UPDATE_FOR_CAPTCHA) {
            return false;
        }

        return captchaCode.getCode().equals(request.getUserInputCaptcha());
    }

    @Scheduled(fixedDelay = 1800000)
    public void deleteOldCaptcha() {
        captchaRepository.delete();
    }

    public Optional<User> getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email);

    }
}
