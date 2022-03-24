package ru.learn.learnSpring.service;

import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.SettingsResponse;

@Service
public class SettingsService {
    public SettingsResponse getGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuserMode(true);
        settingsResponse.setStstisticsIsPublic(true);
        return settingsResponse;
    }
}
