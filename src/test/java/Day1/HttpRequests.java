package Day1;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

/* given() -> Content Type, SSsett Cookies, Add Auth, Add Param, Set Headers Info etc... 
 * when() ->  Get, Post, Put, Delete
 * then() ->  Validate Status Code, Extract Response, Extract Headers Cookies & Response Body... 
*/
public class HttpRequests {
    
    int id;

    @Test(priority = 1)
    void getUsers(){

      given()

         .when()
          .get("https://reqres.in/api/users?page=2")

         .then()
           .statusCode(200)
           .body("page", equalTo(2))
           .log().all();
    }

    @Test(priority = 2)
    void createUser(){

      HashMap hm = new HashMap();
      hm.put("name","Rohan");
      hm.put("job","Trainer");

      
        id = given()
            .contentType("application/json")
            .body(hm)

        .when()
            .post("https://reqres.in/api/users")
            .jsonPath().getInt("id");

        // .then()
        //     .statusCode(201)
        //      .log().all();
    }

    @Test(priority = 3, dependsOnMethods = {"createUser"})
    void updateUser(){
          
      HashMap hm = new HashMap();
      hm.put("name","Araf");
      hm.put("job","Teacher");

      
      given()
            .contentType("application/json")
            .body(hm)

        .when()
            .put("https://reqres.in/api/users/" + id)

        .then()
            .statusCode(200)
            .log().all();
    }
    
    @Test(priority = 4)
    void deleteUser(){

        given()
           
        .when()
            .delete("https://reqres.in/api/users/" + id)

        .then()
            .statusCode(204);
    }
}
