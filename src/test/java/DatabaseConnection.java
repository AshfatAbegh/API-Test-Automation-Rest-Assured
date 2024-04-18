package com.agave.tests.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    public static Statement getConnection() {
        String url = "jdbc:mysql://agave-db-test:3306/api";
        String username = "agave_user";
        String password = "agave_pass";
    
        Connection connection = null;
        Statement statement = null;
    
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statement;
    }
}
