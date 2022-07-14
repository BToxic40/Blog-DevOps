package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.learn.learnSpring.api.response.PostAddResponse;
import ru.learn.learnSpring.exception.MaxImageSizeException;
import ru.learn.learnSpring.exception.UploadImageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    public static final int MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Value("${app.files.images.folder}")
    private String uploadFolder;

    @Value("${app.files.images.folder.posts}")
    private String postsFolder;

    public String addImage(MultipartFile image)  {
        PostAddResponse postResponse = new PostAddResponse();

        Map<String, String> error = new HashMap<>();

        if (image.getSize() > MAX_FILE_SIZE) {
            postResponse.setResult(false);
            error.put("image", "Размер файла превышает допустимый размер 5 мб");
            postResponse.setErrors(error);
            throw new MaxImageSizeException();
        }
            postResponse.setResult(true);
            String nameImage = randomNameGeneration();
            writeImageInServer(image, nameImage);

            return "/upload/" + nameImage + ".png";
    }

    public String randomNameGeneration() {
        return UUID.randomUUID().toString();

    }

    public void writeImageInServer(MultipartFile image, String nameImage) {
        log.info(uploadFolder);

        try {
            InputStream initialStream = image.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
            BufferedImage imageBuffer = ImageIO.read(byteArrayInputStream);

            Path path = Path.of(uploadFolder);
            path = path.resolve(postsFolder);

            log.info("путь до папки с изображениями = {}", path.toAbsolutePath());

            Files.createDirectories(path);
            ImageIO.write(imageBuffer, "png", new File(path.toAbsolutePath() + "/" + nameImage + ".png"));
        } catch (IOException e) {
            throw new UploadImageException(e);
        }
    }
}
