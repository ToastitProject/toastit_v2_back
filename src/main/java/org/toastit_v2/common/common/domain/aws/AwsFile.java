package org.toastit_v2.common.common.domain.aws;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AwsFile {

    private String filename;
    private String contentType;
    private String fileType;
    private String filePath;
    private Long size;

    @Builder
    public AwsFile(String filename, String contentType, String fileType, String filePath, Long size) {
        this.filename = filename;
        this.contentType = contentType;
        this.fileType = fileType;
        this.filePath = filePath;
        this.size = size;
    }

}
