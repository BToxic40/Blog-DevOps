package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.request.ChangeSettingsRequest;
import ru.learn.learnSpring.model.GlobalSettings;
import ru.learn.learnSpring.model.repository.GlobalSettingsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final GlobalSettingsRepository globalSettingsRepository;
    public static final String YES = "YES";

    public static final String NO = "NO";

    public Map<String, Boolean> getGlobalSettings() {
        List<GlobalSettings> globalSettingsList = globalSettingsRepository.findAll();
        Map<String, Boolean> response = new HashMap<>();

        for (GlobalSettings setting : globalSettingsList) {
            response.put(setting.getCode(), setting.getValue().equals(YES));
        }
        return response;
    }

    public void saveSettings(ChangeSettingsRequest request) {
        List<GlobalSettings> globalSettings = globalSettingsRepository.findAll();

        for (GlobalSettings setting : globalSettings) {
            if (setting.getCode().equals("MULTIUSER_MODE")) {
                setting.setValue(request.isMultiuserMode() ? YES : NO);
            }

            if (setting.getCode().equals("POST_PREMODERATION")) {
                setting.setValue(request.isPostPremoderation() ? YES : NO);
            }

            if (setting.getCode().equals("STATISTICS_IS_PUBLIC")) {
                setting.setValue(request.isStatisticsIsPublic() ? YES : NO);
            }
        }

        globalSettingsRepository.saveAll(globalSettings);
    }
}
