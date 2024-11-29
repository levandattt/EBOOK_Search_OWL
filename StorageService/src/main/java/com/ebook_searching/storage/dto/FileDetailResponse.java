package com.ebook_searching.storage.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class FileDetailResponse {
    private Long id;
    private String fileName;
    private String filePath;
    private Long size;
    private String contentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, String> metadata;
}
