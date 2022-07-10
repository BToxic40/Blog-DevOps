package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.learnSpring.api.response.StatisticResponse;
import ru.learn.learnSpring.service.StatisticsService;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<StatisticResponse> my(){
        return ResponseEntity.ok(statisticsService.collectCurrentUserStatistic());
    }

    @GetMapping("/all")
    public ResponseEntity<StatisticResponse> all(){
        return ResponseEntity.ok(statisticsService.collectAllStatistic());
    }
}
