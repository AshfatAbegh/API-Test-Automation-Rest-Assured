package Day8;

import static io.restassured.RestAssured.*;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class GetUser {
    
    @Test
    void test_getUser(ITestContext context){

       int id = (int) context.getAttribute("user_id"); //this should come from createuser request
       String bearerToken = "ca63fef4aa6d434c6804eb3e2ea495ae4ac890fc74130e99b0f2c3b75513163c";

        given()
           .headers("Authorization", "Bearer" + bearerToken)
           .pathParam("id", id)

         .when()
           .get("https://gorest.co.in/public/v2/users/{id}")

         .then()
           .statusCode(200)
           .log().all();
    }
}
