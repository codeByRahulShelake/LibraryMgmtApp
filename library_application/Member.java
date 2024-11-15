package library_application;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

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

   // return book
    public static void returnBook(int copyId, int memberId) {
        PreparedStatement returnStatement = null;
        PreparedStatement historyStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);  // Start transaction

            //Update the availability status of the copy
            String returnSql = "UPDATE copies SET availability_status = 'available' WHERE copy_id = ?";
            returnStatement = connection.prepareStatement(returnSql);
            returnStatement.setInt(1, copyId);
            int updateCount = returnStatement.executeUpdate();

            if (updateCount == 0) {
                System.out.println("No copy found with Copy ID: " + copyId);
                return;
            }

            //Get the expected return date from loanhistory
            String fetchLoanHistorySql = "SELECT expected_return_date FROM loanhistory WHERE copy_id = ? AND member_id = ? AND status = 'borrowed'";
            PreparedStatement fetchStatement = connection.prepareStatement(fetchLoanHistorySql);
            fetchStatement.setInt(1, copyId);
            fetchStatement.setInt(2, memberId);
            resultSet = fetchStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("No loan record found for Copy ID: " + copyId + " and Member ID: " + memberId);
                return;
            }
            Date expectedReturnDate = resultSet.getDate("expected_return_date");

            //Calculate fine if return is late
            LocalDate returnDate = LocalDate.now();
            long daysLate = ChronoUnit.DAYS.between(expectedReturnDate.toLocalDate(), returnDate);
            if (daysLate > 0) {
                int fine = (int) (10 * daysLate);
                System.out.println("Book returned late by " + daysLate + " days. Fine: Rs " + fine);
            }

            // nsert into loanhistory with updated status and return date
            String insertHistory = "UPDATE loanhistory SET return_date = ?, status = 'returned' WHERE copy_id = ? AND member_id = ? AND status = 'borrowed'";
            historyStatement = connection.prepareStatement(insertHistory);
            historyStatement.setDate(1, Date.valueOf(returnDate));
            historyStatement.setInt(2, copyId);
            historyStatement.setInt(3, memberId);

            historyStatement.executeUpdate();

            connection.commit();  // Commit the transaction
            System.out.println("Book successfully returned for Copy ID: " + copyId);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();  // Rollback the transaction in case of an error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (returnStatement != null) returnStatement.close();
                if (historyStatement != null) historyStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // renew book
    public static void renewBook(int copyId, int memberId) {
    
        PreparedStatement fetchStatement = null;
        PreparedStatement renewStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false); // Start transaction

            //Get the current expected return date
            String fetchLoanHistorySql = "SELECT expected_return_date FROM loanhistory WHERE copy_id = ? AND member_id = ? AND status = 'borrowed'";
            fetchStatement = connection.prepareStatement(fetchLoanHistorySql);
            fetchStatement.setInt(1, copyId);
            fetchStatement.setInt(2, memberId);

            resultSet = fetchStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("No loan record found for Copy ID: " + copyId + " and Member ID: " + memberId);
                return;
            }

            Date expectedReturnDate = resultSet.getDate("expected_return_date");
            LocalDate expectedReturnLocalDate = expectedReturnDate.toLocalDate();
            LocalDate today = LocalDate.now();

            // Check if the book is overdue
            if (today.isAfter(expectedReturnLocalDate)) {
                long daysLate = ChronoUnit.DAYS.between(expectedReturnLocalDate, today);
                int fine = (int) (10 * daysLate); // Rs. 10 fine per day
                System.out.println("Renewal delayed by " + daysLate + " days. Fine: Rs " + fine);
            }

            // Calculate the new expected return date
            LocalDate newExpectedReturnDate = today.plusDays(7);

            // Update the expected return date in loanhistory
            String updateLoanHistorySql = "UPDATE loanhistory SET expected_return_date = ? WHERE copy_id = ? AND member_id = ? AND status = 'borrowed'";
            renewStatement = connection.prepareStatement(updateLoanHistorySql);
            renewStatement.setDate(1, Date.valueOf(newExpectedReturnDate));
            renewStatement.setInt(2, copyId);
            renewStatement.setInt(3, memberId);

            int updateCount = renewStatement.executeUpdate();

            if (updateCount > 0) {
                connection.commit(); // Commit the transaction
                System.out.println("Book successfully renewed for Copy ID: " + copyId + ". New Expected Return Date: " + newExpectedReturnDate);
            } else {
                System.out.println("Failed to renew the book. Please check the loan record.");
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
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (fetchStatement != null) fetchStatement.close();
                if (renewStatement != null) renewStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // update name
    public static void updateName(int memberId, String newName)
    {
    	PreparedStatement updateNameStatement = null;
    	try {
    		connection = DriverManager.getConnection(URL, USER, PASSWORD);
    		
    		//write sql query to update name
    		String updateNameSql = "UPDATE MEMBERS SET name = ? WHERE member_id = ?";
    		updateNameStatement = connection.prepareStatement(updateNameSql);
    		updateNameStatement.setString(1, newName);
    		updateNameStatement.setInt(2, memberId);
    		
    		int updateCount = updateNameStatement.executeUpdate();
    		
    		if(updateCount > 0){
    			System.out.println("Name updated succefully.");
    		}
    		else {
    			System.out.println("No member found to update the name.");
    		}
    		
    	}catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (updateNameStatement != null) updateNameStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // update email
    public static void updateEmail(int memberId, String newEmail)
    {
    	PreparedStatement updateEmailStatement = null;
    	try {
    		connection = DriverManager.getConnection(URL, USER, PASSWORD);
    		
    		//write sql query to update email
    		String updateEmailSql = "UPDATE MEMBERS SET email = ? WHERE member_id = ?";
    		updateEmailStatement = connection.prepareStatement(updateEmailSql);
    		updateEmailStatement.setString(1, newEmail);
    		updateEmailStatement.setInt(2, memberId);
    		
    		int updateCount = updateEmailStatement.executeUpdate();
    		
    		if(updateCount > 0){
    			System.out.println("Email updated succefully.");
    		}
    		else {
    			System.out.println("No member found to update the email.");
    		}
    		
    	}catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (updateEmailStatement != null) updateEmailStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // update phone
    public static void updatePhone(int memberId, String newPhone)
    {
    	PreparedStatement updatePhoneStatement = null;
    	try {
    		connection = DriverManager.getConnection(URL, USER, PASSWORD);
    		
    		//write sql query to update phone
    		String updatePhoneSql = "UPDATE MEMBERS SET phone = ? WHERE member_id = ?";
    		updatePhoneStatement = connection.prepareStatement(updatePhoneSql);
    		updatePhoneStatement.setString(1, newPhone);
    		updatePhoneStatement.setInt(2, memberId);
    		
    		int updateCount = updatePhoneStatement.executeUpdate();
    		
    		if(updateCount > 0){
    			System.out.println("Phone no updated succefully.");
    		}
    		else {
    			System.out.println("No member found to update the phone no.");
    		}
    		
    	}catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (updatePhoneStatement != null) updatePhoneStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    //update address
    public static void updateAddress(int memberId, String newAddress) {
        PreparedStatement updateAddressStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL query to update address
            String updateAddressSql = "UPDATE MEMBERS SET address = ? WHERE member_id = ?";
            updateAddressStatement = connection.prepareStatement(updateAddressSql);
            updateAddressStatement.setString(1, newAddress);
            updateAddressStatement.setInt(2, memberId);

            int updateCount = updateAddressStatement.executeUpdate();

            if (updateCount > 0) {
                System.out.println("Address updated successfully.");
            } else {
                System.out.println("No member found to update the address.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (updateAddressStatement != null) updateAddressStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //change password
    public static void changePassword(int memberId, String newPassword) {
        PreparedStatement changePasswordStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL query to update password
            String changePasswordSql = "UPDATE MEMBERS SET password = ? WHERE member_id = ?";
            changePasswordStatement = connection.prepareStatement(changePasswordSql);
            changePasswordStatement.setString(1, newPassword);
            changePasswordStatement.setInt(2, memberId);

            int updateCount = changePasswordStatement.executeUpdate();

            if (updateCount > 0) {
                System.out.println("Password changed successfully.");
            } else {
                System.out.println("No member found to change the password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (changePasswordStatement != null) changePasswordStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
}
