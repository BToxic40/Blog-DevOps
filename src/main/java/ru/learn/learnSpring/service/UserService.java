package ru.learn.learnSpring.service;

import org.springframework.http.ResponseEntity;
import ru.learn.learnSpring.api.response.Response;

public interface UserService {
    ResponseEntity<Response> getUser(int id);
}
