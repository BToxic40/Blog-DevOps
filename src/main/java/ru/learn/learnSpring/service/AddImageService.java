package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.learn.learnSpring.api.response.PostAddResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddImageService {

    public Object addImage(MultipartFile image) throws IOException {
        PostAddResponse postResponse = new PostAddResponse();

        Map<String, String> error = new HashMap<>();

        if (image.getSize() > 5242880) {
            postResponse.setResult(false);
            error.put("image", "Размер файла превышает допустимый размер 5 мб");
            postResponse.setErrors(error);
            return postResponse;
        } else {
            postResponse.setResult(true);
            String nameImage = randomNameGeneration();
            writeImageInServer(image, nameImage);

            return "/upload/" + nameImage + ".png";
        }

    }

    public String randomNameGeneration() {
        return "ab/" + UUID.randomUUID().toString();

    }

    public void writeImageInServer(MultipartFile image, String nameImage) throws IOException {
        InputStream initialStream = image.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        BufferedImage imageBuffer = ImageIO.read(byteArrayInputStream);

        ImageIO.write(imageBuffer, "png", new File("src/main/resources/upload/" + nameImage + ".png"));

    }

}
