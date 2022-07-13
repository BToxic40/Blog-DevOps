package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.learn.learnSpring.api.request.ChangeSettingsRequest;
import ru.learn.learnSpring.service.SettingsService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settings")
@Slf4j
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("")
    public ResponseEntity<Map<String, Boolean>> settings() {
        return ResponseEntity.ok(settingsService.getGlobalSettings());
    }

    @PutMapping(path = "")
    @PreAuthorize("hasAuthority('user:moderate')")
    public void change(@RequestBody ChangeSettingsRequest changeSettingsRequest) {
        log.info("{} - {}", "new settings", changeSettingsRequest);
        settingsService.saveSettings(changeSettingsRequest);
    }
}
