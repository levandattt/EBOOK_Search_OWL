package com.ebook_searching.storage.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String minioEndpoint;
    @Value("${minio.secrets.access_key}")
    private String accessKey;
    @Value("${minio.secrets.secret_key}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        System.out.println("minioClient started, endpoint: " + minioEndpoint);

        return MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(accessKey, secretKey) // Access and secret keys
                .build();
    }
}
