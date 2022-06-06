package ru.learn.learnSpring.service;

import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.ChangeProfileResponse;
import ru.learn.learnSpring.api.response.LogoutResponse;
import ru.learn.learnSpring.model.repository.CaptchaRepository;
import ru.learn.learnSpring.model.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RestorePasswordService {

    private final UserRepository userRepository;
    private final CaptchaRepository captchaRepository;


    public RestorePasswordService(UserRepository usersRepository, CaptchaRepository captchaRepository) {
        this.userRepository = usersRepository;
        this.captchaRepository = captchaRepository;
    }

    public LogoutResponse restorePassword(String toEmail) {
        LogoutResponse logoutResponse = new LogoutResponse();

        if (userRepository.findByEmail(toEmail).isPresent()) {
            logoutResponse.setResult(true);
            String hash = generateHash();
            userRepository.changeCode(hash, toEmail);
        } else {
            logoutResponse.setResult(false);
        }

        return logoutResponse;
    }

    private String generateHash() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        return uuid;
    }

    public ChangeProfileResponse changePassword(String code, String password, String captcha, String captchaSecret) {
        ChangeProfileResponse changeProfileResponse = new ChangeProfileResponse();
        Map<String,String> error = new HashMap<>();

        if (userRepository.findByCode(code).size() == 0) {
            changeProfileResponse.setResult(false);
            error.put("captcha","Ссылка для восстановления пароля устарела.<a href=\"/auth/restore\">Запросить ссылку снова</a>");
            changeProfileResponse.setErrors(error);
        } else if (password.length()<6) {
            changeProfileResponse.setResult(false);
            error.put("password","Пароль короче 6-ти символов");
            changeProfileResponse.setErrors(error);
        } else if (captchaRepository.findByCaptcha(captcha, captchaSecret).size() == 0) {
            changeProfileResponse.setResult(false);
            error.put("captcha","Код с картинки введён неверно");
            changeProfileResponse.setErrors(error);
        } else {
            changeProfileResponse.setResult(true);
            userRepository.newPassword(password, code);
        }

        return changeProfileResponse;
    }
}
