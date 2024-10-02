package kbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conn {
    public Connection connect() {
        Connection conn = null;
        try {
            // DB connection details (make sure they're correct)
            String url = "jdbc:mysql://localhost:3306/kbc_quiz";
            String user = "root";  // replace with your MySQL username
            String password = "Vedant7605@!";  // replace with your MySQL password

            // Establish connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to MySQL has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Main method for testing
    public static void main(String[] args) {
        conn dbConn = new conn();
        dbConn.connect();
    }
}
