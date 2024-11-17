package library_application;

import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Member 
{
	private boolean valid = false;
	private AuthenticatedActions authenticatedActions;
	
	// JDBC URL, user name, and password of MySQL server
	private static final String URL = "jdbc:mysql://localhost:3306/rahul";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	
	// JDBC variables for opening, closing, and managing the connection
	private static Connection connection;
	
	static Scanner sc = new Scanner(System.in);
	
	//login to get the authenticated actions
	public void login(int memberId, String memberPassword) 
	{
	   PreparedStatement statement = null;
	   ResultSet resultSet = null;
	
	   try {
	       connection = DriverManager.getConnection(URL, USER, PASSWORD);
	
	       // SQL query to check if the admin_id and password match
	       String checkLoginSQL = "SELECT * FROM members WHERE member_id = ? AND password = ? AND status = 'active'";
	
	       statement = connection.prepareStatement(checkLoginSQL);
	       statement.setInt(1, memberId);  // Set the admin_id in the query
	       statement.setString(2, memberPassword);  // Set the password in the query
	
	       resultSet = statement.executeQuery();
	
	       // Check if a matching admin record is found
	       if (resultSet.next()) {
	           System.out.println("Login successful.");
	           valid = true;
	           authenticatedActions = new AuthenticatedActions(); // Enable access to restricted methods
	
	       } else {
	           System.out.println("Invalid admin ID or password.");
	       }
	
	   } catch (SQLException e) {
	       e.printStackTrace();
	   } finally {
	       try {
	           if (resultSet != null) {
	               resultSet.close();
	           }
	           if (statement != null) {
	               statement.close();
	           }
	           if (connection != null) {
	               connection.close();
	           }
	       } catch (SQLException e) {
	           e.printStackTrace();
	       }
	   }
	}
	
	// Method to get AuthenticatedActions instance if login was successful
	public AuthenticatedActions getAuthenticatedActions() {
	   if (valid)
	       return authenticatedActions;
	   else 
	       return null;
	   
	}
	
	public void logout() {
	   authenticatedActions = null;
	}
	// Add membership
	   public void addMembership(String name, String email, String phone, String address, String password, int balance) {
		    PreparedStatement statement = null;

		    try {
		        connection = DriverManager.getConnection(URL, USER, PASSWORD);

		        // Check if email already exists
		        if (new AuthenticatedActions().isEmailExists(email)) {
		            System.out.println("Error: Same email already exists.");
		            return;
		        }

		        // Check if phone already exists
		        if (new AuthenticatedActions().isPhoneExists(phone)) {
		            System.out.println("Error: Same phone number already exists.");
		            return;
		        }

		        // SQL query to insert a new member
		        String insertMemberSQL = "INSERT INTO members (name, email, phone, address, password, balance) VALUES (?, ?, ?, ?, ?, ?)";
		        statement = connection.prepareStatement(insertMemberSQL, Statement.RETURN_GENERATED_KEYS);
		        statement.setString(1, name);
		        statement.setString(2, email);
		        statement.setString(3, phone);
		        statement.setString(4, address);
		        statement.setString(5, password);
		        statement.setInt(6, balance);

		        int rowsAffected = statement.executeUpdate();

		        // If insertion is successful, retrieve and display member ID
		        if (rowsAffected > 0) {
		            ResultSet generatedKeys = statement.getGeneratedKeys();
		            if (generatedKeys.next()) {
		                int memberId = generatedKeys.getInt(1);
		                System.out.println("Membership successfully created.");
		                new AuthenticatedActions().displayMemberById(memberId); // Display full member details
		            }
		        } else {
		            System.out.println("Failed to add member.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        // Close resources
		        try {
		            if (statement != null) statement.close();
		            if (connection != null) connection.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}
	
	class AuthenticatedActions 
	{
	   private AuthenticatedActions() {} // making constructor private so that it cannot be instantiated
	    
	 

	
	
	    // method to check if email exists
	    private boolean isEmailExists(String email) {
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
	    private boolean isPhoneExists(String phone) {
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
	    
	    
	    
	    // Method to cancel membership
	    public void cancelMembership(int memberId) {
	        PreparedStatement fetchBalanceStatement = null;
	        PreparedStatement deleteMemberStatement = null;
	        ResultSet resultSet = null;

	        try {
	            // Establish the connection to the database
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            connection.setAutoCommit(false); // Begin transaction

	            // Fetch the member's balance before deleting the member
	            String fetchBalanceSQL = "SELECT balance FROM members WHERE member_id = ?";
	            fetchBalanceStatement = connection.prepareStatement(fetchBalanceSQL);
	            fetchBalanceStatement.setInt(1, memberId);  // Set the member_id to the given value

	            resultSet = fetchBalanceStatement.executeQuery();

	            if (resultSet.next()) {
	                int balance = resultSet.getInt("balance");
	                System.out.println("Balance returned of Rs. " + balance);
	            } else {
	                System.out.println("No member found with ID " + memberId);
	                return; // Exit if the member doesn't exist
	            }

	            // SQL query to delete the member by member_id
	            String deleteSQL = "UPDATE members SET status = 'deactiveted' WHERE member_id = ?";
	            deleteMemberStatement = connection.prepareStatement(deleteSQL);
	            deleteMemberStatement.setInt(1, memberId);  // Set the member_id to the given value

	            // Execute the delete operation
	            int rowsAffected = deleteMemberStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Membership cancelled for Member with ID " + memberId);
	            } else {
	                System.out.println("No member found with ID " + memberId);
	            }

	            connection.commit(); // Commit the transaction

	        } catch (SQLException e) {
	            e.printStackTrace();
	            try {
	                if (connection != null) {
	                    connection.rollback(); // Rollback in case of an error
	                }
	            } catch (SQLException rollbackException) {
	                rollbackException.printStackTrace();
	            }
	        } finally {
	            // Close resources
	            try {
	                if (resultSet != null) resultSet.close();
	                if (fetchBalanceStatement != null) fetchBalanceStatement.close();
	                if (deleteMemberStatement != null) deleteMemberStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException closeException) {
	                closeException.printStackTrace();
	            }
	        }
	    }

	    
	 // List all available books
	    public void listAvailableBooks() {
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
	                    title = title.substring(0, 27) + "...";  // Limit to 30 characters
	                }
	                String author = resultSet.getString("author");
	                if (author.length() > 20) {
	                    author = author.substring(0, 17) + "...";  // Limit to 20 characters
	                }
	                String genre = resultSet.getString("genre");
	                if (genre.length() > 15) {
	                    genre = genre.substring(0, 12) + "...";  // Limit to 15 characters
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
	    public void searchBookByTitle(String searchTitle) {
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
	                    title = title.substring(0, 27) + "...";  // Limit to 30 characters
	                }
	                String author = resultSet.getString("author");
	                if (author.length() > 20) {
	                    author = author.substring(0, 17) + "...";  // Limit to 20 characters
	                }
	                String genre = resultSet.getString("genre");
	                if (genre.length() > 15) {
	                    genre = genre.substring(0, 12) + "...";  // Limit to 15 characters
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
	    public void searchBookByAuthor(String searchAuthor) {
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
	                    title = title.substring(0, 27) + "...";  // Limit to 30 characters
	                }
	                String author = resultSet.getString("author");
	                if (author.length() > 20) {
	                    author = author.substring(0, 17) + "...";  // Limit to 20 characters
	                }
	                String genre = resultSet.getString("genre");
	                if (genre.length() > 15) {
	                    genre = genre.substring(0, 12) + "...";  // Limit to 15 characters
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
	    public void searchBookByGenre(String searchGenre) {
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
	                    title = title.substring(0, 27) + "..."; // Limit to 30 characters
	                }
	                String author = resultSet.getString("author");
	                if (author.length() > 20) {
	                    author = author.substring(0, 17) + "..."; // Limit to 20 characters
	                }
	                String genre = resultSet.getString("genre");
	                if (genre.length() > 15) {
	                    genre = genre.substring(0, 12) + "..."; // Limit to 15 characters
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
	    public void borrowBook(int bookId, int memberId) {
	        PreparedStatement fetchStatement = null;
	        PreparedStatement borrowCountStatement = null;
	        PreparedStatement availabilityStatement = null;
	        PreparedStatement updateStatement = null;
	        PreparedStatement insertStatement = null;
	        ResultSet resultSet = null;

	        try {
	            // Connection to the database
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            connection.setAutoCommit(false); // Begin transaction

	            // Fetch member balance
	            String fetchBalanceSql = "SELECT balance FROM members WHERE member_id = ?";
	            fetchStatement = connection.prepareStatement(fetchBalanceSql);
	            fetchStatement.setInt(1, memberId);
	            resultSet = fetchStatement.executeQuery();

	            // Check if member exists and fetch balance
	            if (resultSet.next()) {
	                int balance = resultSet.getInt("balance");

	                if (balance < 200) {
	                    System.out.println("Sorry, you cannot borrow a book due to low balance caused by multiple fines. Please add balance first.");
	                    return; // Exit the method if balance is insufficient
	                }
	            } else {
	                System.out.println("Member ID not found.");
	                return; // Exit the method if member doesn't exist
	            }

	            // Check if the member has more than 4 active borrows
	            String countActiveBorrowsSQL = "SELECT COUNT(*) AS active_borrow_count " +
	                                           "FROM loanhistory " +
	                                           "WHERE member_id = ? AND status = 'borrowed'";
	            borrowCountStatement = connection.prepareStatement(countActiveBorrowsSQL);
	            borrowCountStatement.setInt(1, memberId);
	            resultSet = borrowCountStatement.executeQuery();

	            if (resultSet.next()) {
	                int activeBorrowCount = resultSet.getInt("active_borrow_count");
	                if (activeBorrowCount >= 4) {
	                    System.out.println("Sorry, you cannot borrow more than 4 books at a time.");
	                    return; // Exit the method if active borrow limit is exceeded
	                }
	            }
	            // check if book id is present
	            String checkBookId = "SELECT book_id FROM books WHERE book_id = ?";
	            availabilityStatement = connection.prepareStatement(checkBookId);
	            availabilityStatement.setInt(1, bookId);
	            resultSet = availabilityStatement.executeQuery();

	            if (!resultSet.next()) { // No available book
	                System.out.println("No book available with Book id : "+bookId);
	                return;
	            }
	            
	            // Check if the book is available
	            String checkAvailabilitySQL = "SELECT c.copy_id FROM copies c " +
	                                          "JOIN books b ON b.book_id = c.book_id " +
	                                          "WHERE b.book_id = ? AND c.availability_status = 'available' LIMIT 1";
	            availabilityStatement = connection.prepareStatement(checkAvailabilitySQL);
	            availabilityStatement.setInt(1, bookId);
	            resultSet = availabilityStatement.executeQuery();

	            if (!resultSet.next()) { // No available copies
	                System.out.println("No available copies of the book are available for borrowing.");
	                return;
	            }

	            int copyId = resultSet.getInt("copy_id");

	            // Update the status of the copy to 'borrowed'
	            String updateCopyStatusSQL = "UPDATE copies SET availability_status = 'borrowed' WHERE copy_id = ?";
	            updateStatement = connection.prepareStatement(updateCopyStatusSQL);
	            updateStatement.setInt(1, copyId);
	            updateStatement.executeUpdate();

	            // Insert loan history
	            LocalDate borrowDate = LocalDate.now();
	            LocalDate expectedReturnDate = borrowDate.plusDays(7);

	            String insertLoanHistorySQL = "INSERT INTO loanhistory (copy_id, member_id, borrow_date, expected_return_date, status) " +
	                                          "VALUES (?, ?, ?, ?, 'borrowed')";
	            insertStatement = connection.prepareStatement(insertLoanHistorySQL);
	            insertStatement.setInt(1, copyId);
	            insertStatement.setInt(2, memberId);
	            insertStatement.setDate(3, Date.valueOf(borrowDate));
	            insertStatement.setDate(4, Date.valueOf(expectedReturnDate));
	            insertStatement.executeUpdate();

	            System.out.println("Book borrowed successfully. Copy ID: " + copyId + " | Expected return date: " + expectedReturnDate);
	            connection.commit(); // Commit the transaction
	        } catch (SQLException e) {
	            e.printStackTrace();
	            try {
	                if (connection != null) {
	                    connection.rollback(); // Rollback the transaction in case of an error
	                }
	            } catch (SQLException rollbackException) {
	                rollbackException.printStackTrace();
	            }
	        } finally {
	            // Close resources
	            try {
	                if (resultSet != null) resultSet.close();
	                if (fetchStatement != null) fetchStatement.close();
	                if (borrowCountStatement != null) borrowCountStatement.close();
	                if (availabilityStatement != null) availabilityStatement.close();
	                if (updateStatement != null) updateStatement.close();
	                if (insertStatement != null) insertStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException closeException) {
	                closeException.printStackTrace();
	            }
	        }
	    }

	
	
	   // return book
	    public void returnBook(int copyId, int memberId) {
	        PreparedStatement returnStatement = null;
	        PreparedStatement fetchLoanHistoryStatement = null;
	        PreparedStatement historyStatement = null;
	        PreparedStatement balanceStatement = null;
	        ResultSet resultSet = null;
	
	        try {
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            connection.setAutoCommit(false); // Start transaction
	
	            // Update the availability status of the copy
	            String returnSql = "UPDATE copies SET availability_status = 'available' WHERE copy_id = ?";
	            returnStatement = connection.prepareStatement(returnSql);
	            returnStatement.setInt(1, copyId);
	            int updateCount = returnStatement.executeUpdate();
	
	            if (updateCount == 0) {
	                System.out.println("No copy found with Copy ID: " + copyId);
	                return;
	            }
	
	            // Fetch the expected return date from loanhistory
	            String fetchLoanHistorySql = "SELECT expected_return_date FROM loanhistory WHERE copy_id = ? AND member_id = ? AND status = 'borrowed'";
	            fetchLoanHistoryStatement = connection.prepareStatement(fetchLoanHistorySql);
	            fetchLoanHistoryStatement.setInt(1, copyId);
	            fetchLoanHistoryStatement.setInt(2, memberId);
	            resultSet = fetchLoanHistoryStatement.executeQuery();
	
	            if (!resultSet.next()) {
	                System.out.println("No borrow record found for Copy ID: " + copyId + " and Member ID: " + memberId);
	                return;
	            }
	            Date expectedReturnDate = resultSet.getDate("expected_return_date");
	
	            // Calculate fine if return is late
	            LocalDate returnDate = LocalDate.now();
	            long daysLate = ChronoUnit.DAYS.between(expectedReturnDate.toLocalDate(), returnDate);
	            if (daysLate > 0) {
	                String fetchBalanceSql = "SELECT balance FROM members WHERE member_id = ?";
	                PreparedStatement fetchBalanceStatement = connection.prepareStatement(fetchBalanceSql);
	                fetchBalanceStatement.setInt(1, memberId);
	                ResultSet balanceResultSet = fetchBalanceStatement.executeQuery();
	
	                if (balanceResultSet.next()) {
	                    int balance = balanceResultSet.getInt("balance");
	                    int fine = (int) (10 * daysLate);
	                    int newBalance = balance - fine;
	
	                    if (newBalance < 0) {
	                        System.out.println("Insufficient balance. Please clear the fine of Rs " + fine + " before returning the book.");
	                        return;
	                    }
	
	                    String updateBalance = "UPDATE members SET balance = ? WHERE member_id = ?";
	                    balanceStatement = connection.prepareStatement(updateBalance);
	                    balanceStatement.setInt(1, newBalance);
	                    balanceStatement.setInt(2, memberId);
	                    balanceStatement.executeUpdate();
	
	                    System.out.println("Book returned late by " + daysLate + " days. Fine: Rs " + fine + "\nNew Balance: " + newBalance);
	                } else {
	                    System.out.println("Member not found with Member ID: " + memberId);
	                    return;
	                }
	            }
	
	            // Update loanhistory with return date and status
	            String updateLoanHistory = "UPDATE loanhistory SET return_date = ?, status = 'returned' WHERE copy_id = ? AND member_id = ? AND status = 'borrowed'";
	            historyStatement = connection.prepareStatement(updateLoanHistory);
	            historyStatement.setDate(1, Date.valueOf(returnDate));
	            historyStatement.setInt(2, copyId);
	            historyStatement.setInt(3, memberId);
	            historyStatement.executeUpdate();
	
	            connection.commit(); // Commit the transaction
	            System.out.println("Book successfully returned for Copy ID: " + copyId);
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	            try {
	                if (connection != null) connection.rollback(); // Rollback the transaction in case of an error
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (returnStatement != null) returnStatement.close();
	                if (fetchLoanHistoryStatement != null) fetchLoanHistoryStatement.close();
	                if (historyStatement != null) historyStatement.close();
	                if (balanceStatement != null) balanceStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
	    
	    // renew book
	    public void renewBook(int copyId, int memberId) {
	    
	        PreparedStatement fetchStatement = null;
	        PreparedStatement renewStatement = null;
	        PreparedStatement balanceStatement = null;
	        ResultSet resultSet = null;
	
	        try {
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            connection.setAutoCommit(false); // Start transaction
	
	            //Get the current expected return date
	            String fetchLoanHistorySql = "SELECT borrow_date, expected_return_date FROM loanhistory WHERE copy_id = ? AND member_id = ? AND status = 'borrowed'";

	            fetchStatement = connection.prepareStatement(fetchLoanHistorySql);
	            fetchStatement.setInt(1, copyId);
	            fetchStatement.setInt(2, memberId);
	
	            resultSet = fetchStatement.executeQuery();
	
	            if (!resultSet.next()) {
	                System.out.println("No borrow record found for Copy ID: " + copyId + " and Member ID: " + memberId);
	                return;
	            }
	
	            Date expectedReturnDate = resultSet.getDate("expected_return_date");
	            LocalDate expectedReturnLocalDate = expectedReturnDate.toLocalDate();
	            LocalDate today = LocalDate.now();
	            long daysLate = ChronoUnit.DAYS.between(expectedReturnLocalDate, today);
	            
	            if(daysLate > 0) {
	            	String fetchBalanceSql = "SELECT balance FROM members WHERE member_id = ?";
	                PreparedStatement fetchBalanceStatement = connection.prepareStatement(fetchBalanceSql);
	                fetchBalanceStatement.setInt(1, memberId);
	                ResultSet balanceResultSet = fetchBalanceStatement.executeQuery();
	
	                if (balanceResultSet.next()) {
	                    int balance = balanceResultSet.getInt("balance");
	                    int fine = (int) (10 * daysLate);
	                    int newBalance = balance - fine;
	
	                    if (newBalance < 0) {
	                        System.out.println("Insufficient balance. Please clear the fine of Rs " + fine + " before renewing the book.");
	                        return;
	                    }
	
	                    String updateBalance = "UPDATE members SET balance = ? WHERE member_id = ?";
	                    balanceStatement = connection.prepareStatement(updateBalance);
	                    balanceStatement.setInt(1, newBalance);
	                    balanceStatement.setInt(2, memberId);
	                    balanceStatement.executeUpdate();
	
	                    System.out.println("Book renewed late by " + daysLate + " days. Fine: Rs " + fine + "\nNew Balance: " + newBalance);
	                } else {
	                    System.out.println("Member not found with Member ID: " + memberId);
	                    return;
	                }
	            }
	
	            // Calculate the new expected return date
	            LocalDate borrowDate = resultSet.getDate("borrow_date").toLocalDate();
	            LocalDate newExpectedReturnDate = today.plusDays(7); // New expected return date after renewal

	            // Calculate the difference in days between the new expected return date and the borrow date
	            long renewalDay = ChronoUnit.DAYS.between(borrowDate, newExpectedReturnDate);

	            // Check if the renewal period exceeds 30 days
	            if (renewalDay > 30) {
	                System.out.println("You cannot have a book for more than 30 days. Please return it.");
	                return;
	            }

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
	    
	    // book lost method
	    public static void reportBookLost(int copyId, int memberId) {
	        PreparedStatement updateCopyStatusStatement = null;
	        PreparedStatement fetchBookIdStatement = null;
	        PreparedStatement updateBookStatement = null;
	        PreparedStatement loanHistoryUpdateStatement = null;
	        PreparedStatement fetchMemberBalanceStatement = null;
	        PreparedStatement updateMemberBalanceStatement = null;
	        ResultSet resultSet = null;

	        try {
	            // Establish connection to the database
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            connection.setAutoCommit(false); // Begin transaction

	            // Fetch the book_id associated with the copy
	            String fetchBookIdSQL = "SELECT book_id, availability_status FROM copies WHERE copy_id = ?";
	            fetchBookIdStatement = connection.prepareStatement(fetchBookIdSQL);
	            fetchBookIdStatement.setInt(1, copyId);
	            resultSet = fetchBookIdStatement.executeQuery();

	            if (!resultSet.next()) {
	                System.out.println("No copy found with Copy ID: " + copyId);
	                return; // Exit if the copy does not exist
	            }

	            int bookId = resultSet.getInt("book_id");
	            String availabilityStatus = resultSet.getString("availability_status");

	            // Check if the copy is already marked as lost
	            if ("lost".equalsIgnoreCase(availabilityStatus)) {
	                System.out.println("The copy with Copy ID: " + copyId + " is already marked as lost.");
	                return;
	            }

	            // Update the status of the copy to 'lost'
	            String updateCopyStatusSQL = "UPDATE copies SET availability_status = 'lost' WHERE copy_id = ?";
	            updateCopyStatusStatement = connection.prepareStatement(updateCopyStatusSQL);
	            updateCopyStatusStatement.setInt(1, copyId);
	            updateCopyStatusStatement.executeUpdate();

	            // Update the total copies in the books table
	            String updateBookSQL = "UPDATE books SET total_copies = total_copies - 1 WHERE book_id = ?";
	            updateBookStatement = connection.prepareStatement(updateBookSQL);
	            updateBookStatement.setInt(1, bookId);
	            updateBookStatement.executeUpdate();

	            // Fetch the member's current balance
	            String fetchMemberBalanceSQL = "SELECT balance FROM members WHERE member_id = ?";
	            fetchMemberBalanceStatement = connection.prepareStatement(fetchMemberBalanceSQL);
	            fetchMemberBalanceStatement.setInt(1, memberId);
	            resultSet = fetchMemberBalanceStatement.executeQuery();

	            if (!resultSet.next()) {
	                System.out.println("Member not found.");
	                return;
	            }

	            int currentBalance = resultSet.getInt("balance");
	            int fineAmount = 200;
	            int updatedBalance = currentBalance - fineAmount;

	            if (updatedBalance < 0) {
	                System.out.println("Insufficient balance to deduct the fine. Please add funds.");
	                return;
	            }

	            // Deduct fine from the member's balance
	            String updateMemberBalanceSQL = "UPDATE members SET balance = ? WHERE member_id = ?";
	            updateMemberBalanceStatement = connection.prepareStatement(updateMemberBalanceSQL);
	            updateMemberBalanceStatement.setInt(1, updatedBalance);
	            updateMemberBalanceStatement.setInt(2, memberId);
	            updateMemberBalanceStatement.executeUpdate();

	            System.out.println("Fine of 200 INR collected from Member ID: " + memberId + ". Updated balance: " + updatedBalance);

	            // Update the loan history to reflect the lost status if applicable
	            String updateLoanHistorySQL = 
	                "UPDATE loanhistory SET status = 'lost' " +
	                "WHERE copy_id = ? AND status = 'borrowed'";
	            loanHistoryUpdateStatement = connection.prepareStatement(updateLoanHistorySQL);
	            loanHistoryUpdateStatement.setInt(1, copyId);
	            loanHistoryUpdateStatement.executeUpdate();

	            System.out.println("The copy with Copy ID: " + copyId + " has been marked as lost.");
	            connection.commit(); // Commit the transaction

	        } catch (SQLException e) {
	            e.printStackTrace();
	            try {
	                if (connection != null) {
	                    connection.rollback(); // Rollback the transaction in case of an error
	                }
	            } catch (SQLException rollbackException) {
	                rollbackException.printStackTrace();
	            }
	        } finally {
	            // Close resources
	            try {
	                if (resultSet != null) resultSet.close();
	                if (fetchBookIdStatement != null) fetchBookIdStatement.close();
	                if (updateCopyStatusStatement != null) updateCopyStatusStatement.close();
	                if (updateBookStatement != null) updateBookStatement.close();
	                if (loanHistoryUpdateStatement != null) loanHistoryUpdateStatement.close();
	                if (fetchMemberBalanceStatement != null) fetchMemberBalanceStatement.close();
	                if (updateMemberBalanceStatement != null) updateMemberBalanceStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException closeException) {
	                closeException.printStackTrace();
	            }
	        }
	    }


	    
	 // Method to display member details by member_id
	    public void displayMemberById(int memberId) {
	        try {
	
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	
	            // Query to select a member by their ID
	            String selectMemberSQL = "SELECT * FROM Members WHERE member_id = ?";
	            PreparedStatement statement = connection.prepareStatement(selectMemberSQL);
	            statement.setInt(1, memberId);
	
	            ResultSet resultSet = statement.executeQuery();
	
	            // Display table header
	            System.out.println();
	            System.out.printf("%-10s %-20s %-30s %-15s %-20s %-15s %-15s%n", 
	                              "Member ID", "Name", "Email", "Phone", "Address", "Balance", "Password");
	            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
	
	            // Check if the result set has any records
	            if (!resultSet.isBeforeFirst()) {
	                System.out.println("No member found with ID: " + memberId);
	                return;
	            }
	
	            // Display the member details
	            if (resultSet.next()) {
	                int id = resultSet.getInt("member_id");
	                String name = resultSet.getString("name");
	                if (name.length() > 20) {
	                    name = name.substring(0, 17) + "...";  // Limit to 20 characters
	                }
	                String email = resultSet.getString("email");
	                if (email.length() > 30) {
	                    email = email.substring(0, 27) + "...";  // Limit to 30 characters
	                }
	                String phone = resultSet.getString("phone");
	                String address = resultSet.getString("address");
	                if (address.length() > 20) {
	                    address = address.substring(0, 17) + "...";  // Limit to 20 characters
	                }
	                double balance = resultSet.getDouble("balance");
	                String password = resultSet.getString("password");
	                if (password.length() > 15) {
	                    password = password.substring(0, 12) + "...";  // Limit to 15 characters
	                }
	
	                // Display member details in formatted table
	                System.out.printf("%-10d %-20s %-30s %-15s %-20s %-15.2f %-15s%n", 
	                                  id, name, email, phone, address, balance, password);
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
	
	    // update name
	    public void updateName(int memberId, String newName)
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
	    public void updateEmail(int memberId, String newEmail)
	    {
	    	PreparedStatement updateEmailStatement = null;
	    	try {
	    		connection = DriverManager.getConnection(URL, USER, PASSWORD);
	    		
	    		// check if email exists
	    		if (isEmailExists(newEmail)) {
		            System.out.println("Error: Same email already exists.");
		            return;
		        } 
	    		
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
	    public void updatePhone(int memberId, String newPhone)
	    {
	    	PreparedStatement updatePhoneStatement = null;
	    	try {
	    		connection = DriverManager.getConnection(URL, USER, PASSWORD);
	    		
		        // Check if phone already exists
		        if (isPhoneExists(newPhone)) {
		            System.out.println("Error: Same phone number already exists.");
		            return;
		        }
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
	    public void updateAddress(int memberId, String newAddress) {
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
	    public void changePassword(int memberId, String newPassword) {
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
	    
	    // add balance
	    public void addBalance(int memberId, int newBalance)
	    {
	    	ResultSet resultSet =null;
	    	PreparedStatement balanceStatement = null;
	    	PreparedStatement fetchStatement = null;
	    	
	    	try {
	    		connection = DriverManager.getConnection(URL, USER, PASSWORD);
	    		
	    		String fetchBalance = "SELECT balance FROM members WHERE member_id = ?";
	    		fetchStatement = connection.prepareStatement(fetchBalance);
	    		fetchStatement.setInt(1, memberId);
	    		resultSet = fetchStatement.executeQuery();
	    		
	    		if(!resultSet.next()) {
	    			System.out.println("No member found with member id : "+memberId);
	    		}
	    		int balance = resultSet.getInt("balance");
	    		
	    		int updatedBalance = balance + newBalance;
	    		if(updatedBalance > 3000) {
	    			System.out.println("Your old Balance is : "+balance +"\nSo you cannot add more balance than "+(3000 - balance));
	    			System.out.println("It's our Policy. Thank you");
	    			return;
	    		}
	    		
	    		String updateBalance ="UPDATE members SET balance = ? WHERE member_id = ?";
	    		balanceStatement = connection.prepareStatement(updateBalance);
	    		balanceStatement.setInt(1, updatedBalance);
	    		balanceStatement.setInt(2, memberId);
	    		
	    		int checkUpdate = balanceStatement.executeUpdate();
	    		if(checkUpdate > 0) {
	    			System.out.println("Balance added successfully. New Balance : "+updatedBalance);
	    		}
	    	}catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Close resources
	            try {
	                if (balanceStatement != null) balanceStatement.close();
	                if (fetchStatement != null) fetchStatement.close();
	                if (resultSet != null) resultSet.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    // view loan history
	    public void viewLoanHistory(int memberId) {
	        PreparedStatement loanHistoryStatement = null;
	        ResultSet resultSet = null;
	
	        try {
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	
	            // query to fetch loan history for the member
	            String loanHistorySql = "SELECT c.copy_id, b.title, l.borrow_date, l.expected_return_date, l.return_date, l.status " +
	                                    "FROM loanhistory l " +
	                                    "JOIN copies c ON l.copy_id = c.copy_id " +
	                                    "JOIN books b ON c.book_id = b.book_id " +
	                                    "WHERE l.member_id = ? " +
	                                    "ORDER BY l.borrow_date DESC";
	            loanHistoryStatement = connection.prepareStatement(loanHistorySql);
	            loanHistoryStatement.setInt(1, memberId);
	
	            resultSet = loanHistoryStatement.executeQuery();
	
	            // Display loan history
	            System.out.printf("%-10s %-30s %-15s %-20s %-15s %-15s\n", 
	                               "Copy ID", "Book Title", "Borrow Date", "Due Date", "Return Date", "Status");
	            System.out.println("-------------------------------------------------------------------------------------------------------------");
	
	            boolean hasHistory = false;
	            int recordCount = 0;
	            while (resultSet.next()) {
	            	recordCount++;
	                hasHistory = true;
	                int copyId = resultSet.getInt("copy_id");
	                String title = resultSet.getString("title");
	                if(title.length() > 30) {
	                	title = title.substring(0, 27) + "...";
	                }
	                Date borrowDate = resultSet.getDate("borrow_date");
	                Date dueDate = resultSet.getDate("expected_return_date");
	                Date returnDate = resultSet.getDate("return_date");
	                String status = resultSet.getString("status");
	
	                System.out.printf("%-10d %-30s %-15s %-20s %-15s %-15s\n", 
	                                   copyId, title, borrowDate, dueDate, 
	                                  (returnDate != null ? returnDate : "Not Returned"), status);
	             // Check if 10 records have been displayed and there are more records left
	                if (recordCount == 10 && !resultSet.isLast()) {
	                		System.out.println();
	                    	System.out.println("Enter 'next' If you want to see next set of history or enter anything to exit");
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
	
	            if (!hasHistory) {
	                System.out.println("No loan history found for Member ID: " + memberId);
	            }
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Close resources
	            try {
	                if (resultSet != null) resultSet.close();
	                if (loanHistoryStatement != null) loanHistoryStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
	}
    
}
