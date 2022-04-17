package ru.learn.learnSpring.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.model.repository.CaptchaRepository;

import java.awt.image.BufferedImage;

@Service
@RequiredArgsConstructor
public class CaptchaService {
    private final CaptchaRepository captchaRepository;

    // сгенерировать код для каптчи
    //--- создать массив разрешенных символов [f, a, e, 3, k, p] -> а33pk
    // на основе кода создать изображение для пользователя
    //--- с помощью библиотеки создать картинку и ресайзнуть
    // сохранить каптчу
    // создать каптчу
    // --- вызывает генерацию каптчи, создание изображения и перевод изображения в base64
    // проверить валидность каптчи (секрет код, код введеный пользователем)
    // --- запросить из бд капчту, проверить код в бд и от пользователя
    // удаление старых каптч
    // --- @Scheduled

    public BufferedImage createCaptchaImage(String code){
        Cage cage = new GCage();
        return  cage.drawImage(code);

    }
}
