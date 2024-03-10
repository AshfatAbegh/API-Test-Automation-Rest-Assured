// package Day6;

// import org.testng.annotations.Test;

// import static io.restassured.RestAssured.*;
// import static org.hamcrest.Matchers.*;

// import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath; // Import the missing class

// public class JSONSChemaValidation {

//     @Test
//     void JSONSchemaValidation() {
//         given()
//         .when()
//            .get("https://jsonplaceholder.typicode.com/users")
//         .then()
//            .assertThat().body(matchesJsonSchemaInClasspath("JSONSchema/JSONSchema.json"));
//     }
// }


