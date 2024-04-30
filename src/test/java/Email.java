package com.agave.tests.Auth;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmailRegistration {

    private String authorizationCode;
    
    private String getApiToken() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
        
        Response response = given()
                .queryParam("app_id", "agave")
                .when()
                  .get(baseURI + "v1/auth/otp/api-token")
                .then()
                  .contentType("application/json")
                  .extract()
                  .response();

        return response.jsonPath().getString("api_token");
    }

    private void shootOtp(String apiToken) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("app_id", "agave");
        hm.put("api_token", apiToken);
        hm.put("email","rakib@gmail.com");

        RestAssured.baseURI = "http://agave-otp-test:8080/api/";
        
        given()
                .contentType(ContentType.JSON)
                .body(hm)
                .when()
                .post(baseURI + "shoot-otp/email");
    }

    private String validateOtp(String apiToken) {
        RestAssured.baseURI = "http://agave-otp-test:8080/api/";

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("app_id", "agave");
        requestBody.put("api_token", apiToken);
        requestBody.put("email", "rakib@gmail.com");
        requestBody.put("otp", "1111");

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "validate-otp/email")
                .path("authorization_code");
    }

     @BeforeMethod
     public void authorizationCodesetup() {
        String apiToken = getApiToken();
        shootOtp(apiToken);
        authorizationCode = validateOtp(apiToken);;
    }

    private String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hashed bytes
            byte[] hashedBytes = md.digest();

            // Convert byte array to a string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Test
    public void userShouldNotBeRegisteredWithAlreadyExistingEmail() throws SQLException {
    DB.getConnection().executeUpdate("DELETE FROM `profiles`");

    String hashedPassword = hashPassword("Rakib9878");
    DB.getConnection().executeUpdate("INSERT INTO `profiles` (`id`, `email`, `isEmailVerified`, `isPhoneVerified`, `password`, `isFromGoogle`) VALUES (2, 'rakib@gmail.com', b'0', b'0', '" + hashedPassword + "', 0)");

    ResultSet resultSet = DB.getConnection().executeQuery("SELECT * FROM `profiles`");
    resultSet.next();

    RestAssured.baseURI = "http://agave-api-test:8080/api/";

    HashMap<String, String> requestBody = new HashMap<>();
    requestBody.put("email", "rafi@gmail.com");
    requestBody.put("password", "Rafi9878");
    requestBody.put("authorization_code", authorizationCode); 

    Response response = given()
             .contentType(ContentType.JSON)
             .body(requestBody)
             .header("Accept-Language", "jp")
             .when()
             .post(baseURI + "v1/auth/register-auth-email");

    System.out.println(response.asString());

    Assert.assertEquals(response.getStatusCode(), 400);

    String message = response.getBody().jsonPath().getString("message");
    Assert.assertEquals(message, "このメールは既に存在します");
}

    public String getAuthorizationCode(){
        return authorizationCode;
    }
}
