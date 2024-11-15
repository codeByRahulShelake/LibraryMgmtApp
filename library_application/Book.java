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
    
    // Method to select and display all books from the database
    public static void displayAllBooks() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String selectBooksSQL = "SELECT * FROM Books";
            PreparedStatement statement = connection.prepareStatement(selectBooksSQL);

            ResultSet resultSet = statement.executeQuery();

                // Display table header
            	System.out.println();
                System.out.printf("%-10s %-30s %-20s %-15s %-15s%n", "Book ID", "Title", "Author", "Genre", "Total Copies");
                System.out.println("--------------------------------------------------------------------------------------------");

                // Display each book in table format
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    String title = resultSet.getString("title");
                    if (title.length() > 30) {
                        title = title.substring(0, 30);  // Limit to 30 characters
                    }
                    String author = resultSet.getString("author");
                    if (author.length() > 20) {
                        author = author.substring(0, 20);  // Limit to 20 characters
                    }
                    String genre = resultSet.getString("genre");
                    if (genre.length() > 15) {
                        genre = genre.substring(0, 15);  // Limit to 15 characters
                    }
                    int totalCopies = resultSet.getInt("total_copies");

                    System.out.printf("%-10d %-30s %-20s %-15s %-15d%n", bookId, title, author, genre, totalCopies);
                }
            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void displayCopiesInfo(int book_id) {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String selectBooksSQL = "SELECT copy_id, availability_status FROM copies where book_id = ?";
            PreparedStatement statement = connection.prepareStatement(selectBooksSQL);
            statement.setInt(1, book_id);

            ResultSet resultSet = statement.executeQuery();

            	System.out.println();
                // Display table header
                System.out.printf("%-10s %-20s%n", "Copy ID", "Status");
                System.out.println("-----------------------");

                // Display each book in table format
                while (resultSet.next()) {
                    int copyId = resultSet.getInt("copy_id");
                    String status = resultSet.getString("availability_status");

                    System.out.printf("%-10d %-20s%n", copyId, status);
                }
            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    //search book by title
    public static void searchBookByTitle(String searchTitle) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare the SQL query with a wildcard search
            String searchSQL = "SELECT * FROM books WHERE title LIKE ?";
            PreparedStatement statement = connection.prepareStatement(searchSQL);
            statement.setString(1, "%" + searchTitle + "%");  // Using wildcard for partial match

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Check if any results were returned
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No books found with title containing: " + searchTitle);
                return;
            }

            // Print table headers
            System.out.println();
            System.out.printf("%-10s %-30s %-20s %-15s %-15s%n", "Book ID", "Title", "Author", "Genre", "Total Copies");
            System.out.println("------------------------------------------------------------------------------------------");

            // Display each matching book in a table format
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                if (title.length() > 30) {
                    title = title.substring(0, 30);  // Limit to 30 characters
                }
                String author = resultSet.getString("author");
                if (author.length() > 20) {
                    author = author.substring(0, 20);  // Limit to 20 characters
                }
                String genre = resultSet.getString("genre");
                if (genre.length() > 15) {
                    genre = genre.substring(0, 15);  // Limit to 15 characters
                }
                int totalCopies = resultSet.getInt("total_copies");

                System.out.printf("%-10d %-30s %-20s %-15s %-15d%n", bookId, title, author, genre, totalCopies);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    // search by author
    public static void searchBookByAuthor(String searchAuthor) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare the SQL query with a wildcard search
            String searchSQL = "SELECT * FROM books WHERE author LIKE ?";
            PreparedStatement statement = connection.prepareStatement(searchSQL);
            statement.setString(1, "%" + searchAuthor + "%");  // Using wildcard for partial match

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Check if any results were returned
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No books found with author containing: " + searchAuthor);
                return;
            }

            // Print table headers
            System.out.println();
            System.out.printf("%-10s %-30s %-20s %-15s %-15s%n", "Book ID", "Title", "Author", "Genre", "Total Copies");
            System.out.println("------------------------------------------------------------------------------------------");

            // Display each matching book in a table format
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                if (title.length() > 30) {
                    title = title.substring(0, 30);  // Limit to 30 characters
                }
                String author = resultSet.getString("author");
                if (author.length() > 20) {
                    author = author.substring(0, 20);  // Limit to 20 characters
                }
                String genre = resultSet.getString("genre");
                if (genre.length() > 15) {
                    genre = genre.substring(0, 15);  // Limit to 15 characters
                }
                int totalCopies = resultSet.getInt("total_copies");

                System.out.printf("%-10d %-30s %-20s %-15s %-15d%n", bookId, title, author, genre, totalCopies);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    //search by genre
    public static void searchBookByGenre(String searchGenre) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare the SQL query with a wildcard search for genre
            String searchSQL = "SELECT * FROM books WHERE genre LIKE ?";
            PreparedStatement statement = connection.prepareStatement(searchSQL);
            statement.setString(1, "%" + searchGenre + "%"); // Using wildcard for partial match

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Check if any results were returned
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No books found in genre: " + searchGenre);
                return;
            }

            // Print table headers
            System.out.println();
            System.out.printf("%-10s %-30s %-20s %-15s %-15s%n", "Book ID", "Title", "Author", "Genre", "Total Copies");
            System.out.println("------------------------------------------------------------------------------------------");

            // Display each matching book in a table format
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                if (title.length() > 30) {
                    title = title.substring(0, 30); // Limit to 30 characters
                }
                String author = resultSet.getString("author");
                if (author.length() > 20) {
                    author = author.substring(0, 20); // Limit to 20 characters
                }
                String genre = resultSet.getString("genre");
                if (genre.length() > 15) {
                    genre = genre.substring(0, 15); // Limit to 15 characters
                }
                int totalCopies = resultSet.getInt("total_copies");

                System.out.printf("%-10d %-30s %-20s %-15s %-15d%n", bookId, title, author, genre, totalCopies);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    
    // list all available books
    public static void listAvailableBooks() {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare the SQL query to find books that have at least one available copy
            String searchSQL = "SELECT b.book_id, b.title, b.author, b.genre, b.total_copies " +
                               "FROM books b JOIN copies c ON b.book_id = c.book_id " +
                               "WHERE c.availability_status = 'available' " +
                               "GROUP BY b.book_id " +
                               "HAVING COUNT(c.availability_status) > 0";

            PreparedStatement statement = connection.prepareStatement(searchSQL);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Check if any results were returned
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No available books found.");
                return;
            }

            // Print table headers
            System.out.println();
            System.out.printf("%-10s %-30s %-20s %-15s %-15s%n", "Book ID", "Title", "Author", "Genre", "Total Copies");
            System.out.println("------------------------------------------------------------------------------------------");

            // Display each matching book in a table format
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                if (title.length() > 30) {
                    title = title.substring(0, 30);  // Limit to 30 characters
                }
                String author = resultSet.getString("author");
                if (author.length() > 20) {
                    author = author.substring(0, 20);  // Limit to 20 characters
                }
                String genre = resultSet.getString("genre");
                if (genre.length() > 15) {
                    genre = genre.substring(0, 15);  // Limit to 15 characters
                }
                int totalCopies = resultSet.getInt("total_copies");

                System.out.printf("%-10d %-30s %-20s %-15s %-15d%n", bookId, title, author, genre, totalCopies);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    // add copies to existing book
    public static void addCopy(int bookId, int numberOfCopies) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare SQL for inserting copies
            String insertCopySQL = "INSERT INTO copies (book_id, availability_status) VALUES (?, ?)";
            PreparedStatement copyStatement = connection.prepareStatement(insertCopySQL);

            // Add the specified number of copies
            for (int i = 0; i < numberOfCopies; i++) {
                copyStatement.setInt(1, bookId); // Set the book_id for each copy
                copyStatement.setString(2, "available"); // Set initial availability status to 'available'
                copyStatement.addBatch();
            }

            // Execute the batch insert
            copyStatement.executeBatch();
            System.out.println(numberOfCopies + " copies added successfully for Book ID: " + bookId);

        } catch (SQLException e) {
            e.printStackTrace();
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

    // delete copy by its id
    public static void deleteCopy(int copyId) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare SQL to delete a specific copy
            String deleteCopySQL = "DELETE FROM copies WHERE copy_id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteCopySQL);
            deleteStatement.setInt(1, copyId); // Set the copy_id of the copy to be deleted

            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Copy with Copy ID: " + copyId + " deleted successfully.");
            } else {
                System.out.println("No copy found with Copy ID: " + copyId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

    
    //update copy status
    public static void updateCopyStatus(int copyId, String status) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare the SQL statement to update the copy's status
            String updateStatusSQL = "UPDATE copies SET availability_status = ? WHERE copy_id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateStatusSQL);
            updateStatement.setString(1, status); // Set the new status (e.g., "available", "borrowed")
            updateStatement.setInt(2, copyId);    // Set the specific copy_id to update

            // Execute the update
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Copy with Copy ID: " + copyId + " status updated to: " + status);
            } else {
                System.out.println("No copy found with Copy ID: " + copyId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

    //update book title
    public static void updateBookTitle(int bookId, String newTitle) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL query to update the title
            String updateTitleSQL = "UPDATE books SET title = ? WHERE book_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateTitleSQL);
            statement.setString(1, newTitle);
            statement.setInt(2, bookId);

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Title updated successfully!");
            } else {
                System.out.println("No book found with ID: " + bookId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    //update book author
    public static void updateBookAuthor(int bookId, String newAuthor) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL query to update the author
            String updateAuthorSQL = "UPDATE books SET author = ? WHERE book_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateAuthorSQL);
            statement.setString(1, newAuthor);
            statement.setInt(2, bookId);

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Author updated successfully!");
            } else {
                System.out.println("No book found with ID: " + bookId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    //update book genre
    public static void updateBookGenre(int bookId, String newGenre) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL query to update the genre
            String updateGenreSQL = "UPDATE books SET genre = ? WHERE book_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateGenreSQL);
            statement.setString(1, newGenre);
            statement.setInt(2, bookId);

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Genre updated successfully!");
            } else {
                System.out.println("No book found with ID: " + bookId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    // add member 
    public static void addMember(String name, String email, String phone, String address, String password) {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            if (isEmailExists(email)) {
                System.out.println("Error: Same email already exists.");
                return;
            }
            
            if (isPhoneExists(phone)) {
                System.out.println("Error: Same phone number already exists.");
                return;
            }

            String insertMemberSQL = "INSERT INTO members (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertMemberSQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, address);
            statement.setString(5, password);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int memberId = generatedKeys.getInt(1);
                    System.out.println("Member added successfully! Your Member ID is: " + memberId);
                }
            } else {
                System.out.println("Failed to add member.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // methos to check if email exists
    private static boolean isEmailExists(String email) {
        try {
            String emailCheckSQL = "SELECT * FROM members WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(emailCheckSQL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // method to check if phone exists
    private static boolean isPhoneExists(String phone) {
        try {
            String phoneCheckSQL = "SELECT * FROM members WHERE phone = ?";
            PreparedStatement statement = connection.prepareStatement(phoneCheckSQL);
            statement.setString(1, phone);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    // Method to delete a member by member_id
    public static void deleteMember(int memberId) {
        PreparedStatement statement = null;

        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL query to delete a member by member_id
            String deleteSQL = "DELETE FROM members WHERE member_id = ?";
            statement = connection.prepareStatement(deleteSQL);
            statement.setInt(1, memberId);  // Set the member_id to the given value

            // Execute the delete operation
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Member with ID " + memberId + " deleted successfully.");
            } else {
                System.out.println("No member found with ID " + memberId + ".");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources in the finally block to ensure they are always closed
            try {
                if (statement != null) {
                    statement.close(); // Close the PreparedStatement
                }
                if (connection != null) {
                    connection.close(); // Close the Connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
