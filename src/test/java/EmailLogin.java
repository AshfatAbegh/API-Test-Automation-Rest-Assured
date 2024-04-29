package com.agave.tests.Auth;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;
import com.agave.tests.Utilities.PasswordEncoder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmailLoginTest {

    @BeforeClass
    public void dbSetUp() throws SQLException {
        String[] tables = {"profiles"};
        DB.truncateTables(tables);
        
        String plainTextPassword = "Asif9878";
        String encodedPassword = PasswordEncoder.encodePassword(plainTextPassword);
        insertTestUser("asif@gmail.com", encodedPassword);
    } 

    private void insertTestUser(String email, String password) throws SQLException {
        DB.getConnection().executeUpdate("INSERT INTO `profiles` (`email`, `password`, `isFromGoogle`) VALUES ('" + email + "', '" + password + "', 0)");
    }
    

    @Test(priority = 1)
    public void userShouldLoginUsingExistingEmail() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asif@gmail.com");
        requestBody.put("password", "Asif9878");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .header("Accept-Language", "jp")
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        System.out.println("Response body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void userShouldNotLoginWithoutExistingEmail() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asi@gmail.com");
        requestBody.put("password", "Asif9878");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .header("Accept-Language", "jp")
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message,"指定されたログイン情報が間違っています");
    }

    @Test(priority = 3)
    public void userShouldNotLoginWithInvalidPassword() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "asif@gmail.com");
        requestBody.put("password", "Asif987");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .header("Accept-Language", "jp")
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message,"指定されたログイン情報が間違っています");
    }

    @Test(priority = 4)
    public void userShouldNotLoginWithoutInsertingEmailAndPassword() {
        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Accept-Language", "jp")
                .when()
                .post(baseURI + "v1/auth/login-auth-email");

        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message,"無効な JSON 形式");
    }
}







