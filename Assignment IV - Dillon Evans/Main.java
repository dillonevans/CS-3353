/**
 * Author: Dillon Evans
 * Email: <dillon.e.evans@okstate.edu>
 * Date: November 2nd, 2020
 * Program Description: This program allows for books to be inserted in a Lookup Table by 
 * their ISBN-13 that allows for constant time insertion, deletion, and searching.
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * The driver class
 * @author Dillon Evans
 */
public class Main {
    static Scanner input = new Scanner(System.in);
    static DoubleHashedHashTable<String, Book> bookTable;
    static InputStream inStream;
    static String header;
    static BufferedReader inReader;
    static enum Operations {INSERT, SEARCH, DELETE}

    /**
     * Program Entry Point
     * @param args Command Line Parameters
     */
    public static void main(String[] args) 
    {
        initialize();
    }

    /**
     * Initializes the HashTable and the File
     */
    private static void initialize()
    {
        bookTable = new DoubleHashedHashTable<>();
        String filePath = "booksInfo.csv";
        try
        {
            Path file = Paths.get(filePath);
            inStream = new BufferedInputStream(Files.newInputStream(file));
            inReader = new BufferedReader(new InputStreamReader(inStream)); 
            header = inReader.readLine();
            promptUser();
        }
        catch(IOException e)
        {
            System.out.printf("File \"%s\" could not be found.", filePath);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Prompts the user for input until they specify they would like to quit.
     */
    public static void promptUser()
    {
        boolean quit = false;
        String decision;
        try
        {
            System.out.println("Library Menu");
           
            //Continuously prompt the user until they are finished
            while (!quit)
            {
                System.out.print("1. Add Book\n2. Search by ISBN-13\n3. Remove by ISBN-13\n4. Compare Hashing Methods\n5. Print Available Books\n6. Quit\n>>");
                decision = input.nextLine().trim();
                System.out.println();
    
                //Handles user input
                switch (decision)
                {
                    case "1":
                        insert();
                        break;
                    case "2":
                        find();
                        break;
                    case "3":
                        remove();
                        break;
                    case "4":
                        compare();
                        break;
                    case "5":
                        print(bookTable);
                        break;
                    case "6":
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid Operation!");
                        break;
                }   
            }
            input.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inserts the next book to be read from the file
     */
    public static void insert()
    {
        try
        {
            String contents, response = "";
            String[] tokenized;
            Book currentBook;
            while (!response.equals("n"))
            {   
                contents = inReader.readLine();
                if (contents == null)
                {
                    throw new Exception("There are no more books.");
                }
                else
                {
                    tokenized = contents.split(",");
                    currentBook = new Book(tokenized);
                    
                    bookTable.put(currentBook.getISBN13(), currentBook);

                    System.out.printf("Added Book: %s\n", currentBook);
                    System.out.print("Would you like to add another book? (y for yes and n for no): ");
                    response = input.nextLine().toLowerCase().trim();
                    
                    while (!response.equals("y") && !response.equals("n"))
                    {
                        System.out.print("Please enter 'y' or 'n': ");
                        response = input.nextLine().toLowerCase().trim();
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println();
    }

    /**
     * Searches for a book by the ISBN-13
     */
    public static void find()
    {
        String isbn;
        try
        {
            System.out.print("Enter the ISBN-13 of the book you are searching for >> ");
            isbn = input.nextLine().trim();
           
            while (isbn.length() != 13)
            {
                System.out.print("Enter a valid ISBN-13 >> ");
                isbn = input.nextLine().trim();
            }

            Book temp = bookTable.get(isbn);

            if (temp != null)
            {
                System.out.println(temp.toString());
            }
            else
            {
                System.out.printf("There is no book with ISBN-13 %s\n", isbn);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes the book with specified ISBN-13
     */
    public static void remove()
    {
        String isbn;
        try
        {
            System.out.print("Enter the ISBN-13 of the Book you wish to remove >> ");
            isbn = input.nextLine();

            while (isbn.length() != 13)
            {
                System.out.print("Enter a valid ISBN-13 >> ");
                isbn = input.nextLine().trim();
            }

            Book temp = bookTable.remove(isbn);
            
            if (temp != null)
            {
                System.out.printf("Removed Book \"%s\"\n", temp.getTitle());
            }
            else
            {
                System.out.printf("There is no book with ISBN-13 %s\n", isbn);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Compares the time for insertion, searching, and deletion using the different hashing methods.
     */    
    public static void compare()
    {
        int max = 1500;
        int partitions = max / 100;
        Dictionary<String, Book> testDoubleHashed = new DoubleHashedHashTable<>();
        Dictionary<String, Book> testProbeTable = new ProbedHashTable<>();
        long[] doubleHashedTimeEntries = new long[partitions], probedTimeEntries = new long[partitions];
       
        for (Operations op : Operations.values())
        {
            System.out.printf("\t\t\t\t%s\n",op.toString());
            measureHashingMethod(testDoubleHashed,doubleHashedTimeEntries, op);
            measureHashingMethod(testProbeTable,probedTimeEntries, op);
            for (int i = 0; i < partitions; i++)
            {
                System.out.printf("%5d Entries --> Double Hashing: %5d ms, Linear Probing: %5d ms\n", (i+1) * 100,doubleHashedTimeEntries[i], probedTimeEntries[i]);
            }
            System.out.println();
        }
    }

    /**
     * Measures the time it takes for the HashTable to perform the specified operation
     * @param table The HashTable to use
     * @param timeEntries The array used to store operation times
     * @param op The operation to perform
     */
    private static void measureHashingMethod(Dictionary<String, Book> table, long[] timeEntries, Operations op)
    {
  
        BufferedInputStream tempInstream;
        BufferedReader tempInreader;
        String filePath = "booksInfo.csv", contents = " ";
        String[] tokenized;
        Book temp;
        long start = 0, elapsed = 0, current = 0, parsingTime, parsingStart, parsingStop;
        int index = 0;

        try
        {
            Path file = Paths.get(filePath);
            tempInstream = new BufferedInputStream(Files.newInputStream(file));
            tempInreader = new BufferedReader(new InputStreamReader(tempInstream)); 
            header = tempInreader.readLine();
            start = System.currentTimeMillis();
			
            for (int i = 1; i <= 1500; i++)
            {
                //Time to parse the file
                parsingStart = System.currentTimeMillis();
                contents = tempInreader.readLine();
                tokenized = contents.split(",");
                temp = new Book(tokenized);
                parsingStop = System.currentTimeMillis();
                parsingTime = parsingStop - parsingStart;

                switch (op)
                {
                    case INSERT:
						table.put(temp.getISBN13(), temp);
						break;
                    case SEARCH:
                        table.get(temp.getISBN13());
                        break;
                    case DELETE:
						table.remove(temp.getISBN13());
						break;
					default:
						break;
                }
                if (i % 100 == 0)
                {
                    current = System.currentTimeMillis();
                    elapsed = current - parsingTime - start;
                    timeEntries[index++] = elapsed;
                }
            }
        }
        catch(IOException e)
        {
            System.out.printf("File \"%s\" could not be found.", filePath);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }   
    }

    /**
     * Prints all available books in the table
     * @param table The table to print from
     */
    public static void print(Dictionary<String, Book> table)
    {
        for (String ISBN : table.keySet())
        {
            System.out.println(table.get(ISBN));
        }
    }
}