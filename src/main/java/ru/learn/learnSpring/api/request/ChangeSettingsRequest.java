package ru.learn.learnSpring.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChangeSettingsRequest {

    @JsonProperty("STATISTICS_IS_PUBLIC")
    private boolean statisticsIsPublic;
    @JsonProperty("POST_PREMODERATION")
    private boolean postPremoderation;
    @JsonProperty("MULTIUSER_MODE")
    private boolean multiuserMode;
}
