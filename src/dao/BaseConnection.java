package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/agenda";
    private static final String USER = "shakalinux";
    private static final String PASSWORD = "221025";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
