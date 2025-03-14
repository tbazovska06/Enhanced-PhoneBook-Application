import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "My$ql_$erveR@2024";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
