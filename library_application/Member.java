package library_application;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Member {
	// JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/rahul";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // JDBC variables for opening, closing, and managing the connection
    private static Connection connection;
    static Scanner sc = new Scanner(System.in);
    
 // List all available books
    public static void listAvailableBooks() {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare the SQL query to find books that have at least one available copy
            String searchSQL = "SELECT b.book_id, b.title, b.author, b.genre " +
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
            System.out.printf("%-10s %-30s %-20s %-15s%n", "Book ID", "Title", "Author", "Genre");
            System.out.println("-----------------------------------------------------------");

            int recordCount = 0;
            // Display each matching book in a table format
            while (resultSet.next()) {
            	recordCount++;
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

                // Print book details
                System.out.printf("%-10d %-30s %-20s %-15s%n", bookId, title, author, genre);
             // Check if 10 records have been displayed and there are more records left
                if (recordCount == 10 && !resultSet.isLast()) {
                	System.out.println();
                    	System.out.println("Enter 'next' If you want to see next set of books or enter anything to exit");
                    	String choice = sc.nextLine();
                    	if(choice.equalsIgnoreCase("next"))
                    	{
                    		recordCount = 0;
                    	}
                    	else
                    	{
                    		break;
                    	}
                    }
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

    // Search book by title
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
            System.out.printf("%-10s %-30s %-20s %-15s%n", "Book ID", "Title", "Author", "Genre");
            System.out.println("-----------------------------------------------------------");

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

                // Print book details 
                System.out.printf("%-10d %-30s %-20s %-15s%n", bookId, title, author, genre);
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

    // Search book by author
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
            System.out.printf("%-10s %-30s %-20s %-15s%n", "Book ID", "Title", "Author", "Genre");
            System.out.println("-----------------------------------------------------------");

            int recordCount = 0; // to display books in set
            // Display each matching book in a table format
            while (resultSet.next()) {
            	recordCount++;
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

                // Print book details
                System.out.printf("%-10d %-30s %-20s %-15s%n", bookId, title, author, genre);
             // Check if 10 records have been displayed and there are more records left
                if (recordCount == 10 && !resultSet.isLast()) {
                	System.out.println();
                    	System.out.println("Enter 'next' If you want to see next set of books or enter anything to exit");
                    	String choice = sc.nextLine();
                    	if(choice.equalsIgnoreCase("next"))
                    	{
                    		recordCount = 0;
                    	}
                    	else
                    	{
                    		break;
                    	}
                    }
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

    // Search book by genre
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
            System.out.printf("%-10s %-30s %-20s %-15s%n", "Book ID", "Title", "Author", "Genre");
            System.out.println("-----------------------------------------------------------");

            int recordCount = 0; // to display books in set
            // Display each matching book in a table format
            while (resultSet.next()) {
            	recordCount++;
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

                // Print book details
                System.out.printf("%-10d %-30s %-20s %-15s%n", bookId, title, author, genre);
                
             // Check if 10 records have been displayed and there are more records left
                if (recordCount == 10 && !resultSet.isLast()) {
                	System.out.println();
                    	System.out.println("Enter 'next' If you want to see next set of books or enter anything to exit");
                    	String choice = sc.nextLine();
                    	if(choice.equalsIgnoreCase("next"))
                    	{
                    		recordCount = 0;
                    	}
                    	else
                    	{
                    		break;
                    	}
                    }
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
    
    //borrow book
    public static void borrowBook(int bookId, int memberId) {
        try {
            // Establish the connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            connection.setAutoCommit(false);

            // Step 1: Check if the book is available 
            String checkAvailabilitySQL = "SELECT c.copy_id FROM copies c " +
                                          "JOIN books b ON b.book_id = c.book_id " +
                                          "WHERE b.book_id = ? AND c.availability_status = 'available' LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(checkAvailabilitySQL);
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            // Step 2: If no available copy is found, exit
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No available copies of the book are available for borrowing.");
                return;
            }

            // Step 3: Get the copy_id from the result set
            resultSet.next();
            int copyId = resultSet.getInt("copy_id");

            // Step 4: Update the status of the copy to 'borrowed'
            String updateCopyStatusSQL = "UPDATE copies SET availability_status = 'borrowed' WHERE copy_id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateCopyStatusSQL);
            updateStatement.setInt(1, copyId);
            updateStatement.executeUpdate();

            // Step 5: Insert the loan history
            LocalDate borrowDate = LocalDate.now();
            LocalDate expectedReturnDate = borrowDate.plusDays(7);

            String insertLoanHistorySQL = "INSERT INTO loanhistory (copy_id, member_id, borrow_date, expected_return_date, status) " +
                                          "VALUES (?, ?, ?, ?, 'borrowed')";
            PreparedStatement insertStatement = connection.prepareStatement(insertLoanHistorySQL);
            insertStatement.setInt(1, copyId);
            insertStatement.setInt(2, memberId);
            insertStatement.setDate(3, Date.valueOf(borrowDate));
            insertStatement.setDate(4, Date.valueOf(expectedReturnDate));
            insertStatement.executeUpdate();

            System.out.println("Book borrowed successfully. Copy Id : "+copyId + "Expected return date: " + expectedReturnDate);
            connection.commit();
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
