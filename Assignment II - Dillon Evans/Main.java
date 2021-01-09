/**
 * Author: Dillon Evans
 * Email: <dillon.e.evans@okstate.edu>
 * Date: September 28th, 2020
 * Program Description: This program allows for customers to be served
 * based on their priority. That is, despite the order they arrive in, 
 * the customer with the lowest ID number is the first to be served and then
 * the next lowest is served until every customer has been served.
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * The driver class
 */
public class Main
{
    static Scanner input = new Scanner(System.in);
    static CustomerQueue customerQueue;
    static InputStream inStream;
    static BufferedReader inReader;
    static boolean hasInitialized = false, usesGrowthMethod = false;

    /**
     * Program entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) 
    {
        promptUser();
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
            System.out.println("Welcome to the Secretary Of State's Office! Enter one of the numbers below to perform the action specified.");
           
            //Continuously prompt the user until they are finished
            while (!quit)
            {
                System.out.print("1. Enqueue\n2. Dequeue\n3. Initialize\n4. First\n5. Size\n6. Is Empty?\n7. Is Full?\n8. Quit\n>>");
                decision = input.nextLine().trim();
                System.out.println();
    
                //Handles user input
                switch (decision)
                {
                    case "1":
                        enqueue();
                        break;
                    case "2":
                        dequeue();
                        break;
                    case "3":
                        initialize();
                        break;
                    case "4":
                        first();
                        break;
                    case "5":
                        size();
                        break;
                    case "6":
                        isEmpty();
                        break;
                    case "7":
                        isFull();
                        break;
                    case "8":
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
     * Adds the next customer to the waiting list
     */
    public static void enqueue()
    {
        try
        {
            String contents, response = "";
            String[] tokenized;
            if (!hasInitialized)
            {
                throw new Exception("Error: The Queue Has Not Been Initialized.");
            }
            else
            {
                while (!response.equals("n"))
                {   
                    if (customerQueue.getSize() != 80)
                    {
                        contents = inReader.readLine();
                        if (contents == null)
                        {
                            throw new Exception("There are no more customers requesting service.");
                        }
                        else
                        {
                            tokenized = contents.split("\t");
                            customerQueue.enqueue(new Customer(Integer.valueOf(tokenized[0]), tokenized[1], tokenized[2], tokenized[3]));
                            System.out.printf("%s, ID: %s has been added to the waiting list.\n", tokenized[2], tokenized[0]);
                            System.out.print("Do you want to enqueue more customers? (y for yes and n for no): ");
                            response = input.nextLine().toLowerCase().trim();
                            while (!response.equals("y") && !response.equals("n"))
                            {
                                System.out.print("Please enter 'y' or 'n': ");
                                response = input.nextLine().toLowerCase().trim();
                            }
                        }
                    }
                    else
                    {
                        throw new QueueOverflowException();
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
     * Serves the customer at the top of the waiting list
     */
    public static void dequeue()
    {
        String response = "";
        try
        {
            if (!hasInitialized)
            {
                throw new Exception("Error: The Queue Has Not Been Initialized.");
            }
            else
            {
                while (!response.equals("n"))
                {
                    if (!customerQueue.isEmpty())
                    {
                        System.out.println("Servicing: ");
                        System.out.println(customerQueue.dequeue(true));
                        System.out.print("Do you want to serve more customers? (y for yes and n for no): ");
                        response = input.nextLine().toLowerCase().trim();
                        while (!response.equals("y") && !response.equals("n"))
                        {
                            System.out.print("Please enter 'y' or 'n': ");
                            response = input.nextLine().toLowerCase().trim();
                        }
                    }
                    else
                    {
                        throw new QueueUnderflowException();
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
     * Initializes or reinitializes the queue.
     */
    public static void initialize()
    {
        String filePath = "myFile.txt", response;
        Path file = Paths.get(filePath);
        boolean isValidResponse = false;
        try
        {
            inStream = new BufferedInputStream(Files.newInputStream(file));
            inReader = new BufferedReader(new InputStreamReader(inStream));     
            inReader.readLine(); 
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        } 
        if(!hasInitialized)
        {
            while (!isValidResponse)
            {
                isValidResponse = true; //Assume the input is valid until proven otherwise
                System.out.print("Would you like the list to grow by a factor of two? If not, the list will grow by 10. (y for yes and n for no): ");
                response = input.nextLine().toLowerCase().trim();
                switch (response)
                {
                    case "y":
                        usesGrowthMethod = true;
                        break;
                    case "n":
                        usesGrowthMethod = false;
                        break;
                    default:
                        isValidResponse = false;
                        System.out.println("Please enter a valid response");
                }
            }
            hasInitialized = true;
        }
        customerQueue = new CustomerQueue(usesGrowthMethod);
        anyKeyPrompt();
    }

    /**
     * Prints the customer at the top of the waiting list
     */
    public static void first()
    {
        try
        {
            if (!hasInitialized)
            {
                throw new Exception("Error: The Queue Has Not Been Initialized.");
            }
            else
            {
                if (!customerQueue.isEmpty())
                {
                    System.out.println("Next Customer to be Served:");
                    System.out.println(customerQueue.first());
                }
                else
                {
                    throw new QueueUnderflowException();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        anyKeyPrompt();
    }

    /**
     * Prints the number of customers in the queue
     */
    public static void size()
    {
        try
        {
            if (!hasInitialized)
            {
                throw new Exception("Error: The Queue Has Not Been Initialized.");
            }
            else
            {
                System.out.printf("Number of Customers in the Queue: %d\n", customerQueue.getSize());
                
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        anyKeyPrompt();
    }

    /**
     * Prints whether or not the queue is empty
     */
    public static void isEmpty()
    {
        try
        {
            if (!hasInitialized)
            {
                throw new Exception("Error: The Queue Has Not Been Initialized.");
            }
            else
            {
                if (customerQueue.isEmpty())
                {
                    System.out.println("The Queue is Empty.");
                }
                else
                {
                    System.out.println("The Queue is Not Empty.");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        anyKeyPrompt();
    }

    /**
     * Prints whether or not the queue is full
     */
    public static void isFull()
    {
        try
        {
            if (!hasInitialized)
            {
                throw new Exception("Error: The Queue Has Not Been Initialized.");
            }
            else
            {
                if (customerQueue.isFull())
                {
                    System.out.println("The Queue is Full.");
                }
                else
                {
                    System.out.println("The Queue is Not Full.");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        anyKeyPrompt();
    }

    /**
     * Prompts the user to enter any key in order to return the main menu
     */
    public static void anyKeyPrompt()
    {
        System.out.print("Press any key to return to the main menu. ");
        input.nextLine();
    }
}
