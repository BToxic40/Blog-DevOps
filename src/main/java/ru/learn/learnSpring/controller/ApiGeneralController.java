package ru.learn.learnSpring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.learn.learnSpring.api.request.ModerationRequest;
import ru.learn.learnSpring.api.response.BaseResponse;
import ru.learn.learnSpring.api.response.InitResponse;
import ru.learn.learnSpring.api.response.PostCalendarResponse;
import ru.learn.learnSpring.service.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;
    private final CalendarService calendarService;
    private final ModerationService moderationService;


    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, CalendarService calendarService, PostService postService, ModerationService moderationService) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.calendarService = calendarService;
        this.moderationService = moderationService;
    }

    @GetMapping("/settings")
    public ResponseEntity<Map<String, Boolean>> settings() {
        return ResponseEntity.ok(settingsService.getGlobalSettings());
    }

    @GetMapping("/init")
    public ResponseEntity<InitResponse> init(){
        return ResponseEntity.ok(initResponse);
    }

    @GetMapping("/calendar")
    private ResponseEntity<PostCalendarResponse> calendar(
            @RequestParam(value = "year", defaultValue = "0") Integer years) {
        return ResponseEntity.ok(calendarService.getPosts(years));
    }

    @PostMapping("/moderation")
    public BaseResponse Restore(@RequestBody ModerationRequest moderationRequest) {
        return moderationService.decisionModeration(moderationRequest.getPostId(), moderationRequest.getDecision());
    }

//    @RequestMapping(path = "/profile/my", method = POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    private ChangeProfileResponse profileMy(@RequestPart(value = "photo") MultipartFile photo,
//                                            @ModelAttribute ChangeProfileRequest changeProfileRequest) throws IOException {
//        return profileService.changeProfile(photo, changeProfileRequest.getEmail(),
//                changeProfileRequest.getName(), changeProfileRequest.getPassword(),
//                changeProfileRequest.getRemovePhoto());
//    }

}
