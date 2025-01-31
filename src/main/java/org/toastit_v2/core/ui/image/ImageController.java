package org.toastit_v2.core.ui.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.common.response.SuccessResponse;
import org.toastit_v2.common.response.code.SuccessCode;
import org.toastit_v2.core.application.image.service.ImageService;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "Image Management",
        description = "이미지 업로드, 이동 및 삭제 API"
)
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(
            summary = "이미지 업로드",
            description = "이미지를 업로드하는 API"
    )
    @PostMapping(value = "/uploads",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<String>> upload(
            @Parameter(description = "업로드할 파일")
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "업로드할 폴더 이름")
            @RequestParam("folderName") String folderName){
        String fileUrl = imageService.uploadFile(file, folderName);
        return ResponseEntity.ok(new SuccessResponse<>(fileUrl, SuccessCode.SUCCESS.getHttpStatus(), SuccessCode.SUCCESS.getMessage(), SuccessCode.SUCCESS.getStatusCode()));
    }

    @Operation(
            summary = "임시 이미지 업로드",
            description = "임시로 이미지를 업로드하는 API"
    )
    @PostMapping(value = "/uploads/temporary",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<String>> temporary(
            @Parameter(description = "임시로 업로드할 파일")
            @RequestParam("file") MultipartFile file) {
        String fileUrl = imageService.uploadFileToTemp(file);
        return ResponseEntity.ok(new SuccessResponse<>(fileUrl, SuccessCode.SUCCESS.getHttpStatus(), SuccessCode.SUCCESS.getMessage(), SuccessCode.SUCCESS.getStatusCode()));
    }

    @Operation(
            summary = "프로필 이미지 업로드",
            description = "프로필 이미지를 업로드하는 API"
    )
    @PostMapping("/uploads/profileImage")
    public ResponseEntity<SuccessResponse<String>> uploadProfileImage(
            @Parameter(description = "업로드할 프로필 이미지 파일")
            @RequestParam("file") MultipartFile file) {
        String fileUrl = imageService.uploadProfileImage(file, 100, 100);
        return ResponseEntity.ok(new SuccessResponse<>(fileUrl, SuccessCode.SUCCESS.getHttpStatus(), SuccessCode.SUCCESS.getMessage(), SuccessCode.SUCCESS.getStatusCode()));
    }

    @Operation(
            summary = "임시 이미지 이동",
            description = "임시 이미지를 최종 위치로 이동하는 API"
    )
    @PostMapping("/move")
    public ResponseEntity<SuccessResponse<Object>> move(
            @Parameter(description = "이동할 파일 이름")
            @RequestParam("fileName") String fileName,
            @Parameter(description = "목표 폴더 이름")
            @RequestParam("folderName") String folderName) {
        imageService.moveTempToFinal(fileName, folderName);
        return ResponseEntity.ok(new SuccessResponse<>(null, SuccessCode.SUCCESS.getHttpStatus(), SuccessCode.SUCCESS.getMessage(), SuccessCode.SUCCESS.getStatusCode()));
    }

    @Operation(
            summary = "이미지 삭제",
            description = "이미지를 삭제하는 API"
    )
    @DeleteMapping("/deleteImage")
    public ResponseEntity<SuccessResponse<Object>> deleteImage(
            @Parameter(description = "삭제할 이미지 파일 이름")
            @RequestParam("fileName") String fileName) {
        imageService.deleteImageFile(fileName);
        return ResponseEntity.ok(new SuccessResponse<>(null, SuccessCode.SUCCESS.getHttpStatus(), SuccessCode.SUCCESS.getMessage(), SuccessCode.SUCCESS.getStatusCode()));
    }
}
