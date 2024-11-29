package com.ebook_searching.storage.service;

import com.ebook_searching.storage.dto.UploadFileRequest;
import com.ebook_searching.storage.dto.UploadFileResponse;

public interface FileService {
    UploadFileResponse uploadFile(UploadFileRequest fileRequest);
    byte[] getFile(String bucketName, String fileName);
}
