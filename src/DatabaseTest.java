import java.sql.Connection;
import java.sql.SQLException;

import dao.DatabaseConnection;

public class DatabaseTest {

    public static void main(String[] args) {

        System.out.println("Testing MySQL connection...");

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection != null && connection.isValid(5)) {
                System.out.println("SUCCESS! Java connected to MySQL!");
                System.out.println("Database: "
                        + connection.getCatalog());
            }

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
