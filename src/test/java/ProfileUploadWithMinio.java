package com.agave.tests.Profile.ProfileDocument;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProfileDocumentUpload {

    public static void uploadToMinioServer(String minioUrl, String accessKey, String secretKey, String bucketName, String objectName, File file) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey, secretKey)
                    .build();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(new FileInputStream(file), file.length(), -1)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJwcm9maWxlSWQiOjgsInN1YiI6InNha2liQGdtYWlsLmNvbSIsImlhdCI6MTcxMzg1MTk5NSwiZXhwIjoxNzEzOTM4Mzk1fQ.WASb_jd9J6ZJXTfiOajePCjQl_sFpT76bL939m9g2B0";

    @Test(priority = 1)
    public void userShouldSuccessfullyUploadProfileDocument() throws FileNotFoundException {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/APIDocSample.pdf");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + bearerToken)
                .multiPart("profilePicture", file)
                .multiPart("nick_name", "sakib")
                .multiPart("self_introductory_statement", "abcd")
                .multiPart("documents", file2)
                .header("Accept-Language", "jp")
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/profile/upload-document");

        Assert.assertEquals(response.getStatusCode(), 200);

        String minioUrl = "http://local-minio-test-1:9000";
        String accessKey = "root";
        String secretKey = "agave_root";
        String bucketName = "agave";

        uploadToMinioServer(minioUrl, accessKey, secretKey, bucketName, "New.jpg", file);
        uploadToMinioServer(minioUrl, accessKey, secretKey, bucketName, "APIDocSample.pdf", file2);
    }
}
