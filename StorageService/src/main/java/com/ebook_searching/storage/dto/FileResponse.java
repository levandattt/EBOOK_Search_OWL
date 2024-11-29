package com.ebook_searching.storage.dto;

import lombok.Data;

@Data
public class FileResponse {
    private Long id;
    private String fileName;
    private String filePath;
}
