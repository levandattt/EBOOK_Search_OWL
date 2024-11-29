package com.ebook_searching.storage.dto;

import lombok.Data;
import java.util.Map;

@Data
public class UpdateFileRequest {
    private Map<String, String> metadata; // Metadata to update
}
