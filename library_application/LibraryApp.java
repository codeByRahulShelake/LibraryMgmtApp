package library_application;

import java.sql.*;
import java.util.*;

public class LibraryApp 
{
    public static void main(String[] args)  throws Exception
    {
    	Admin adminObj = new Admin();
    	Member memberObj = new Member();
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("--- Library Management System ---");
    	
    	boolean mainFlag = true;
    	int choice = 0;
    	
    	while(mainFlag)
    	{
    		System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("1: Admin | 2: User | 3: Exit");
            try 
            {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) 
            {
                System.out.println("Enter valid input.");
                sc.nextLine();
            }
            
            switch (choice)
            {
	            case 1 : //admin
	            	try 
	                {
	            		System.out.println("Enter Admin id : ");
	            		int adminId = sc.nextInt();
	            		sc.nextLine();
	            		System.out.println("Enter Admin Password : ");
	            		String adminPassword = sc.nextLine();
	            		
	            		adminObj.login(adminId, adminPassword);
	            		
	                } catch (InputMismatchException e) 
	                {
	                    System.out.println("Enter valid input.");
	                    sc.nextLine();
	                }
	            	
	            	Admin.AuthenticatedActions actions = adminObj.getAuthenticatedActions();
	            	
	            	if(actions != null)
	            	{
	            		boolean adminFlag = true;
	            		
	            		while(adminFlag)
	            		{
	            			System.out.println();
	                        System.out.println("-----------------------------------------------------------------------------------------------");
	                        System.out.println();
	                        System.out.println("1 : Book | 2: Member | 3: Loan | "
	                                            + "4: Exit \nSelect options from above to do related operations.");
	                        try 
		            		{
		            			choice = sc.nextInt();
		            			sc.nextLine();
		            		}catch (InputMismatchException e) 
			                {
			                    System.out.println("Enter valid input.");
			                    sc.nextLine();
			                }
		            		
		            		switch (choice)
		            		{
		            			// book
		            			case 1 :
		            				boolean bookFlag = true;
		            				
		            				while(bookFlag)
		            				{
		            					System.out.println();
		    	                        System.out.println("-----------------------------------------------------------------------------------------------");
		    	                        System.out.println();
		    	                        System.out.println("1 : Add book | 2: Add copies | 3: Delete book | 4: Delete copy | 5 : Display books"
		    	                                            + "\n6: Display copies  | 7: Search | 8: Update |  9 : View book details by id (including total available copies)"
		    	                        					+ "\n10 : View Available books | 11: Exit");
		    	                        try 
		    		            		{
		    		            			choice = sc.nextInt();
		    		            			sc.nextLine();
		    		            		}catch (InputMismatchException e) 
		    			                {
		    			                    System.out.println("Enter valid input.");
		    			                    sc.nextLine();
		    			                }
		    	                        
		    	                        
		    	                        switch (choice)
		    	                        {	
		    	                        	case 1 : // add book
		    	                        		try 
				    		            		{
				    		            			System.out.println("Enter title of the book : ");
				    		            			String title = sc.nextLine();
				    		            			System.out.println("Enter author of the book : ");
				    		            			String author = sc.nextLine();
				    		            			System.out.println("Enter genre of the book : ");
				    		            			String genre = sc.nextLine();
				    		            			System.out.println("Enter total copies : ");
				    		            			int copies = sc.nextInt();
				    		            			sc.nextLine();
				    		            			
				    		            			actions.addBook(title, author, genre, copies);
				    		            			
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		break;

		    	                        	case 2 : // add copy
		    	                        		try 
				    		            		{
				    		            			System.out.println("Enter book id : ");
				    		            			int bookId = sc.nextInt();
				    		            			System.out.println("Enter total copies to add : ");
				    		            			int copies = sc.nextInt();
				    		            			sc.nextLine();
				    		            			
				    		            			actions.addCopies(bookId, copies);
				    		            			
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		break;
		    	                        	
		    	                        	case 3 : // delete book
		    	                        		try 
				    		            		{
				    		            			System.out.println("Enter book id : ");
				    		            			int bookId = sc.nextInt();
				    		            			sc.nextLine();
				    		            			
				    		            			actions.deleteBook(bookId);
				    		            			
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		break;
		    	                        		
		    	                        	case 4 : // delete copy
		    	                        		try 
				    		            		{
				    		            			System.out.println("Enter copy id : ");
				    		            			int copyId = sc.nextInt();
				    		            			sc.nextLine();
				    		            			
				    		            			actions.deleteCopy(copyId);
				    		            			
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		break;

		    	                        	case 5 : // display books
		    	                        		actions.displayAllBooks();
		    	                        		break;
		    	                        		
		    	                        	case 6 : // display copies
		    	                        		try 
				    		            		{
				    		            			System.out.println("Enter book id to display its copies : ");
				    		            			int bookId = sc.nextInt();
				    		            			sc.nextLine();
				    		            			
				    		            			actions.displayCopies(bookId);
				    		            			
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		break;

		    	                        	case 7 : // search
		    	                        		boolean searchFlag = true;
		    	                        		while(searchFlag)
		    	                        		{
		    	                        			System.out.println();
					    	                        System.out.println("-----------------------------------------------------------------------------------------------");
					    	                        System.out.println("\nEnter what to search for from following options :");
					    	                        System.out.println("1 : Title | 2: Author | 3: Genre | 4: Exit");
					    	                        try 
					    		            		{
					    		            			choice = sc.nextInt();
					    		            			sc.nextLine();
					    		            		}catch (InputMismatchException e) 
					    			                {
					    			                    System.out.println("Enter valid input.");
					    			                    sc.nextLine();
					    			                }
					    	                        
					    	                        switch(choice)
					    	                        {
					    	                        	case 1 : //title
					    	                        		try 
							    		            		{
							    		            			System.out.println("Enter title of the book : ");
							    		            			String title = sc.nextLine();
							    		            			
							    		            			actions.searchBookByTitle(title);
							    		            			
							    		            		}catch (InputMismatchException e) 
							    			                {
							    			                    System.out.println("Enter valid input.");
							    			                    sc.nextLine();
							    			                }
					    	                        		break;

					    	                        	case 2 : //author
					    	                        		try 
							    		            		{
							    		            			System.out.println("Enter author of the book : ");
							    		            			String author = sc.nextLine();
							    		            			
							    		            			actions.searchBookByAuthor(author);
							    		            			
							    		            		}catch (InputMismatchException e) 
							    			                {
							    			                    System.out.println("Enter valid input.");
							    			                    sc.nextLine();
							    			                }
					    	                        		break;

					    	                        	case 3 : //genre
					    	                        		try 
							    		            		{
							    		            			System.out.println("Enter genre of the book : ");
							    		            			String genre = sc.nextLine();
							    		            			
							    		            			actions.searchBookByGenre(genre);
							    		            			
							    		            		}catch (InputMismatchException e) 
							    			                {
							    			                    System.out.println("Enter valid input.");
							    			                    sc.nextLine();
							    			                }
					    	                        		break;

					    	                        	case 4 : //exit
					    	                        		searchFlag = false;
					    	                        		break;
					    	                        		
					    	                        	default :
					    	                        		System.out.println("Enter choice from above options.");
					    	                        } // switch ends here
					    	                      
		    	                        		} //search while loop ends here
		    	                        		
		    	                        		break;

		    	                        	case 8 : // update
		    	                        		boolean updateFlag = true;
		    	                        		
		    	                        		while(updateFlag)
		    	                        		{
		    	                        			System.out.println();
					    	                        System.out.println("-----------------------------------------------------------------------------------------------");
					    	                        System.out.println("\nEnter what to update from following options :");
					    	                        System.out.println("1 : Book title | 2: Book author | 3: Book genre | 4: Copy Status | 5: Exit");
					    	                        try 
					    		            		{
					    		            			choice = sc.nextInt();
					    		            			sc.nextLine();
					    		            		}catch (InputMismatchException e) 
					    			                {
					    			                    System.out.println("Enter valid input.");
					    			                    sc.nextLine();
					    			                }
					    	                        
					    	                        switch(choice)
					    	                        {
					    	                        case 1 : //update book title
				    	                        		try 
						    		            		{
				    	                        			System.out.println("Enter Book id : ");
				    	                        			int bookId = sc.nextInt();
				    	                        			sc.nextLine();
						    		            			System.out.println("Enter new title of the book : ");
						    		            			String newTitle = sc.nextLine();
						    		            			
						    		            			actions.updateBookTitle(bookId, newTitle);;
						    		            			
						    		            		}catch (InputMismatchException e) 
						    			                {
						    			                    System.out.println("Enter valid input.");
						    			                    sc.nextLine();
						    			                }
				    	                        		break;

				    	                        	case 2 : //update book author
				    	                        		try 
						    		            		{
				    	                        			System.out.println("Enter Book id : ");
				    	                        			int bookId = sc.nextInt();
				    	                        			sc.nextLine();
						    		            			System.out.println("Enter new author of the book : ");
						    		            			String newAuthor = sc.nextLine();
						    		            			
						    		            			actions.updateBookAuthor(bookId, newAuthor);
						    		            			
						    		            		}catch (InputMismatchException e) 
						    			                {
						    			                    System.out.println("Enter valid input.");
						    			                    sc.nextLine();
						    			                }
				    	                        		break;

				    	                        	case 3 : //update book genre
				    	                        		try 
						    		            		{
				    	                        			System.out.println("Enter Book id : ");
				    	                        			int bookId = sc.nextInt();
				    	                        			sc.nextLine();
						    		            			System.out.println("Enter new genre of the book : ");
						    		            			String newGenre = sc.nextLine();
						    		            			
						    		            			actions.updateBookGenre(bookId, newGenre);
						    		            			
						    		            		}catch (InputMismatchException e) 
						    			                {
						    			                    System.out.println("Enter valid input.");
						    			                    sc.nextLine();
						    			                }
				    	                        		break;
				    	                        		
				    	                        	case 4 : //update copy status
				    	                        		try 
						    		            		{
				    	                        			String newStatus = "";
				    	                        			System.out.println("Enter Book id : ");
				    	                        			int bookId = sc.nextInt();
						    		            			System.out.println("Enter 1 : borrowed  | 2 : available");
						    		            			int ch = sc.nextInt();
						    		            			sc.nextLine();
						    		            			if(ch == 1)
						    		            				newStatus = "borrowed";
						    		            			else
						    		            				newStatus = "available";
						    		            			
						    		            			actions.updateCopyStatus(bookId, newStatus);
						    		            			
						    		            		}catch (InputMismatchException e) 
						    			                {
						    			                    System.out.println("Enter valid input.");
						    			                    sc.nextLine();
						    			                }
				    	                        		break;

				    	                        	case 5 : //exit
				    	                        		updateFlag = false;
				    	                        		break;
				    	                        		
				    	                        	default :
				    	                        		System.out.println("Enter valid choice.");
					    	                        }
					    	                      
		    	                        		} //update while loop ends here
		    	                        			
		    	                        		break;
		    	                        		
		    	                        	case 9 : // view book details by id
		    	                        		try 
				    		            		{
		    	                        			System.out.println("Enter Book id : ");
		    	                        			int bookId = sc.nextInt();
				    		            			sc.nextLine();
				    		            			
				    		            			actions.viewBookDetailsByBookId(bookId);
				    		            			
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		break;
		    	                        		
		    	                        	case 10 :
		    	                        		actions.listAvailableBooks();
		    	                        		break;

		    	                        	case 11 : // exit
		    	                        		bookFlag = false;
		    	                        		break;
		    	                        		
		    	                        }
		            				}
		            				break; // Book case ends here
		            				
		            			//Member
		            			case 2 :
		            				boolean memberFlag = true;
		            				while(memberFlag)
		            				{
		            					System.out.println();
		    	                        System.out.println("-----------------------------------------------------------------------------------------------");
		    	                        System.out.println();
		    	                        System.out.println("1 : Add member | 2: Delete member | 3: Display all member | 4: Search By id"
		    	                        					+ "5: Update | 6: Exit");
		    	                        try 
		    		            		{
		    		            			choice = sc.nextInt();
		    		            			sc.nextLine();
		    		            		}catch (InputMismatchException e) 
		    			                {
		    			                    System.out.println("Enter valid input.");
		    			                    sc.nextLine();
		    			                }
		    	                        
		    	                        switch(choice)
		    	                        {
		    	                        	case 1 :
		    	                        		try 
				    		            		{
		    	                        			System.out.println("Enter member name : ");
		    	                        			String name = sc.nextLine();
		    	                        			String email = returnEmailId(sc);
		    	                        			String phone = String.valueOf(returnPhone(sc));
		    	                        			System.out.println("Enter member address : ");
		    	                        			String address = sc.nextLine();
		    	                        			System.out.println("Enter password (i.e it is imp dont forget) : ");
		    	                        			String password = sc.nextLine();
		    	                        			int balance = returnBalance(sc);
		    	                        			sc.nextLine();
		    	                        			actions.addMember(name, email, phone, address, password, balance);
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		
		    	                        		break;
		    	                        		
		    	                        	case 2 : // delete member
		    	                        		try 
				    		            		{
		    	                        			System.out.println("Enter member id : ");
		    	                        			int memberId = sc.nextInt();
		    	                        			sc.nextLine();
		    	                        			actions.deleteMember(memberId);
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		break;
		    	                        		
		    	                        	case 3 : // display all members
		    	                        		actions.displayAllMembers();
		    	                        		break;
		    	                        		
		    	                        	case 4 : // search by id
		    	                        		try 
				    		            		{
		    	                        			System.out.println("Enter member id : ");
		    	                        			int memberId = sc.nextInt();
		    	                        			sc.nextLine();
		    	                        			actions.searchMemberById(memberId);
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		break;
		    	                        		
		    	                        	case 5 : // update
		    	                        		boolean updateFlag = true;
		    	                        		while(updateFlag)
		    	                        		{
		    	                        			System.out.println();
					    	                        System.out.println("-----------------------------------------------------------------------------------------------");
					    	                        System.out.println("\nEnter what to update from following options :");
					    	                        System.out.println("1 : Name | 2: Email | 3: Address | 4: Phone | 5: Add balance"
					    	                        		+ "6: Exit");
		    		    	                        try 
		    		    		            		{
		    		    		            			choice = sc.nextInt();
		    		    		            			sc.nextLine();
		    		    		            		}catch (InputMismatchException e) 
		    		    			                {
		    		    			                    System.out.println("Enter valid input.");
		    		    			                    sc.nextLine();
		    		    			                }
		    		    	                        
		    		    	                        switch(choice)
		    		    	                        {
		    		    	                        	case 1 : //name
		    		    	                        		try 
							    		            		{
					    	                        			System.out.println("Enter member id : ");
					    	                        			int memberId = sc.nextInt();
					    	                        			sc.nextLine();
							    		            			System.out.println("Enter new name of member : ");
							    		            			String newName = sc.nextLine();
							    		            			
							    		            			actions.updateMemberName(memberId, newName);
							    		            			
							    		            		}catch (InputMismatchException e) 
							    			                {
							    			                    System.out.println("Enter valid input.");
							    			                    sc.nextLine();
							    			                }
		    		    	                        		break;

		    		    	                        	case 2 : // email
		    		    	                        		try 
							    		            		{
					    	                        			System.out.println("Enter member id : ");
					    	                        			int memberId = sc.nextInt();
					    	                        			sc.nextLine();
							    		            			String newEmail = returnEmailId(sc);
							    		            			
							    		            			actions.updateMemberEmail(memberId, newEmail);
							    		            			
							    		            		}catch (InputMismatchException e) 
							    			                {
							    			                    System.out.println("Enter valid input.");
							    			                    sc.nextLine();
							    			                }
		    		    	                        		break;

		    		    	                        	case 3 : //address
		    		    	                        		try 
							    		            		{
					    	                        			System.out.println("Enter member id : ");
					    	                        			int memberId = sc.nextInt();
					    	                        			sc.nextLine();
							    		            			System.out.println("Enter new address of member : ");
							    		            			String newAddress = sc.nextLine();
							    		            			
							    		            			actions.updateMemberAddress(memberId, newAddress);
							    		            			
							    		            		}catch (InputMismatchException e) 
							    			                {
							    			                    System.out.println("Enter valid input.");
							    			                    sc.nextLine();
							    			                }
		    		    	                        		break;

		    		    	                        	case 4 : // phone
		    		    	                        		try 
							    		            		{
					    	                        			System.out.println("Enter member id : ");
					    	                        			int memberId = sc.nextInt();
					    	                        			sc.nextLine();
							    		            			System.out.println("Enter new phone no of member : ");
							    		            			String newPhone = String.valueOf(returnPhone(sc));
							    		            			
							    		            			actions.updateMemberPhone(memberId, newPhone);
							    		            			
							    		            		}catch (InputMismatchException e) 
							    			                {
							    			                    System.out.println("Enter valid input.");
							    			                    sc.nextLine();
							    			                }
		    		    	                        		break;
		    		    	                        		
		    		    	                        	case 5 : //add balance
		    		    	                        		try 
							    		            		{
					    	                        			System.out.println("Enter member id : ");
					    	                        			int memberId = sc.nextInt();
					    	                        			sc.nextLine();
							    		            			int newBalance = returnBalance(sc);
							    		            			
							    		            			actions.addBalance(memberId, newBalance);
							    		            			
							    		            		}catch (InputMismatchException e) 
							    			                {
							    			                    System.out.println("Enter valid input.");
							    			                    sc.nextLine();
							    			                }
		    		    	                        		break;

		    		    	                        	case 6 :
		    		    	                        		updateFlag = false;
		    		    	                        		break;
		    		    	                        		
		    		    	                        	default : 
		    		    	                        		System.out.println("Enter choice from above options");
		    		    	                        }// update switch ends here
		    		    	                        
		    	                        		}//update loop ends here
		    	                        		
		    	                        		break; //member update case ends here
		    	                        		
		    	                        	case 6 : // exit
		    	                        		memberFlag = false;
		    	                        		break;
		    	                        		
		    	                        		default :
		    	                        			System.out.println("Enter choice from above options.");
		    	                        }// member choice ends here
		            				}// member while loop ends here
		            				
	    	                        
		            				break; // Member case ends here
		            				
		            			// Loan
		            			case 3 :
		            				boolean loanFlag = true;
		            				while(loanFlag)
		            				{
		            					System.out.println();
		    	                        System.out.println("-----------------------------------------------------------------------------------------------");
		    	                        System.out.println();
		    	                        System.out.println("1 : Loan history | 2: Loan History of a member | 3: Popular Book | 4: Popular Genre "
		    	                        		+ "\n5: Total books borrowed | 6: Total books Loaned | 7: Exit");
		    	                        try 
		    		            		{
		    		            			choice = sc.nextInt();
		    		            			sc.nextLine();
		    		            		}catch (InputMismatchException e) 
		    			                {
		    			                    System.out.println("Enter valid input.");
		    			                    sc.nextLine();
		    			                }
		    	                        
		    	                        switch(choice)
		    	                        {
		    	                        	case 1 : //loan history
		    	                        		actions.viewLoanHistory();
		    	                        		break;

		    	                        	case 2 : //loan history of member
		    	                        		try 
				    		            		{
		    	                        			System.out.println("Enter member Id : ");
				    		            			int memberId = sc.nextInt();
				    		            			sc.nextLine();
				    		            			actions.viewLoanHistoryByMemberId(memberId);
				    		            		}catch (InputMismatchException e) 
				    			                {
				    			                    System.out.println("Enter valid input.");
				    			                    sc.nextLine();
				    			                }
		    	                        		break;

		    	                        	case 3 : // popular books
		    	                        		actions.displayMostBorrowedBooks();
		    	                        		break;

		    	                        	case 4 : // popular genre
		    	                        		actions.displayMostPopularGenres();
		    	                        		break;

		    	                        	case 5 : // total borowed book that are not returned
		    	                        		actions.countBorrowedBooks();
		    	                        		break;

		    	                        	case 6 : // total books loaned till now
		    	                        		actions.displayTotalBooksLoaned();
		    	                        		break;

		    	                        	case 7 :
		    	                        		loanFlag = false;
		    	                        		break;
		    	                        		
		    	                        	default :
		    	                        		System.out.println("Enter choice from above options.");
		    	                        
		    	                        }
		    	                        
		            				}// loan while loop ends here
		            				break; // loan case ends here
		            				
		            			case 4 :
		            				adminFlag = false;
		            				break; 
		            				
		            			default :
		            				System.out.println("Enter choice from above.");
		            		} // admin switch ends here
	            		} // admin while loop ends here
	            	} // admin if actions ends here
	            	
	            	break;// admin case ends here
	            
	            case 2 : //user
	            	boolean userFlag = true;
	            	while(userFlag)
	            	{
	                    try 
	                    {
	                    	System.out.println();
		                    System.out.println("-----------------------------------------------------------------------------------------------");
		                    System.out.println();
		                    System.out.println("1: Get Membership | 2: Log in | 3: Exit");
	                        choice = sc.nextInt();
	                        sc.nextLine();
	                    } catch (InputMismatchException e) 
	                    {
	                        System.out.println("Enter valid input.");
	                        sc.nextLine();
	                    }
	                    switch(choice)
	                    {
	                    	case 1 : //get membership
	                    		try 
    		            		{
                        			System.out.println("Enter member name : ");
                        			String name = sc.nextLine();
                        			String email = returnEmailId(sc);
                        			String phone = String.valueOf(returnPhone(sc));
                        			System.out.println("Enter member address : ");
                        			String address = sc.nextLine();
                        			System.out.println("Enter password (i.e it is imp dont forget) : ");
                        			String password = sc.nextLine();
                        			int balance = returnBalance(sc);
                        			sc.nextLine();
                        			memberObj.addMembership(name, email, phone, address, password, balance);
    		            		}catch (InputMismatchException e) 
    			                {
    			                    System.out.println("Enter valid input.");
    			                    sc.nextLine();
    			                }
	                    		
	                    		break;
	                    	case 2 : // Log in
	                    		int memberId = 0;
	                    		try 
	        	                {
	        	            		System.out.println("Enter Member id : ");
	        	            		memberId = sc.nextInt();
	        	            		sc.nextLine();
	        	            		System.out.println("Enter Member Password : ");
	        	            		String memberPassword = sc.nextLine();
	        	            		
	        	            		memberObj.login(memberId, memberPassword);
	        	            		
	        	                } catch (InputMismatchException e) 
	        	                {
	        	                    System.out.println("Enter valid input.");
	        	                    sc.nextLine();
	        	                }
	        	            	
	        	            	Member.AuthenticatedActions action = memberObj.getAuthenticatedActions();
	        	            	
	        	            	if(action != null)
	        	            	{
	        	            		boolean userActionFlag = true;
	        	            		while(userActionFlag)
	        	            		{
	        	            			try 
	        		                    {
	        		                    	System.out.println();
	        			                    System.out.println("-----------------------------------------------------------------------------------------------");
	        			                    System.out.println();
	        			                    System.out.println("1: My details | 2: Available books  | 3: Borrow book | 4: Return Book | 5: Renew Book"
	        			                    		+ "\n6: Lost Book | 7: Search book | 8: Update details | 9: Loan History | 10: Cancel Membership"
	        			                    		+ "\n11 : Exit");
	        		                        choice = sc.nextInt();
	        		                        sc.nextLine();
	        		                    } catch (InputMismatchException e) 
	        		                    {
	        		                        System.out.println("Enter valid input.");
	        		                        sc.nextLine();
	        		                    }
	        	            		 switch(choice)
	        	            		 {
	        	            		 	case 1 : // my details
	        	            		 		action.displayMemberById(memberId);
	        	            		 		break;

	        	            		 	case 2 : // available books
	        	            		 		action.listAvailableBooks();
	        	            		 		break;

	        	            		 	case 3 : // borrow books
	        	            		 		try 
	        	        	                {
	        	        	            		System.out.println("Enter book id : ");
	        	        	            		int bookId = sc.nextInt();
	        	        	            		sc.nextLine();
	        	        	            		
	        	        	            		action.borrowBook(bookId, memberId);
	        	        	            		
	        	        	                } catch (InputMismatchException e) 
	        	        	                {
	        	        	                    System.out.println("Enter valid input.");
	        	        	                    sc.nextLine();
	        	        	                }
	        	            		 		break;

	        	            		 	case 4 :// return book
	        	            		 		try 
	        	        	                {
	        	        	            		System.out.println("Enter book copy id : ");
	        	        	            		int copyId = sc.nextInt();
	        	        	            		sc.nextLine();
	        	        	            		
	        	        	            		action.returnBook(copyId, memberId);
	        	        	            		
	        	        	                } catch (InputMismatchException e) 
	        	        	                {
	        	        	                    System.out.println("Enter valid input.");
	        	        	                    sc.nextLine();
	        	        	                }
	        	            		 		break;

	        	            		 	case 5 : //renew book
	        	            		 		try 
	        	        	                {
	        	        	            		System.out.println("Enter book copy id : ");
	        	        	            		int copyId = sc.nextInt();
	        	        	            		sc.nextLine();
	        	        	            		
	        	        	            		action.renewBook(copyId, memberId);
	        	        	            		
	        	        	                } catch (InputMismatchException e) 
	        	        	                {
	        	        	                    System.out.println("Enter valid input.");
	        	        	                    sc.nextLine();
	        	        	                }
	        	            		 		break;

	        	            		 	case 6 : // lost book
	        	            		 		try 
	        	        	                {
	        	        	            		System.out.println("Enter book copy id : ");
	        	        	            		int copyId = sc.nextInt();
	        	        	            		sc.nextLine();
	        	        	            		
	        	        	            		action.reportBookLost(copyId, memberId);
	        	        	            		
	        	        	                } catch (InputMismatchException e) 
	        	        	                {
	        	        	                    System.out.println("Enter valid input.");
	        	        	                    sc.nextLine();
	        	        	                }
	        	            		 		break;

	        	            		 	case 7 : // search book
	        	            		 		boolean searchFlag = true;
	        	            		 		while(searchFlag)
	        	            		 		{
	        	            		 			try 
	    	        		                    {
	    	        		                    	System.out.println();
	    	        			                    System.out.println("-----------------------------------------------------------------------------------------------");
	    	        			                    System.out.println();
	    	        			                    System.out.println("1: By title | 2: By author  | 3: By genre | 4: Exit");
	    	        		                        choice = sc.nextInt();
	    	        		                        sc.nextLine();
	    	        		                    } catch (InputMismatchException e) 
	    	        		                    {
	    	        		                        System.out.println("Enter valid input.");
	    	        		                        sc.nextLine();
	    	        		                    }
	        	            		 			switch(choice)
	        	            		 			{
	        	            		 				case 1 :// by title
	        	            		 					try 
	    	    	        		                    {
	    	    	        		                    	System.out.println("Enter title :");
	    	    	        		                        String title = sc.nextLine();
	    	    	        		                        action.searchBookByTitle(title);
	    	    	        		                    } catch (InputMismatchException e) 
	    	    	        		                    {
	    	    	        		                        System.out.println("Enter valid input.");
	    	    	        		                    }
	        	            		 					break;

	        	            		 				case 2 :// by author
	        	            		 					try 
	    	    	        		                    {
	    	    	        		                    	System.out.println("Enter author :");
	    	    	        		                        String author = sc.nextLine();
	    	    	        		                        action.searchBookByAuthor(author);
	    	    	        		                    } catch (InputMismatchException e) 
	    	    	        		                    {
	    	    	        		                        System.out.println("Enter valid input.");
	    	    	        		                    }
	        	            		 					break;

	        	            		 				case 3 : // by genre
	        	            		 					try 
	    	    	        		                    {
	    	    	        		                    	System.out.println("Enter genre :");
	    	    	        		                        String genre = sc.nextLine();
	    	    	        		                        action.searchBookByGenre(genre);
	    	    	        		                    } catch (InputMismatchException e) 
	    	    	        		                    {
	    	    	        		                        System.out.println("Enter valid input.");
	    	    	        		                    }
	        	            		 					break;

	        	            		 				case 4 : //exit
	        	            		 					searchFlag = false;
	        	            		 					break;
	        	            		 					
	        	            		 				default :
	        	            		 					System.out.println("Enter choice from above options.");
	        	            		 			}// search switch ends here
	        	            		 		}// search while loop ends here
	        	            		 		break;

	        	            		 	case 8 : // update details
	        	            		 		boolean updateFlag = true;
	        	            		 		while(updateFlag)
	        	            		 		{
	        	            		 			try 
	    	        		                    {
	    	        		                    	System.out.println();
	    	        			                    System.out.println("-----------------------------------------------------------------------------------------------");
	    	        			                    System.out.println();
	    	        			                    System.out.println("1: Name | 2: Email  | 3: Phone | 4: Address"
	    	        			                    		+ "5: Add balance | 6: Change password  | 7: Exit");
	    	        		                        choice = sc.nextInt();
	    	        		                        sc.nextLine();
	    	        		                    } catch (InputMismatchException e) 
	    	        		                    {
	    	        		                        System.out.println("Enter valid input.");
	    	        		                        sc.nextLine();
	    	        		                    }
	        	            		 			
	        	            		 			switch(choice)
	        	            		 			{
	        	            		 				case 1:// name
	        	            		 					try 
	    	    	        		                    {
	    	    	        		                    	System.out.println("Enter new name :");
	    	    	        		                        String newName = sc.nextLine();
	    	    	        		                        action.updateName(memberId, newName);
	    	    	        		                    } catch (InputMismatchException e) 
	    	    	        		                    {
	    	    	        		                        System.out.println("Enter valid input.");
	    	    	        		                    }
	        	            		 					break;

	        	            		 				case 2: // email
	        	            		 					try 
	    	    	        		                    {
	    	    	        		                    	System.out.println("Enter new email :");
	    	    	        		                        String newEmail = returnEmailId(sc);
	    	    	        		                        action.updateEmail(memberId, newEmail);
	    	    	        		                    } catch (InputMismatchException e) 
	    	    	        		                    {
	    	    	        		                        System.out.println("Enter valid input.");
	    	    	        		                    }
	        	            		 					break;

	        	            		 				case 3: // phone
	        	            		 					try 
	    	    	        		                    {
	    	    	        		                    	System.out.println("Enter new phone no :");
	    	    	        		                        String newPhoneNo = String.valueOf(returnPhone(sc));
	    	    	        		                        action.updatePhone(memberId, newPhoneNo);
	    	    	        		                    } catch (InputMismatchException e) 
	    	    	        		                    {
	    	    	        		                        System.out.println("Enter valid input.");
	    	    	        		                    }
	        	            		 					break;

	        	            		 				case 4: // address
	        	            		 					try 
	    	    	        		                    {
	    	    	        		                    	System.out.println("Enter new address :");
	    	    	        		                        String newAddress = sc.nextLine();
	    	    	        		                        action.updateAddress(memberId, newAddress);
	    	    	        		                    } catch (InputMismatchException e) 
	    	    	        		                    {
	    	    	        		                        System.out.println("Enter valid input.");
	    	    	        		                    }
	        	            		 					break;

	        	            		 				case 5: // add balance
	        	            		 					try 
	    	    	        		                    {
	    	    	        		                    	int balance = returnBalance(sc);
	    	    	        		                    	action.addBalance(memberId, balance);
	    	    	        		                    } catch (InputMismatchException e) 
	    	    	        		                    {
	    	    	        		                        System.out.println("Enter valid input.");
	    	    	        		                    }
	        	            		 					break;

	        	            		 				case 6: // change password
	        	            		 					try 
	    	    	        		                    {
	    	    	        		                    	System.out.println("Enter new password :");
	    	    	        		                        String newPassword = sc.nextLine();
	    	    	        		                        action.changePassword(memberId, newPassword);
	    	    	        		                    } catch (InputMismatchException e) 
	    	    	        		                    {
	    	    	        		                        System.out.println("Enter valid input.");
	    	    	        		                    }
	        	            		 					break;

	        	            		 				case 7:
	        	            		 					updateFlag = false;
	        	            		 					break;
	        	            		 					
	        	            		 				default :
	        	            		 					System.out.println("Enter choice from above options.");
	        	            		 			} // update choice ends here
	        	            		 		} // update while loop ends here
	        	            		 		break;

	        	            		 	case 9 : // loan history
	        	            		 		action.viewLoanHistory(memberId);
	        	            		 		break;

	        	            		 	case 10 : // cancel membership
	        	            		 		action.cancelMembership(memberId);
	        	            		 		break;

	        	            		 	case 11 :
	        	            		 		userActionFlag = false;
	        	            		 		break;
	        	            		 		
	        	            		 	default :
	        	            		 		System.out.println("Enter choice from above options.");
	        	            		 } //action switch ends here
	        	            		} // user action while loop ends here
	        	            		 
	        	            	}// member action is ends here
	                    		break; //log in case ends here
	                    		
	                    	case 3 : //exit
	                    		userFlag = false;
	                    		break;
	                    		
	                    	default :
	                    		System.out.println("Enter choice from above options.");
	                    } // user choice ends here
	            	}// user while loop ends here
	            	break;// user case ends here
	            
	            
	            case 3 : // exit
	            	mainFlag = false;
	            	break;
	            
	            default :
	            	System.out.println("Enter choice from above options.");
            } // main switch ends here
    	} // main while loop ends here
    } // main method ends here 
    
    public static int returnBalance(Scanner sc)
    {
        boolean balanceFlag = false;
        int balance = 0;
        while(!balanceFlag)
        {
            try 
            {
                System.out.println("Enter balance between 1000 to 3000:");
                balance = sc.nextInt();
                sc.nextLine();
                if(balance > 3000 || balance < 1000)
                	System.out.println("Enter balance between 1000 to 3000:");
                else
                	balanceFlag = true;
            } catch (Exception e) 
            {
                System.out.println("Enter digit only.");
                sc.nextLine();
            }
        }
        return balance;
    }
    
    public static String returnEmailId(Scanner sc)
    {
        boolean flag = true;
        String email = "";

        while(flag)
        {
            System.out.println("Enter Email Id : ");
            email = sc.nextLine();

            if(email.endsWith("@gmail.com"))
                flag = false;
            else
                System.out.println("Enter valid Email Id (i.e. ends with @gmail.com)");
        }
        return email;
    }
    
    public static long returnPhone(Scanner sc)
    {
        boolean phFlag = false;
        long phNo = 0;
        while(!phFlag)
        {
            try 
            {
                System.out.println("Enter Phone No.:");
                phNo = sc.nextLong();
                sc.nextLine();
                int digitCount = String.valueOf(phNo).length();
                if(digitCount == 10)
                    phFlag = true; 
                else 
                    System.out.println("Enter 10 digit no."); 
            } catch (Exception e) 
            {
                System.out.println("Enter digit only.");
                sc.nextLine();
            }
        }
        return phNo;
    }

    
    
} // main class ends here

