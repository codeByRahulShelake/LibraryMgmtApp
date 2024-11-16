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
		    	                        					+ "10 : Exit");
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
						    		            			
						    		            			actions.updateBookTitle(bookId, newTitle);
						    		            			
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

		    	                        	case 10 : // exit
		    	                        		bookFlag = false;
		    	                        		break;
		    	                        		
		    	                        }
		            				}
		            				break; // Book case ends here
		            				
		            			//Member
		            			case 2 :
		            				
		            				break; // Member case ends here
		            				
		            			// Loan
		            			case 3 :
		            				
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
	            	
	            	break;// user case ends here
	            
	            
	            case 3 : // exit
	            	mainFlag = false;
	            	break;
	            
	            default :
	            	System.out.println("Enter choice from above options.");
            } // main switch ends here
    	} // main while loop ends here
    } // main method ends here 
} // main class ends here

