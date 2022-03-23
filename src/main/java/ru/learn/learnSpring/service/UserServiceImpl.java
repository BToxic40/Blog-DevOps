package ru.learn.learnSpring.service;

import org.springframework.http.ResponseEntity;
import ru.learn.learnSpring.api.response.Response;


public class UserServiceImpl implements UserService {

    public ResponseEntity<Response> getUser(int id) {
        if (id > 500){
            return ResponseEntity.notFound().build();
        }
        Response response = new Response(id, "John");
        return ResponseEntity.ok(response);
    }
}
