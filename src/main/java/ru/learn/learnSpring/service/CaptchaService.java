package ru.learn.learnSpring.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.CaptchaResponse;
import ru.learn.learnSpring.exception.CanNotCreateCaptchaException;
import ru.learn.learnSpring.model.CaptchaCode;
import ru.learn.learnSpring.model.repository.CaptchaRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaptchaService {
    public static final int CAPTCHA_CODE_LENGTH = 5;
    public static final String CAPTCHA_FORMAT_IMAGE = "png";
    private final CaptchaRepository captchaRepository;
    public final static int CAPTCHA_LENGTH = 100;
    public final static String CAPTCHA_SYMBOLS = "abcdfgkp12356789";
    public final static String BASE64_HEADER = "data:image/png;base64, ";
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

    public CaptchaResponse createCaptcha() {

        String code = generateCaptchaCode();
        BufferedImage captchaImage = createCaptchaImage(code);
        BufferedImage resizedImage = resizeImage(captchaImage);
        String base64Image = imageToBase64(resizedImage);
        UUID secretCode = UUID.randomUUID();
        log.info("code={}", code);

        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setCode(code);
        captchaCode.setSecretCode(secretCode.toString());
        captchaCode.setTime(LocalDateTime.now());

        captchaRepository.save(captchaCode);

        CaptchaResponse captchaResponse = new CaptchaResponse();
        captchaResponse.setImage(base64Image);
        captchaResponse.setSecret(secretCode.toString());
        return captchaResponse;
    }

    private String imageToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, CAPTCHA_FORMAT_IMAGE, baos);
            byte[] bytes = baos.toByteArray();
            return BASE64_HEADER + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new CanNotCreateCaptchaException(e);
        }
    }

    private String generateCaptchaCode() {
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < CAPTCHA_CODE_LENGTH; i++) {
            int number = random.nextInt(CAPTCHA_SYMBOLS.length());
            captcha.append(CAPTCHA_SYMBOLS.charAt(number));
        }

        return captcha.toString();
    }

    public BufferedImage createCaptchaImage(String code) {
        Cage cage = new GCage();
        return cage.drawImage(code);
    }

    public BufferedImage resizeImage(BufferedImage originalImage) {
        return Scalr.resize(originalImage, CAPTCHA_LENGTH);
    }
}

