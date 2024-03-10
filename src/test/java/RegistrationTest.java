package com.paperless.tests.auth;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import static io.restassured.RestAssured.*;


public class RegistrationTest {

    @BeforeClass
    public static void setup() {
        try {
            String url = "jdbc:mysql://paperless-db-test:3306/auth";
            String username = "root";
            String password = "paperless_root";

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            // Reset auto-increment value to 1
            statement.executeUpdate("ALTER TABLE _user AUTO_INCREMENT = 1");

            // Modify column to auto-increment
            statement.executeUpdate("ALTER TABLE _user MODIFY COLUMN id INT AUTO_INCREMENT");

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Test(priority = 6)
     public void userShouldbeRegisteredInDatabase() throws Exception{

    String phoneNumber = "01687945155";

    // Insert data into the _user table
    insertDataIntoUserTable(phoneNumber);

    // Retrieve the phone number from the database
    String url = "jdbc:mysql://paperless-db-test:3306/auth";
    String username = "root";
    String password = "paperless_root";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM _user WHERE phone = '" + phoneNumber + "'");

        // Assert that the user exists in the database
        Assert.assertTrue(resultSet.next());
        String phoneNumberFromDB = resultSet.getString("phone");
        System.out.println(phoneNumberFromDB);
        Assert.assertEquals(phoneNumber, phoneNumberFromDB);

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }
}

private void insertDataIntoUserTable(String phoneNumber) throws SQLException {
    String url = "jdbc:mysql://paperless-db-test:3306/auth";
    String username = "root";
    String password = "paperless_root";

    Connection connection = null;
    Statement statement = null;

    try {
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO _user (phone) VALUES ('" + phoneNumber + "')");
    } finally {
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }

 }

  @AfterClass
     public static void tearDown() {
         try {
             String url = "jdbc:mysql://paperless-db-test:3306/auth";
            String username = "root";
             String password = "paperless_root";

             Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();

             // Delete all data from the _user table
            statement.executeUpdate("DELETE FROM _user");

             statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
         }
     }
}
