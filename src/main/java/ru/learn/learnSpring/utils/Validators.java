package ru.learn.learnSpring.utils;

import java.util.Map;
import java.util.Optional;

public class Validators {

    public static Optional<Map.Entry<String, String>> password(String password) {
        if (password.length() < 6) {
            return Optional.of(Map.entry("password", "Пароль короче 6-ти символов"));
        }
        return Optional.empty();
    }

    public static Optional<Map.Entry<String, String>> name(String name) {
        if (name.length() > 20) {
            return Optional.of(Map.entry("name", "Имя не должно превышать 20 символов"));
        }
        return Optional.empty();
    }
}
