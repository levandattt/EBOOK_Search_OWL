package com.ebook_searching.storage.dto;
import lombok.Data;

import java.util.Map;
import java.time.LocalDateTime;

@Data
public class UpdateFileResponse {
    private Long id;
    private Map<String, String> metadata;
    private LocalDateTime updatedAt;
}
