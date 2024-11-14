package library_application;

import java.sql.*;

public class Book 
{  // Changed class name from LibraryDatabase to Book

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/rahul";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // JDBC variables for opening, closing, and managing the connection
    private static Connection connection;

    
    // method to add new book in database
    public static void addBookData(String title, String author, String genre, int totalCopies) {
        Connection connection = null;
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Start a transaction to ensure both insertions happen together
            connection.setAutoCommit(false);

            // 1. Check if the book already exists in the Books table
            String checkBookSQL = "SELECT book_id, total_copies FROM Books WHERE title = ? AND author = ?";
            PreparedStatement checkBookStatement = connection.prepareStatement(checkBookSQL);
            checkBookStatement.setString(1, title);
            checkBookStatement.setString(2, author);

            ResultSet resultSet = checkBookStatement.executeQuery();

            if (resultSet.next()) {
                // Book already exists, update the total_copies in the Books table
                int existingCopies = resultSet.getInt("total_copies");
                int newTotalCopies = existingCopies + totalCopies;

                String updateBookSQL = "UPDATE Books SET total_copies = ? WHERE book_id = ?";
                PreparedStatement updateBookStatement = connection.prepareStatement(updateBookSQL);
                updateBookStatement.setInt(1, newTotalCopies);
                updateBookStatement.setInt(2, resultSet.getInt("book_id"));

                int rowsAffected = updateBookStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // 2. Insert additional copies in the Copies table
                    String insertCopySQL = "INSERT INTO Copies (book_id, availability_status) VALUES (?, ?)";
                    PreparedStatement copyStatement = connection.prepareStatement(insertCopySQL);

                    // Insert the new copies
                    for (int i = 0; i < totalCopies; i++) {
                        copyStatement.setInt(1, resultSet.getInt("book_id")); // Use the existing book_id
                        copyStatement.setString(2, "available"); // Set the initial availability status
                        copyStatement.addBatch();
                    }

                    // Execute the batch insert for copies
                    copyStatement.executeBatch();
                    connection.commit(); // Commit the transaction

                    System.out.println("Copies added successfully! Total copies: " + newTotalCopies);
                } else {
                    System.out.println("Failed to update the book.");
                    connection.rollback(); // Rollback if update fails
                }
            } else {
                // Book does not exist, insert a new book into the Books table
                String insertBookSQL = "INSERT INTO Books (title, author, genre, total_copies) VALUES (?, ?, ?, ?)";
                PreparedStatement bookStatement = connection.prepareStatement(insertBookSQL, PreparedStatement.RETURN_GENERATED_KEYS);
                bookStatement.setString(1, title);
                bookStatement.setString(2, author);
                bookStatement.setString(3, genre);
                bookStatement.setInt(4, totalCopies);

                int rowsAffected = bookStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Get the generated book_id for the newly inserted book
                    ResultSet generatedKeys = bookStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int bookId = generatedKeys.getInt(1);

                        // 2. Insert data into the Copies table for the new book
                        String insertCopySQL = "INSERT INTO Copies (book_id, availability_status) VALUES (?, ?)";
                        PreparedStatement copyStatement = connection.prepareStatement(insertCopySQL);

                        // Insert multiple copies
                        for (int i = 0; i < totalCopies; i++) {
                            copyStatement.setInt(1, bookId); // Set the book_id of the newly added book
                            copyStatement.setString(2, "available"); // Set the initial availability status
                            copyStatement.addBatch();
                        }

                        // Execute the batch insert for copies
                        copyStatement.executeBatch();
                        connection.commit(); // Commit the transaction

                        System.out.println("Book and copies added successfully!");
                    }
                } else {
                    System.out.println("Failed to add the book.");
                    connection.rollback(); // Rollback the transaction if book insert fails
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback the transaction in case of an error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    public void deleteBook(int book_id, String title) {
        Connection connection = null;
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Start a transaction to ensure both delete operations happen together
            connection.setAutoCommit(false);

            // 1. Delete data from the Copies table
            String deleteCopySQL = "DELETE FROM Copies WHERE book_id = ?";
            PreparedStatement copyStatement = connection.prepareStatement(deleteCopySQL);
            copyStatement.setInt(1, book_id);

            int rowsAffected = copyStatement.executeUpdate();

            if (rowsAffected >= 0) {
                // 2. Delete data from the Books table
                String deleteBookSQL = "DELETE FROM Books WHERE book_id = ? AND title = ?";
                PreparedStatement bookStatement = connection.prepareStatement(deleteBookSQL);
                bookStatement.setInt(1, book_id);
                bookStatement.setString(2, title);

                rowsAffected = bookStatement.executeUpdate();

                if (rowsAffected > 0) {
                    connection.commit(); // Commit the transaction
                    System.out.println("Book and its copies deleted successfully!");
                } else {
                    System.out.println("Failed to delete the book.");
                    connection.rollback(); // Rollback if the book delete fails
                }
            } else {
                System.out.println("Failed to delete copies.");
                connection.rollback(); // Rollback if the copies delete fails
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback the transaction in case of an error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
