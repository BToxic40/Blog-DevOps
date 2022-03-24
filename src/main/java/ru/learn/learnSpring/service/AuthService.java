package ru.learn.learnSpring.service;

import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.CheckResponse;
import ru.learn.learnSpring.api.response.UserCheckResponse;

@Service
public class AuthService {

    public CheckResponse check() {
        boolean auth = true;

        if(auth){
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
}
