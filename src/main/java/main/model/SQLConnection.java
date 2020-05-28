package main.model;

import java.sql.*;

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
    public static String getColumn(String sql) {
        String str = null;
        try (Statement s = getConnection().createStatement()) {
            ResultSet r = s.executeQuery(sql);
            r.next();
            str = r.getString(1);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return str;
    }
}
