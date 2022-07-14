package ru.learn.learnSpring.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.request.RegistrationRequest;
import ru.learn.learnSpring.api.response.CaptchaResponse;
import ru.learn.learnSpring.exception.CanNotCreateCaptchaException;
import ru.learn.learnSpring.model.CaptchaCode;
import ru.learn.learnSpring.model.repository.CaptchaRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaptchaService {
    public static final int CAPTCHA_CODE_LENGTH = 5;
    public static final String CAPTCHA_FORMAT_IMAGE = "png";
    public static final long UPDATE_FOR_CAPTCHA = 3600;
    private final CaptchaRepository captchaRepository;
    public final static int CAPTCHA_LENGTH = 100;
    public final static String CAPTCHA_SYMBOLS = "abcdfgkp12356789";
    public final static String BASE64_HEADER = "data:image/png;base64, ";
    public static final int TIME_TO_DELETE = 1800000;
//    private final UserRepository userRepository;

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

    @Transactional
    @Scheduled(fixedDelay = TIME_TO_DELETE)
    public void deleteOldCaptcha() {
        captchaRepository.delete();
    }

    boolean isCaptchaValid(RegistrationRequest request) {

        List<CaptchaCode> captchaCodes =
                new ArrayList<>(captchaRepository.findBySecretCode(request.getCaptchaSecretCode()));

        if (captchaCodes.isEmpty()) {
            return false;
        }

        CaptchaCode captchaCode = captchaCodes.get(0);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = captchaCode.getTime();
        long hour = ChronoUnit.HOURS.between(startTime, endTime);

        if (hour >= UPDATE_FOR_CAPTCHA) {
            return false;
        }

        return captchaCode.getCode().equals(request.getUserInputCaptcha());
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

