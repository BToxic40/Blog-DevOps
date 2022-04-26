package ru.learn.learnSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.service.ProfileService;

@RestController
@RequestMapping("/api/post/")
public class ApiProfileController {
    @Autowired
    private ProfileService profileService;

//    @GetMapping("/my")
//    public ResponseEntity<BaseResponse> getMe() {
//        BaseResponse response = profileService.getMe();
//        return new ResponseEntity<>(response,
//                response.isSuccess() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
//    }
}
