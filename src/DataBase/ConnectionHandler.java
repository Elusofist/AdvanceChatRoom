package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class ConnectionHandler {
    private static Connection connection;
    public static String username;
    private static String password;
    public static String url;
    public static String driver;

    static {
        username = "root";
        setPassword("Elif6440");
        url = "jdbc:mysql://localhost:3306/chat";
        driver = "com.mysql.jdbc.Driver";
    }

    public ConnectionHandler() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, getPassword());
            System.out.println("connection started!");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }


    public static String getPassword() {
        return password;
    }

    public static void setPassword(String pass) {
        password = pass;
    }

    public static Connection getConnection() {
        return connection;
    }
}