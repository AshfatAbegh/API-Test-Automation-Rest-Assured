package Day3;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PathAndQueryParameters {
    
    //https://reqres.in/api/users?page=2&id=7

    @Test
    void testPathAndQueryParameters(){
        
        given()
          .pathParam("mypath", "users") // path parameters
          .queryParam("page", 2) //query parameter
           .queryParam("id", 7)

        .when()
          .get("https://reqres.in/api/{mypath}")
        
        .then()
          .statusCode(200)
          .log().all();
    }
}
