package ru.learn.learnSpring.service;

import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.*;

@Service
public class AuthService {

    public CheckResponse check() {
        boolean auth = true;

        if (auth) {
            // лезем в репозиторий и получаем пользователя
            UserCheckResponse user = new UserCheckResponse();
            user.setEmail("sdfsdf");
            user.setId(34);
            user.setName("For");

            //заполняем ответ для контроллера
            CheckResponse checkResponse = new CheckResponse();
            checkResponse.success();
            checkResponse.setUser(user);
            return checkResponse;
        }
        return new CheckResponse();
    }

    public CaptchaResponse captcha() {
        CaptchaResponse captcha = new CaptchaResponse();
        captcha.setImage("data:image/png;base64, код_изображения_в_base64");
        captcha.setSecret("car4y8cryaw84cr89awnrc");
        return captcha;
    }

    public RegisterResponse register() {

        boolean authorisation = true;

        if (authorisation) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setE_mail("konstantin@mail.ru");
            registerResponse.setPassword("123456");
            registerResponse.setName("Константин");
            registerResponse.setCaptcha("d34f");
            registerResponse.setCaptcha_code("69sdFd67df7Pd9d3");
            return registerResponse;
        } else {
            return null;
        }
    }
}
