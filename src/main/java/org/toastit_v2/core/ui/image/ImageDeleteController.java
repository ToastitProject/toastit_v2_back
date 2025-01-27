/*
package org.toastit_v2.core.application.image.image.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.core.application.image.service.ImageService;

@RestController
public class ImageDeleteController {

    private final ImageService imageService;

    public ImageDeleteController(@Qualifier("prod") ImageService imageService) {
        this.imageService = imageService;
    }

    @DeleteMapping("/deleteImage")
    public void deleteImage(String fileName){
        imageService.deleteImageFile(fileName);
    }
}
*/
