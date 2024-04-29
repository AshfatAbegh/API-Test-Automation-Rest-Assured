package com.agave.tests.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

    public static void truncateTables(String[] tables) {
        Statement s = DB.getConnection();
        try {
            s.addBatch("SET FOREIGN_KEY_CHECKS = 0");
            List.of(tables).forEach(t -> {
                try {
                    s.addBatch("TRUNCATE `" + t + "`");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            s.addBatch("SET FOREIGN_KEY_CHECKS = 1");
            s.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

