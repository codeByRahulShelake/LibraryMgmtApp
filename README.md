# Library Management System

## Overview

The Java Library Management System is a console-based application designed to efficiently manage a library's operations. The system provides functionality for managing books, members, and administrative tasks, with features for searching, borrowing, and returning books. The application supports role-based functionality, with distinct capabilities for administrators and members.

The project uses JDBC (Java Database Connectivity) for data storage, connecting to a MySQL database to manage library records persistently.

## Features

### General Features
- Console-based interactive interface for user operations.
- Modular code structure for ease of maintenance and future enhancements.
- Persistent data storage using MySQL through JDBC.

### Admin Features
- Add, remove, or update book details.
- View all books and their details.
- Search for books based on title, author, or genre.
- Manage member accounts (add, update, delete).
- View loan history and statistics on borrowed books.

### Member Features
- Search for books by title, author, or genre.
- Borrow, return or renew books.
- View personal loan history.
- Manage personal account details.

## Project Structure

### 1. LibraryApp.java
The main application class that acts as the entry point for the program, handling user interactions and facilitating communication between different components.

**Methods:**
- `void main(String[] args)`: Entry point for the application.


### 2. Admin.java
Handles administrative operations for managing books and library members.

**Methods:**
- `void addBook(title, author, genre, copies)`: Adds a new book.
- `void deleteBook(int bookId)`: Removes a book from the library.
- `void displayAllBooks()`: Lists all books.
- `void addMember(name, email, phone, address, password, balance)`: add new member.
- Additional methods for managing member accounts, viewing loan histories, and updating book/member details.

### 3. Member.java
Handles member-specific operations for interacting with library books and personal account management.

**Methods:**
- `void searchBook(String title)`: Allows a member to search for a book by title.
- `void borrowBook(int bookId)`: Facilitates borrowing of books.
- `void returnBook(int copyId)`: Handles book returns and aplly fine if late.
- `void viewLoanHistory(memberId)`: Displays a loan history of the member.
- Additional methods for managing personal information and interactions with library services.

## How It Works

### Account Management:
- Admins can add, update, or delete books and manage member accounts.
- Members can search for books, borrow, and return them.

### Book Management:
- Books are stored and managed within the application using MySQL as a persistent data storage solution.

### Transaction Management:
- Transactions, such as borrowing and returning books, are logged and managed to track availability and user history.

### JDBC and MySQL Integration:
- The application uses JDBC to connect and interact with a MySQL database.
- Data, such as book and member details, are persistently stored and retrieved using SQL queries executed via JDBC.

## Prerequisites
- **JDK**: Java Development Kit (Version 8 or later).
- **MySQL**: MySQL server for database operations.
- **JDBC**: Properly configured JDBC driver for MySQL.
- **An IDE** or text editor supporting Java development.

## Usage Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/codeByRahulShelake/LibraryMgmtApp.git
```
### 2. Restoring the Database

To restore the `rahul` database from the backup file, use the following command:

```bash
mysql -u root -p rahul < library_backup.sql
```
### 3. Run the application
To run the banking application, navigate to the project directory and execute the BankCloneApp class using your Java IDE or the command line:

```bash 
java BankCloneApp
```
### 4. Interact with the application
Follow the prompts on the console to create an account, log in, and manage your account and transactions.

Contributions
Feel free to fork this repository, make improvements, and create pull requests. Contributions are welcome!

License
This project is open source and licensed under the MIT License
