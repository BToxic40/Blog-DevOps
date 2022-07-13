package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @PreAuthorize("hasAuthority('user:write')")
    public BaseResponse changeProfileWithoutPhoto(@RequestBody ChangeProfileWithoutPhotoRequest request) {
        log.info("меняют профиль без отправки фото");
        return profileService.changeWithoutPhoto(request);
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('user:write')")
    public BaseResponse changeProfile(@RequestParam MultipartFile photo,
                              @RequestParam String name,
                              @RequestParam(required = false) String password) {
        log.info("name: {} photosize: {}", photo.getOriginalFilename(), photo.getSize());
        log.info("name:{},pass:{}", name, password);
        log.info("меняют профиль с новой фото");
        return profileService.change(photo, name, password);
    }
}
