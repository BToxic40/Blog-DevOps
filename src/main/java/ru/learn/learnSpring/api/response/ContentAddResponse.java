package ru.learn.learnSpring.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentAddResponse {
    private boolean result;
    private ContentAddErrors errors;
}
