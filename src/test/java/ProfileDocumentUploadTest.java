package com.agave.tests.Profile;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.JWT.JwtService;
import com.agave.tests.Utilities.JWT.Profile;

import io.jsonwebtoken.SignatureAlgorithm;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@SuppressWarnings({ "deprecation", "unused" })
public class ProfileDocumentUploadTest {

    private String accessToken;
   
    @BeforeClass
    public void setUp() throws SQLException {
        //DB.truncateTables(new String[] { "profiles_documents", "identification_documents"});
        accessToken = generateAccessToken(1,"safi@gmail.com");
    }

    @Autowired
    private final JwtService jwtService = new JwtService();

    private String generateAccessToken(int id, String email) {

        Profile pr = new Profile();
        pr.setId(Long.valueOf(1));
        pr.setEmail(email);
        
        return jwtService.generateAccessToken(pr);
    }

    @Test(priority = 1)
    public void nickNameFieldShouldNotBeNull(){
        
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

         File file = new File("assets/New.jpg");
         File file2 = new File("assets/big-o-notation.png");

        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .multiPart("profile_picture",file)
                    .multiPart("self_introductory_statement", "abcd")
                    .multiPart("documents", file2)
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")

                    .when()
                    .post(baseURI + "v1/profile/upload-document");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "ニックネームフィールドはnullであってはなりません");
    }

    @Test(priority = 2, dependsOnMethods = {"nickNameFieldShouldNotBeNull"})
    public void nickNameShouldNotTakeOnlyNumbers(){
        
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

         File file = new File("assets/New.jpg");
         File file2 = new File("assets/big-o-notation.png");

        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .multiPart("nick_name","123456")
                    .multiPart("profile_picture",file)
                    .multiPart("self_introductory_statement", "abcd")
                    .multiPart("documents", file2)
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")

                    .when()
                    .post(baseURI + "v1/profile/upload-document");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "ニックネームには数字のみを含めることはできません");
    }

    @Test(priority = 3, dependsOnMethods = {"nickNameShouldNotTakeOnlyNumbers"})
    public void nickNameShouldNotTakeOnlySpecialCharacters(){
        
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

         File file = new File("assets/New.jpg");
         File file2 = new File("assets/big-o-notation.png");

        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .multiPart("nick_name","@#$%^&*")
                    .multiPart("profile_picture",file)
                    .multiPart("self_introductory_statement", "abcd")
                    .multiPart("documents", file2)
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")

                    .when()
                    .post(baseURI + "v1/profile/upload-document");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "ニックネームには特殊文字のみを含めることはできません");
    }

    @Test(priority = 4, dependsOnMethods = {"nickNameFieldShouldNotBeNull"})
    public void userShouldSuccessfullyUploadProfileDocumentWithoutProfilePicture(){

        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        File file = new File("assets/big-o-notation.png");

        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .multiPart("nick_name","safi")
                    .multiPart("self_introductory_statement", "abcd")
                    .multiPart("documents", file)
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")

                    .when()
                    .post(baseURI + "v1/profile/upload-document");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5, dependsOnMethods = {"userShouldSuccessfullyUploadProfileDocumentWithoutProfilePicture"})
    public void userShouldSuccessfullyUploadProfileDocumentWithoutDocuments(){

        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        File file = new File("assets/New.jpg");

        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .multiPart("profile_picture",file)
                    .multiPart("nick_name","safi")
                    .multiPart("self_introductory_statement", "abcd")
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")

                    .when()
                    .post(baseURI + "v1/profile/upload-document");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6, dependsOnMethods = {"userShouldSuccessfullyUploadProfileDocumentWithoutDocuments"})
    public void userShouldNotUploadProfileDocumentWithoutAuthorization(){

        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");

        Response response = given() 
                    .multiPart("profile_picture", file)
                    .multiPart("nick_name","safi")
                    .multiPart("self_introductory_statement", "abcd")
                    .multiPart("documents", file2)
                    .header("Accept-Language","jp")
                    .contentType("multipart/form-data")

                    .when()
                    .post(baseURI + "v1/profile/upload-document");

        Assert.assertEquals(response.getStatusCode(), 403);
    }

    @Test(priority = 7, dependsOnMethods = {"userShouldNotUploadProfileDocumentWithoutAuthorization"})
    public void responseShouldShowErrorMessageWithoutFillingAnyFormData(){

        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        Response response = given() 
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept-Language","jp")

                    .when()
                    .post(baseURI + "v1/profile/upload-document");

        Assert.assertEquals(response.getStatusCode(), 406);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message,"ニックネームフィールドはnullであってはなりません");
    }  


    @Test(priority = 8, dependsOnMethods = {"responseShouldShowErrorMessageWithoutFillingAnyFormData"})
    public void userShouldSuccessfullyUploadProfileDocumentByFillingAllTheField() {
        
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .multiPart("profile_picture", file)
                .multiPart("nick_name", "safi")
                .multiPart("self_introductory_statement", "abcd")
                .multiPart("documents", file2)
                .header("Accept-Language", "jp")
                .contentType("multipart/form-data")
                .when()
                .post(baseURI + "v1/profile/upload-document");

        Assert.assertEquals(response.getStatusCode(), 200);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "成功");
    }

    public String getAccessToken(){
        return accessToken;
    }
}

