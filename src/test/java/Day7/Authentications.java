package Day7;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Authentications{
    
    @Test(priority = 1)
    void  testBasicAuthentication(){
        
        given()
           .auth().basic("postman", "password")

        .when()
            .get("https://postman-echo.com/basic-auth")

        .then()
            .statusCode(200) // Fix the typo here
            .body("authenticated", equalTo(true))
            .log().all();
    }


    @Test(priority = 2)
    void  testDigestAuthentication(){
        
        given()
           .auth().digest("postman", "password")

        .when()
            .get("https://postman-echo.com/basic-auth")

        .then()
            .statusCode(200) // Fix the typo here
            .body("authenticated", equalTo(true))
            .log().all();
    }


    @Test(priority = 3)
    void  testPreemptiveAuthentication(){
        
        given()
           .auth().preemptive().basic("postman", "password")

        .when()
            .get("https://postman-echo.com/basic-auth")

        .then()
            .statusCode(200) // Fix the typo here
            .body("authenticated", equalTo(true))
            .log().all();
    }

    @Test(priority = 4)
    void  testBearerTokenAuthentication() throws Exception {
        
        String bearerToken = "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";

        given()
        .headers("Authorization", "Bearer " + bearerToken)

        .when()
           .get("https://api.github.com/user/repos")

        .then()
           .statusCode(200)
           .log().all();
    }  
       
       @Test(priority = 5)
       void testOAuth2(){
          
        given()
           .auth().oauth2("ghp_bsCr0SuUIiuPRnxSN80dbIxqNhvYL42CpHj8")

        .when()
            .get("https://api.github.com/user/repos")

        .then()
            .statusCode(200)
            .log().all();
       }


       @Test(priority = 6)
       void testAPIKeyAuthentication(){
          
        //Method1
        // given()
        //    .queryParam("appid", "d8cc1c6d097f620e782b0d2d7527b9de")

        // .when()
        //     .get("https://api.openweathermap.org/data/2.5/weather?id=2172797")

        // .then()
        //     .statusCode(200)
        //     .log().all(); 

        //Method2
         given()
             .queryParam("appid", "d8cc1c6d097f620e782b0d2d7527b9de")
             .pathParam("mypath", "data/2.5/weather")
             .queryParam("id", 2172797)
         
         .when()
             .get("https://api.openweathermap.org/{mypath}")
         
         .then()
            .statusCode(200)
            .log().all();
    }
}

