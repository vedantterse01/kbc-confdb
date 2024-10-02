package kbc;

import java.sql.*;

public class questions {

    // Establish database connection
    private Connection connect() throws Exception {
        // URL, username, and password for the DB connection
        String url = "jdbc:mysql://localhost:3306/kbc_quiz";
        String username = "root";
        String password = "Vedant7605@!";
        Class.forName("com.mysql.cj.jdbc.Driver"); // Loading the MySQL JDBC driver
        return DriverManager.getConnection(url, username, password); 
    }

    // Method to fetch a question and its options from the database
    public String menu1(int n, int p, int level) throws Exception {
        String query = "SELECT * FROM questions WHERE id = ?"; // SQL query to fetch the question by ID
        String result = "";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            int questionId = p + n; // Calculate question ID
            stmt.setInt(1, questionId); // Set the ID for the query
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Construct the question in the required format
                result += rs.getString("question") + "\n";
                result += "a. " + rs.getString("option1") + "\n";
                result += "b. " + rs.getString("option2") + "\n";
                result += "c. " + rs.getString("option3") + "\n";
                result += "d. " + rs.getString("option4") + "\n";
            }
        }
        return result;
    }

    // Another method to fetch a question and its options (this is identical to menu1, so we'll reuse menu1 logic)
    public String menu(int n, int p, int level) throws Exception {
        return menu1(n, p, level);
    }

    // Method to check the correct option
    public int check(int n, int level) throws Exception {
        String query = "SELECT correct_option FROM questions WHERE id = ?"; // Query to fetch the correct option
        int correctOption = 0;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int questionId = 5 + n; // Calculate the question ID
            stmt.setInt(1, questionId); // Set the ID for the query
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                correctOption = rs.getInt("correct_option"); // Get the correct option number (1-4)
            }
        }
        return correctOption;
    }
}
