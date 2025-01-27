package org.toastit_v2.core.application.image.util.resizer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageResizer {

    /**
     * 이미지의 크기를 변경하여 반환하는 메서드 입니다.
     *
     * @param originalImage :이미지 크기를 변경할 원본 이미지 입니다.
     * @param targetWidth : 변경하고자 하는 이미지의 Width 입니다.
     * @param targetHeight : 변경하고자 하는 이미지의 Height 입니다.
     * @return : 인자 값으로 입력된 Width 와 Height 원본 이미지의 크기를 변경하여 반환 합니다.
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }
}
