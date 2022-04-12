package ru.learn.learnSpring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.learn.learnSpring.api.request.LoginRequest;
import ru.learn.learnSpring.api.request.RegistrationRequest;
import ru.learn.learnSpring.api.response.*;
import ru.learn.learnSpring.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthService authService;

    public ApiAuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/check")
    public ResponseEntity<CheckResponse> checkUserAuth() {
        return ResponseEntity.ok(authService.check());
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> checkCaptcha() {
        return ResponseEntity.ok(authService.captcha());
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registration(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new LoginResponse());
    }
}


