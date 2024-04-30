package com.agave.tests.Product;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Utilities.DB;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProductAddTest {

    @BeforeClass
    public void dbSetUp() throws SQLException{
        DB.truncateTables(new String[] {"products","productHashtag"});
        DB.getConnection().executeUpdate("UPDATE `profiles` SET `sellerStatus` = 1, `isFromGoogle` = 0 WHERE `id` = 1");
    }
    
    @Test(priority = 1)
    public void userShouldSuccessfullyAddProductWithProperAuthorization() throws SQLException{
        
        String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJwcm9maWxlSWQiOjEsInN1YiI6ImFzaWZAZ21haWwuY29tIiwiaWF0IjoxNzE0NDg1NDMxLCJleHAiOjE3MTQ1NzE4MzF9.u8zcmArbhRQPj3lZrGdnYcvNajAngjgIW3udOT4vp5M";
        
        File file = new File("assets/New.jpg");
        File file2 = new File("assets/big-o-notation.png");
        
        RestAssured.baseURI="http://agave-api-test:8080/api/";
        
        Response response = given()
        .header("Authorization", "Bearer " + bearerToken)
        .multiPart("name","Asif")
        .multiPart("price",33410)
        .multiPart("supplementaryInformation","abcd")
        .multiPart("bredingMethod","ABC")
        .multiPart("acquisitionRouteDescription","Japan")
        .multiPart("treeSpecies","UmbrellaPlant")
        .multiPart("countryOfOrigin","Taiwan")
        .multiPart("conditionAtTheTimeOfShipment","BareRoot")
        .multiPart("shippingOrigin","RegionOfOrigin")
        .multiPart("shippingCost","CashOnDelivery")
        .multiPart("daysOfDelivery",2)
        .multiPart("purchaserDesignation","Tree Lover")
        .multiPart("status","UNDER_REVIEW")
        .multiPart("hashTags","RecommendedForBeginners, RecommendedAsAGift")
        .multiPart("shippingMethod","FourthClassMail")
        .multiPart("productImages",file)
        .multiPart("productImages",file2)
        .contentType("multipart/form-data")

        .when()
        .post(baseURI + "v1/product/add");

        Assert.assertEquals(response.getStatusCode(), 200);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "Successful");
    }         
}
