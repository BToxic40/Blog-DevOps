package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ru.learn.learnSpring.api.request.LoginRequest;
import ru.learn.learnSpring.api.request.RegistrationRequest;
import ru.learn.learnSpring.api.request.RestoreRequest;
import ru.learn.learnSpring.api.response.BaseResponse;
import ru.learn.learnSpring.api.response.CaptchaResponse;
import ru.learn.learnSpring.api.response.LoginResponse;
import ru.learn.learnSpring.api.response.LogoutResponse;
import ru.learn.learnSpring.service.AuthService;
import ru.learn.learnSpring.service.CaptchaService;
import ru.learn.learnSpring.service.RestorePasswordService;
import ru.learn.learnSpring.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final AuthService authService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CaptchaService captchaService;
    private final RestorePasswordService restorePasswordService;

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> generateCaptcha() {
        return ResponseEntity.ok(captchaService.createCaptcha());
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registration(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();

        return ResponseEntity.ok(userService.getLogin(user.getUsername()));
    }

    @GetMapping("/logout")
    public ResponseEntity<BaseResponse> logout(){
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(BaseResponse.successResponse);
    }

    @GetMapping("/check")
    public ResponseEntity<LoginResponse> check(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(userService.getLogin(principal.getName()));
    }

    @PostMapping("/restore")
    public LogoutResponse Restore(@RequestBody RestoreRequest restoreRequest) {
        return restorePasswordService.restorePassword(restoreRequest.getEmail());
    }
}


