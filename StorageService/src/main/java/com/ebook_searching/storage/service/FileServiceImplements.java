package com.ebook_searching.storage.service;

import com.ebook_searching.storage.dto.*;
import com.ebook_searching.storage.model.File;
import com.ebook_searching.storage.repository.FileRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImplements implements FileService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public UploadFileResponse uploadFile(UploadFileRequest fileRequest) {
        try {
            // Convert Base64 content to byte array
            byte[] fileBytes = java.util.Base64.getDecoder().decode(fileRequest.getFileContent());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);

            // Upload the file to MinIO
            String bucketName = fileRequest.getBucketName();
            String fileName;
            if (fileRequest.getFileName() != null) {
                fileName = fileRequest.getFileName();
            } else {
                fileName = UUID.randomUUID() + "." + fileRequest.getContentType();
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, fileBytes.length, -1)
                            .build()
            );

            // Save file metadata in the database
            File fileEntity = new File();
            fileEntity.setFileName(fileName);
            fileEntity.setFilePath(bucketName + "/" + fileName);
            fileEntity.setSize((long) fileBytes.length);
            fileEntity.setContentType(fileRequest.getContentType());
            fileRepository.save(fileEntity);

            UploadFileResponse uploadFileResponse = new UploadFileResponse();
            uploadFileResponse.setFilePath(bucketName + "/" + fileName);

            return uploadFileResponse;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] getFile(String bucketName, String fileName) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder().bucket(bucketName).object(fileName).build())) {
            return stream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching file: " + e.getMessage(), e);
        }
    }
}
