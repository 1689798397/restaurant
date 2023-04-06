package com.example.common.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    @Bean
    public MinioClient minioClient() throws Exception{
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioClient minioClient = new MinioClient.Builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
        // 检查存储桶是否已经存在
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if(bucketExists) {
            System.out.println("Bucket already exists.");
        } else {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        return minioClient;
    }
}