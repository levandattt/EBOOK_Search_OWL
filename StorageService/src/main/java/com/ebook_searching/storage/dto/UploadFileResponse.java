package com.ebook_searching.storage.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UploadFileResponse {
    private Long id;
    private String fileName;
    private String filePath;
    private String contentType;
    private Long size;
    private LocalDateTime createdAt;
}
