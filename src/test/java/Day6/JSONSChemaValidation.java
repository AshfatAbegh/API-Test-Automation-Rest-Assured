package Day6;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.module.jsv.JsonSchemaValidator; 

public class JSONSChemaValidation {

    @Test
    void JSONSchemaValidation() {
        given()
        .when()
           .get("https://jsonplaceholder.typicode.com/users")
        .then()
           .assertThat().body(matchesJsonSchemaInClasspath("JSONSchema.json"));
    }
}


