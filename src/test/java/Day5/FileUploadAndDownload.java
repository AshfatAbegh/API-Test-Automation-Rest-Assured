package Day5;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.Test;

public class FileUploadAndDownload {

    @Test(priority = 1)
    void singleFileUpload() {

        File myfile = new File("/home/ashfatrashid/Downloads/Body.json");

        given()
            .multiPart("file", myfile) //Equivalent to form-data with post request body in postman 
            .contentType("multipart/form-data")

        .when()
            .post("http://localhost:8080/uploadFile")

        .then()
            .statusCode(200)
            .body("filename", equalTo("Body.json"));
    }


    // @Test
    // void multipleFilesUpload() {

    //     File myfile1 = new File("/home/ashfatrashid/Downloads/Body.json");
    //     File myfile2 = new File("/home/ashfatrashid/Downloads/Body.json");
       
    //     //Array Approach for storing files but won't work for all typos of API
    //     //File filearr[] = {myfile1, myfile2};

    //     given()
    //         .multiPart("files", myfile1) //Equivalent to form-data with post request body in postman 
    //         .multiPart("files", myfile2) 

    //         //.multiPart("files", filearr) -> Array Approach
    //         .contentType("multipart/form-data")

    //     .when()
    //         .post("http://localhost:8080/uploadMultipleFiles")

    //     .then()
    //         .statusCode(200)
    //         .body("[0].filename", equalTo("Body1.json"))
    //         .body("[1].filename", equalTo("Body2.json"));
    // }

    @Test(priority = 2)
    void fileDownload(){
        
        given()

        .when()
           .get("http://localhost:8080/downloadFile/Body1.json") //-> For Test1

        .then()
           .statusCode(200)
           .log().body();
    }
}
