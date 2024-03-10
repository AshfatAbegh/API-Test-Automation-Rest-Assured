package Day5;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ParsingXMLResponse {
    
    //@Test
  //   void testXMLResponse(){
       
  //        {
  //       //Approach 1

  //       given()
  //          .pathParam("id",11133)

  //       .when()
  //          .get("http://restapi.adequateshop.com/api/Traveler/{id}")
        
  //       .then()
  //           .statusCode(200)
  //           .header("content-type","application/xml; charset=utf-8")
  //           .body("Travelerinformation.id", equalTo("11133"))
  //           .body("Travelerinformation.email", equalTo("Developer12@gmail.com"))
  //           .log().all();
  //       }

  //   //Approach 2 -> Returning the response into variable
     
  //   Response res = given()
  //          .pathParam("id",11133)

  //    .when()
  //          .get("http://restapi.adequateshop.com/api/Traveler/{id}");
    
  //   Assert.assertEquals(res.getStatusCode(), 200);
  //   Assert.assertEquals(res.getHeader("content-type"), "application/xml; charset=utf-8");
  //   Assert.assertEquals(res.xmlPath().get("Travelerinformation.id").toString(),"11133");
  //   Assert.assertEquals(res.xmlPath().get("Travelerinformation.email"),"Developer12@gmail.com");
  // }

    @Test
    void testXMLResponseBody(){
        Response res = given()

     .when()
            .get("http://restapi.adequateshop.com/api/Traveler");

      //If we want covert the whole response in a string then we will use asString() method
      //If we want covert a single data in a string then we will use toString() method
    
      XmlPath xmlobj = new XmlPath(res.asString());

      //Verify total number of travellers from the response
       
      List<String> travellers = xmlobj.getList("TravelerinformationResponse.travelers.TravelerInformation");
      Assert.assertEquals(travellers.size(), 0);

      //Verify traveller name is present in the response
       List<String> travellerNames= xmlobj.getList("TravelerinformationResponse.travelers.TravelerInformation.name");
       
       boolean status = false;
       for(String travellername:travellerNames){
         if(travellername.equals("Developer")){
            status = true;
            break;
         }
            Assert.assertEquals(status, true);
       }
    
    }
}

