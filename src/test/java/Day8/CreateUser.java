package Day8;

import org.json.JSONObject;
import static io.restassured.RestAssured.*;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class CreateUser {
    
    @Test
    void test_createUser(ITestContext context){
        
       Faker faker = new Faker(); 
        
       JSONObject data = new JSONObject();

       data.put("name",faker.name().fullName());
       data.put("gender","Male");
       data.put("email",faker.internet().emailAddress());
       data.put("status","inactive");

       String bearerToken = "ca63fef4aa6d434c6804eb3e2ea495ae4ac890fc74130e99b0f2c3b75513163c";
  
       int id = given()
           .headers("Authorization","Bearer " + bearerToken)
           .contentType("application/json")
           .body(data.toString())

        .when()
           .post("https://gorest.co.in/public/v2/users")
           .jsonPath().getInt("id");

           System.out.println("Generated id is: " + id);

           context.setAttribute("user_id", id);
    }
}
