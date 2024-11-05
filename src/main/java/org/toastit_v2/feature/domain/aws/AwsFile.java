package org.toastit_v2.feature.domain.aws;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AwsFile {

    private String filename;

    private String contentType;

    private Long size;

    private String fileType;

    private String filePath;

    @Builder
    public AwsFile(String filename, String contentType, Long size, String fileType, String filePath) {
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
        this.fileType = fileType;
        this.filePath = filePath;
    }
}
