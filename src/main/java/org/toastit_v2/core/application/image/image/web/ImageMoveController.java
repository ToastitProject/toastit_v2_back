package org.toastit_v2.core.application.image.image.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.core.application.image.service.ImageService;

@RestController
public class ImageMoveController {

    private final ImageService imageService;

    public ImageMoveController(@Qualifier("prod") ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/move")
    public void move(String fileName, String folderName){
        imageService.moveTempToFinal(fileName,folderName);
    }
}
