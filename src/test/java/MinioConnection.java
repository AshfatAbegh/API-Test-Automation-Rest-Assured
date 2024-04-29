package com.agave.tests.Utilities;

import io.minio.MinioClient;

public class MinioConfig {

    public static MinioClient getMinioClient(String minioUrl, String accessKey, String secretKey) {
        try {
            return MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey, secretKey)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

