package ru.learn.learnSpring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.api.response.CalendarDaysResponse;
import ru.learn.learnSpring.api.response.InitResponse;
import ru.learn.learnSpring.service.CalendarService;
import ru.learn.learnSpring.service.SettingsService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;
    private final CalendarService calendarService;

    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, CalendarService calendarService) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.calendarService = calendarService;
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
    public ResponseEntity<CalendarDaysResponse> calendar(@RequestParam (required = false) String year){
        return ResponseEntity.ok(calendarService.calendarDaysResponse(year));
    }

}
