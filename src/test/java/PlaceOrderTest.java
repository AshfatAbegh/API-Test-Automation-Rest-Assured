package com.agave.tests.Product;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agave.tests.Profile.ProfileDocumentUploadTest;
import com.agave.tests.Utilities.DB;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PlaceOrderTest extends ProfileDocumentUploadTest {

    @BeforeClass
    public void dbSetUp() throws SQLException {
        DB.truncateTables(new String[] {"products","productImages","productHashtag","products_browsedUsers","products_likedUsers"});
        insertProduct("Japan", "ABC", "BareRoot", "Taiwan", 7, "RecommendedForBeginners", "Toy", 33410, "Tree Lover", "CashOnDelivery", "FourthClassMail", "RegionOfOrigin", "Pup", "PUBLISHED", "abcd", "UmbrellaPlant", "LessThanFive", new Date().getTime(), new Date().getTime(), 1, 1);
    }

    private void insertProduct(String acquisitionRouteDescription, String bredingMethod, String conditionAtTheTimeOfShipment, String countryOfOrigin, int daysOfDelivery, String hashTags, String name, double price, String purchaserDesignation, String shippingCost, String shippingMethod, String shippingOrigin, String size, String status, String supplementaryInformation, String treeSpecies, String width, long createdAt, long publishedAt, int buyerId_id, int sellerId_id) throws SQLException {
        Timestamp createdAtTimestamp = new Timestamp(createdAt);
        Timestamp publishedAtTimestamp = new Timestamp(publishedAt);
    
        DB.getConnection().executeUpdate("INSERT INTO `products` (`acquisitionRouteDescription`, `bredingMethod`, `conditionAtTheTimeOfShipment`, `countryOfOrigin`, `daysOfDelivery`, `hashTags`, `name`, `price`, `purchaserDesignation`, `shippingCost`, `shippingMethod`, `shippingOrigin`, `size`, `status`, `supplementaryInformation`, `treeSpecies`, `width`, `createdAt`, `publishedAt`, `buyerId_id`, `sellerId_id`) VALUES ('" + acquisitionRouteDescription + "', '" + bredingMethod + "', '" + conditionAtTheTimeOfShipment + "', '" + countryOfOrigin + "', '" + daysOfDelivery + "', '" + hashTags + "', '" + name + "', '" + price + "', '" + purchaserDesignation + "', '" + shippingCost + "', '" + shippingMethod + "', '" + shippingOrigin + "', '" + size + "', '" + status + "', '" + supplementaryInformation + "', '" + treeSpecies + "', '" + width + "', '" + createdAtTimestamp + "', '" + publishedAtTimestamp + "', '" + buyerId_id + "', '" + sellerId_id + "')");
    }
      
    @Test(priority = 1)
    public void userShouldPlaceOrderOnThePublishedProduct() throws SQLException {

        RestAssured.baseURI = "http://agave-api-test:8080/api/";

        HashMap<String, String> hm = new HashMap<>();
        hm.put("id", "1");
        hm.put("shippingPostalCode", "12348445");
        hm.put("shippingPrefecture", "ccc");
        hm.put("shippingCity", "99");
        hm.put("shippingBuildingName", "454 m n");

        Response response = given()
                 .header("Authorization", "Bearer " + getAccessToken())
                 .contentType(ContentType.JSON)
                 .body(hm)

                .when()
                .post(baseURI + "v1/product/order");

        Assert.assertEquals(response.getStatusCode(), 200);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "成功");
    }

    @AfterMethod
    public void dbUpdate() throws SQLException {
        DB.getConnection().executeUpdate("UPDATE `products` SET `status` = 'UNDER_REVIEW' WHERE `id` = 1");
    }
}




