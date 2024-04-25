package com.agave.tests.Auth;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmailRegistrationTest {

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
        hm.put("email","asif@gmail.com");

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
        requestBody.put("email", "asif@gmail.com");
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
        authorizationCode = validateOtp(apiToken);
    }


     @Test(priority = 1)
     public void userShouldBeRegisteredWithNewEmail() throws SQLException {
        
    // Disable or drop the foreign key constraint between profiles and profile_documents
    DB.getConnection().executeUpdate("ALTER TABLE profiles_documents DROP FOREIGN KEY FKl05ptoblhcm9ycmle73jdtlxm");
    //DB.getConnection().executeUpdate("INSERT INTO `profiles` (`id`, `email`, `isEmailVerified`, `isPhoneVerified`, `password`, `isFromGoogle`) " +
                                           "VALUES ('1', 'asif@gmail.com', b'0', b'0', 'Asif9878', 0)");

    // Truncate the profiles table
    DB.getConnection().executeUpdate("TRUNCATE `profiles`");

    // Re-enable or recreate the foreign key constraint
    DB.getConnection().executeUpdate("ALTER TABLE profiles_documents ADD CONSTRAINT FKl05ptoblhcm9ycmle73jdtlxm FOREIGN KEY (Profile_id) REFERENCES profiles(id)");

  
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
    
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asif@gmail.com");
        requestBody.put("password", "Asif9878");
        requestBody.put("authorization_code", authorizationCode);
    
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .header("Accept-Language", "jp")
                .when()
                .post(baseURI + "v1/auth/register-auth-email")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }  

    @Test(priority = 2)
     public void userShouldNotBeRegisteredWithExistingEmail() {
    
        RestAssured.baseURI = "http://agave-api-test:8080/api/";
    
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asif@gmail.com");
        requestBody.put("password", "Asif9878");
        requestBody.put("authorization_code", authorizationCode);
    
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .header("Accept-Language", "jp")
                .when()
                .post(baseURI + "v1/auth/register-auth-email")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 400);

        String message = response.getBody().jsonPath().getString("message");
        Assert.assertEquals(message, "このメールは既に存在します");
    }  

    public String getAuthorizationCode(){
        return authorizationCode;
    }
}

