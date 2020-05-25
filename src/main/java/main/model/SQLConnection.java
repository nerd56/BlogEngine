package main.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogdb?serverTimezone=Europe/Moscow&password=testtest&user=root");
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
