/**
 * Author: Dillon Evans
 * Email: <dillon.e.evans@okstate.edu>
 * Date: September 7th, 2020
 * Program Description: This program allows a librarian to manage a library given the list of books in 
 * the library as well the list of students in the library system. The librarian can perform tasks such as 
 * viewing every book available to rent, checking in and out books, as well as maintaining a waitlist for 
 * a book that is in demand.
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Driver {
    static boolean bookListCreated = false, studentListCreated = false;
    static ElementList<Book> bookList, rentedBookList = new ElementList<>();
    static ElementList<Student> studentList = new ElementList<>();
    static Scanner input = new Scanner(System.in);
    
    /**
     * The program entry point
     * @param args Command Line Parameters
     */
    public static void main(String[] args) 
    {
        promptUser();
    }

    /**
     * Prompts the user for input with an interactive console menu.
     */
    public static void promptUser()
    {
        boolean quit = false;
        String decision;;

        try
        {
            System.out.println("Welcome to the Library System! Enter one of the numbers below to perform the action specified.");
           
            //Continuously prompt the user until they are finished
            while (!quit)
            {
                System.out.print("1) Create a list of books\n2) Rent a book to a student\n3) Return a book to the library\n" +
                "4) Display the details of a book\n5) Print a list of all books and their information\n" + 
                "6) Print a list of books rented by each student\n7) Waitlist for a book along with the book info\n8) quit\n>>");
                decision = input.nextLine().trim();
                System.out.println();
    
                //Handles user input
                switch (decision)
                {
                    case "1":
                        createBookList();
                        break;
                    case "2":
                        rentBooks();
                        break;
                    case "3":
                        returnBook();
                        break;
                    case "4":
                        viewBookDetails();
                        break;
                    case "5":
                        printBookList();
                        break;
                    case "6":
                        printRentalList();
                        break;
                    case "7":
                        printWaitlist();
                        break;
                    case "8":
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid Operation!");
                        break;
                }   
                System.out.println();
            }
            input.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Generates a list of books present in the library by parsing Books.txt
     */
    public static void createBookList()
    {
        String filePath, input;
        String[] parsedTokens;
        try
        {
            if (bookListCreated) {throw new Exception("The list of books has already been created.");}
            bookList = new ElementList<>();
            filePath = "Books.txt";
            Path file = Paths.get(filePath);
            InputStream inStream = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader inReader = new BufferedReader(new InputStreamReader(inStream));      
            while ((input = inReader.readLine()) != null)
            {
                parsedTokens = input.trim().split("\t");
                Book book = new Book();
                book.setTitle(parsedTokens[0]);
                book.setISBN(parsedTokens[1]);
                book.setDOI(parsedTokens[2]);
                book.setSubjectCategory(parsedTokens[3]);
                book.setExpectedPublicationDate(parsedTokens[4]);
                book.setFirstAuthor(parsedTokens[5]);
                book.setSecondAuthor(parsedTokens[6]);
                book.setFirstAuthorAffiliation(parsedTokens[7]);
                book.setSecondAuthorAffiliation(parsedTokens[8]);
                book.setNumberOfCopies(Integer.parseInt(parsedTokens[9]));
                bookList.append(book.getTitle(), book);
            }
            inStream.close();
            inReader.close();
            bookListCreated = true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

     /**
     * Creates a list of every student in the library system by parsing Students.txt
     * and rents them the requested book. If the book is unavailable the student will be added to the waitlist.
     */
    public static void rentBooks()
    {
        String[] parsedTokens;
        String filePath, input;

        try
        {
            //The student list cannot be created until the book list is
            if (!bookListCreated)
            {
                throw new Exception("Please populate the student list first.");
            }
            
            filePath = "Students.txt";

            Path file = Paths.get(filePath);
            InputStream inStream = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader inReader = new BufferedReader(new InputStreamReader(inStream));    

            //Parse the file line by line until EOF is reached. 
            while ((input = inReader.readLine()) != null)
            {
                Student student;
                Book rentedBook;
                String studentName;

                parsedTokens = input.trim().split("\t");
                studentName = parsedTokens[0].replace("\"", "");

                if (!studentListCreated)
                {
                    student = new Student();
                    student.setName(studentName);
                }
                else
                {
                    student = studentList.getElement(studentName);
                }

                if (parsedTokens[0].contains("\""))
                {
                    //Read in the book titles
                    for (int i = 1; i < parsedTokens.length; i++) 
                    {
                        //f the string is not blank it represents a book
                        if (!parsedTokens[i].isBlank())
                        {
                            rentedBook = bookList.getElement(parsedTokens[i]);
                            student.rentBook(rentedBook);
                            
                            // A bit costly, but it ensures that there are no duplicate entries
                            if (!rentedBookList.contains(rentedBook.getTitle())) 
                            {
                                rentedBookList.append(rentedBook.getTitle(), rentedBook);
                            }
                        }
                    }
                    //The student list only needs to be populated once.
                    if (!studentListCreated)
                    studentList.append(student.getName(), student);
                }
            }
            inStream.close();
            inReader.close();
            studentListCreated = true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Returns a book that a student has checked out. The first student on the waitlist (if any) 
     * will automatically check out the book and the next student will advance to 
     * the head of the list.
     */
    public static void returnBook()
    {
        try
        {
            String bookTitle, studentName;
            Book bookToReturn;
            Student returningStudent;

            if (!studentListCreated)
            {
                throw new Exception("There is insufficient data. Please create the list of students and books.");
            }

            System.out.print("Please enter the name of the student in the form last, first >> ");
            studentName = input.nextLine().trim();

            //Formatting input
            if (studentName.contains("\"")) {studentName = studentName.replace("\"", "");}
            if (!studentName.contains(",")) {studentName = studentName.replace(" ", ", ");}
            returningStudent = studentList.getElement(studentName);

            //If the student does not exist
            if (returningStudent == null)
            {
                throw new Exception("Error: The specified student is not present in our system.");
            }  
            else
            {
                System.out.print("Please specify the title of the book you are returning >>");
                bookTitle = input.nextLine();
                bookToReturn = returningStudent.getRentedBookList().getElement(bookTitle);

                //If the book does not exist
                if (bookToReturn == null) 
                {
                    throw new Exception("The student has not checked out that book");
                }
                else
                {
                   returningStudent.returnBook(bookToReturn);
                   //If nobody is currently renting, remove it from the list
                   if (bookToReturn.getRenterList().getElementCount() == 0)
                   {
                        rentedBookList.removeElement(bookTitle);
                   }
                }
            }          
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

     /**
     * Prints all of the information of the book the user specifies
     */
    public static void viewBookDetails()
    {
        try
        {
            String bookTitle;
            Book bookToView;

            if (!bookListCreated) 
            {
                throw new Exception("There is insufficient data. Please create the book list.");
            }

            System.out.print("Please specify the title of the book you are inquiring for >>");
            bookTitle = input.nextLine().trim();
            bookToView = bookList.getElement(bookTitle);

            if (bookToView == null)
            {
                throw new Exception("Error: The specified book is not present in our library.");
            }
            else
            {
                System.out.println(bookToView);
            }      
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the information of every book found in the library
     */
    public static void printBookList()
    {
        try
        {
            if (bookListCreated)
            {
                bookList.print();
            }
            else
            {
                throw new Exception("There is insufficient data. Please create the book list.");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the list of all books that are currently being rented by 
     * students.
     */
    public static void printRentalList()
    {
        try
        {
            if (studentListCreated)
            {
                rentedBookList.print();
            }
            else
            {
                throw new Exception("There is no rental information available");
            }   
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the waitlist for a user specified book
     */
    public static void printWaitlist()
    {
        try
        {
            if (bookListCreated)
            {
                String title;
                Book toView;

                System.out.print("Please specify the title of the book you are inquiring for >> ");
                title = input.nextLine().trim();
                toView = bookList.getElement(title);

                if (toView != null)
                {
                    System.out.println(toView);
                    toView.viewWaitlist();
                }
                else
                {
                    throw new Exception("Error: The specified book is not present in our library.");
                }
            }
            else
            {
                throw new Exception("There is no rental information available.");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }        
    }
}