package ru.learn.learnSpring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.learn.learnSpring.service.ImageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @PostMapping(path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> addImage(@RequestPart(value = "image") MultipartFile image) {
        log.info(image.getOriginalFilename());
        return ResponseEntity.ok().body(imageService.addImage(image));
    }
}
