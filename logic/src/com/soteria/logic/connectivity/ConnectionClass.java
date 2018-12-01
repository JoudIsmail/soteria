package com.soteria.logic.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    private static Connection connection;
    private static boolean connected=false;
    public static Connection getConnection() {
        final String USERNAME = "root";
        final String PASSWORD = "admin";
        final String CONN_STRING = "jdbc:mysql://localhost:3306/login";
        try {
            connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            connected=true;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("not connected!");
            connected=false;
        }return  connection;
    }
    public static boolean isConnected() {
        return connected;
    }
}
