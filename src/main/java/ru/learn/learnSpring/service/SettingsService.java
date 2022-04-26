package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.model.GlobalSettings;
import ru.learn.learnSpring.model.repository.GlobalSettingsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final GlobalSettingsRepository globalSettingsRepository;
    public static final String YES_PARAM = "YES";
    public Map<String, Boolean> getGlobalSettings() {
        List<GlobalSettings> globalSettingsList = globalSettingsRepository.findAll();
        Map<String, Boolean> response = new HashMap<>();

        for (GlobalSettings setting: globalSettingsList) {
            response.put(setting.getCode(), setting.getValue().equals(YES_PARAM));
        }
        return response;
    }
}
