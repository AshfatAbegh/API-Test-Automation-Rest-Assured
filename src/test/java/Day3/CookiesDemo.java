package Day3;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;

public class CookiesDemo {
    
    //@Test(priority = 1)
    void testCookies(){
        
        given()

        .when()
            .get("https://www.google.com/")

        .then()
           .cookie("AEC","Ackid1QrrbgUxg8122v80WBuu9JqXySmlCmHpBkSoJxsPCNMq_OmpeCjag")
           .log().all();
    }
    
    @Test(priority = 2)
    void getCookiesInfo(){
         
        Response res = given()

        .when()
            .get("https://www.google.com/");

            //get single cookie info
            // String Cookie_Value = res.getCookie("AEC");
            // System.out.println("Value of Cookie is: " + Cookie_Value);

            //get all cookies info
            Map<String,String> cookies_values = res.getCookies();
            System.out.println(cookies_values.keySet()); //->Return multiple keys

            //Retrieving all the values using for loop
            for(String k:cookies_values.keySet()){
                String Cookie_Value = res.getCookie(k);
                System.out.println("Cookie Value: " + Cookie_Value);
            }
    }
}
