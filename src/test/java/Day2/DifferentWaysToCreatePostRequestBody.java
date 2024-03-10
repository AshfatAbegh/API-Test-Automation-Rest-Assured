package Day2;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class DifferentWaysToCreatePostRequestBody {
    
    //Post Request Body Using HashMap()

    //@Test(priority = 1)
    void testPostUsingHashMap(){

       HashMap data = new HashMap();

       data.put("name","Nadim");
       data.put("location","France");
       data.put("phone","0123456");

       String coursesArr[] =  {"C","C++"};
       data.put("courses",coursesArr);

       given()
           .contentType("application/json")
           .body(data)

        .when()
            .post("https://localhost:3000/students")
        
        .then()
             .statusCode(201)
             .body("name",equalTo("Nadim"))
             .body("location",equalTo("France"))
             .body("phone",equalTo("123456"))
             .body("courses[0]",equalTo("C"))
             .body("courses[1]",equalTo("C++"))

             .header("Content-Type", "application/json; charset=utf-8")
    
             .log().all();
    }   

    //Post Request Body Using org.json library

    //@Test(priority = 1)
    void testPostUsingJsonLibrary() {

        JSONObject obj = new JSONObject();
        obj.put("name", "Scott");
        obj.put("location", "France");
        obj.put("phone", "123456");
    
        String coursesArr[] = {"C", "C++"};
        obj.put("courses", coursesArr);
    
        given()
                .contentType("application/json")
                .body(obj.toString()) // -> toString() method is only required for json library
            .when()
                .post("https://localhost:3000/students")
            .then()
                .statusCode(201)
                .body("name", equalTo("Nadim")) 
                .body("location", equalTo("France"))
                .body("phone", equalTo("123456")) 
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json; charset=utf-8")
               
                .log().all();
    }

    //Post Request Body Using POJO Class -> Plain Old Java Object

    //@Test(priority = 1)
    void testPostUsingPOJO() {

       POJO_PostRequest data = new POJO_PostRequest();

       data.setName("Nadim");
       data.setLocation("France");
       data.setPhone("123456");

       String coursesArr[] = {"C","C++"};
       data.setCourses(coursesArr);
    
        given()
                .contentType("application/json")
                .body(data)
            .when()
                .post("https://localhost:3000/students")
            .then()
                .statusCode(201)
                .body("name", equalTo("Nadim")) 
                .body("location", equalTo("France"))
                .body("phone", equalTo("123456")) 
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json; charset=utf-8")
                .log().all();
    }

    //Post Request Body Using External JSON File

    @Test(priority = 1)
    void testPostUsingExternalJsonFile() throws FileNotFoundException {

        File f = new File("\\Body.json");

        FileReader fr = new FileReader(f);

        JSONTokener jt = new JSONTokener(fr);

        JSONObject data = new JSONObject(jt);
    
        given()
                .contentType("application/json")
                .body(data)
            .when()
                .post("https://localhost:3000/students")
            .then()
                .statusCode(201)
                .body("name", equalTo("Nadim")) 
                .body("location", equalTo("France"))
                .body("phone", equalTo("123456")) 
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json; charset=utf-8")
                .log().all();
    }
    

    @Test(priority = 2)
    void testDelete(){
        
        given()

          .when()
            .delete("https://localhost:3000/students/4")

            .then()
                 .statusCode(201);
    }
}
