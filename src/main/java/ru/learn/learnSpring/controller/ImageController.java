package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.learn.learnSpring.service.ImageService;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @RequestMapping(path = "/image", method = POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addImage(@RequestPart(value = "image") MultipartFile image) throws IOException {
        log.info(image.getOriginalFilename());
        return ResponseEntity.ok().body(imageService.addImage(image));
    }
}
