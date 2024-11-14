package library_application;

import java.sql.*;

public class Book 
{  

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/rahul";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // JDBC variables for opening, closing, and managing the connection
    private static Connection connection;

    
    // method to add new book in database
    public static void addBookData(String title, String author, String genre, int totalCopies) 
    {
        try 
        {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Start a transaction to ensure both insertions happen together
            connection.setAutoCommit(false);

            // 1. Insert data into the Books table
            String insertBookSQL = "INSERT INTO Books (title, author, genre, total_copies) VALUES (?, ?, ?, ?)";
            PreparedStatement bookStatement = connection.prepareStatement(insertBookSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            bookStatement.setString(1, title);
            bookStatement.setString(2, author);
            bookStatement.setString(3, genre);
            bookStatement.setInt(4, totalCopies);

            int rowsAffected = bookStatement.executeUpdate();

            if (rowsAffected > 0) 
            {
                // Get the generated book_id for the newly inserted book
                var generatedKeys = bookStatement.getGeneratedKeys();
                if (generatedKeys.next()) 
                {
                    int bookId = generatedKeys.getInt(1);

                    // 2. Insert data into the Copies table (multiple copies of the same book)
                    String insertCopySQL = "INSERT INTO Copies (book_id, availability_status) VALUES (?, ?)";
                    PreparedStatement copyStatement = connection.prepareStatement(insertCopySQL);

                    // Insert multiple copies
                    for (int i = 0; i < totalCopies; i++) 
                    {
                        copyStatement.setInt(1, bookId); // Set the book_id of the newly added book
                        copyStatement.setString(2, "available"); // Set the initial availability status
                        copyStatement.addBatch();
                    }

                    // Execute the batch insert for copies
                    copyStatement.executeBatch();
                    connection.commit(); // Commit the transaction

                    System.out.println("Book and copies added successfully!");
                }
            } 
            else 
            {
                System.out.println("Failed to add the book.");
                connection.rollback(); // Rollback the transaction if the book insert fails
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            try 
            {
                if (connection != null) 
                {
                    connection.rollback(); // Rollback the transaction in case of an error
                }
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        } 
        finally 
        {
            try 
            {
                if (connection != null) 
                {
                    connection.close(); // Close the connection
                }
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
