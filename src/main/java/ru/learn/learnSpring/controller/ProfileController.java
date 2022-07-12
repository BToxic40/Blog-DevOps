package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.api.request.ChangeProfileWithoutPhotoRequest;
import ru.learn.learnSpring.api.response.BaseResponse;
import ru.learn.learnSpring.service.ProfileService;

@RestController
@RequestMapping("/api/profile/my")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final ProfileService profileService;

        @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse  changeProfileWithoutPhoto(@RequestBody ChangeProfileWithoutPhotoRequest request){
        log.info("меняют профиль без отправки фото");
        return profileService.changeWithoutPhoto(request);
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void changeProfile(){
        log.info("меняют профиль с новой фото");
    }
}
