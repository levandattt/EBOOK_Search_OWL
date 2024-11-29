package com.ebook_searching.storage.controller;

import com.ebook_searching.storage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class PublicFileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{bucketName}/{fileName}")
    public ResponseEntity<byte[]> getFile(
            @PathVariable String bucketName,
            @PathVariable String fileName) {
        byte[] fileContent = fileService.getFile(bucketName, fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
