package com.ebook_searching.storage.controller;

import com.ebook_searching.storage.dto.UploadFileRequest;
import com.ebook_searching.storage.dto.UploadFileResponse;
import com.ebook_searching.storage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping
    public UploadFileResponse uploadFile(@Valid @RequestBody UploadFileRequest fileRequest) {
        return fileService.uploadFile(fileRequest);
    }


}
