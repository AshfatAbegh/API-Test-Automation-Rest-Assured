package Day8;

import static io.restassured.RestAssured.*;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class DeleteUser {
    
    @Test
    void test_deleteUser(ITestContext context) {

    String bearerToken = "ca63fef4aa6d434c6804eb3e2ea495ae4ac890fc74130e99b0f2c3b75513163c";

    int id = (int) context.getAttribute("user_id");

    given()
      .headers("Authorization","Bearer " + bearerToken)
      .pathParam("id", id)

     .when()
       .delete("https://gorest.co.in/public/v2/users/{id}")

     .then()
       .statusCode(200)
       .log().all();
  }
}
