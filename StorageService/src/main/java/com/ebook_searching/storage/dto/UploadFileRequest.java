package com.ebook_searching.storage.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class UploadFileRequest {
    private String fileName;

    @NotNull
    private String bucketName;

    @NotNull
    private String fileContent;

    @NotNull
    private String contentType;
}
