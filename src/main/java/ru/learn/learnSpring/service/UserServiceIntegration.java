package ru.learn.learnSpring.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.Response;

@Service
public class UserServiceIntegration implements UserService{
    @Override
    public ResponseEntity<Response> getUser(int id) {
        return null;
    }
}
