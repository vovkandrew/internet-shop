package internetshop.util.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "Andrewreader007");
        String url = "jdbc:mysql://localhost:3307/internetshop?serverTimezone=UTC";
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to DB", e);
        }
    }
}
