package com.example.common.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class Uploader {
    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private MinioClient minioClient;

    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        return minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + fileName;
    }
}
