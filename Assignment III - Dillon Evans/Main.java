/**
 * Author: Dillon Evans
 * Email: <dillon.e.evans@okstate.edu>
 * Date: October 19th, 2020
 * Program Description: This program allows for processes to be scheduled based on their 
 * priority. The user can add processes from the provided file until they quit the program or
 * all the processes have been handled. The user can choose whether to kill a process (thus removing it from the heap)
 * or alter its priority thus changing when the task is executed.
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
 */
public class Main {
    static Scanner input = new Scanner(System.in);
    static PriorityQueue<Integer, Process> processQueue;
    static InputStream inStream;
    static String header;
    static BufferedReader inReader;

    /**
     * Program Entry Point
     * @param args Command Line Parameters
     */
    public static void main(String[] args) 
    {
        initialize();
    }

    /**
     * Initializes the Priority Queue and the File
     */
    private static void initialize()
    {
        processQueue = new MaxPriorityQueue<>();
        String filePath = "processesInformation.txt";
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
            System.out.println("Process Menu");
           
            //Continuously prompt the user until they are finished
            while (!quit)
            {
                System.out.print("1. Add Process\n2. Max\n3. Remove Max\n4. Update\n5. Is Empty?\n6. Is Full?\n7. Quit\n>>");
                decision = input.nextLine().trim();
                System.out.println();
    
                //Handles user input
                switch (decision)
                {
                    case "1":
                        insert();
                        break;
                    case "2":
                        max();
                        break;
                    case "3":
                        removeMax();
                        break;
                    case "4":
                        update();
                        break;
                    case "5":
                        isEmpty();
                        break;
                    case "6":
                        isFull();
                        break;
                    case "7":
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
     * Displays whether or not the queue is full
     */
    private static void isFull() {
        System.out.println("No. The Process Manager grows dynamically");
    }

    /**
     * Displays whether or not the queue is empty
     */
    private static void isEmpty() 
    {
        System.out.printf("The Queue is %s\n", processQueue.isEmpty() ? "empty." : "not empty.");
    }
    
    /**
     * Inserts the next process to be read from the file
     */
    public static void insert()
    {
        try
        {
            String contents, response = "", truncatedInput;
            String[] tokenized;
            Process currentProcess;
            while (!response.equals("n"))
            {   
                contents = inReader.readLine();
                if (contents == null)
                {
                    throw new Exception("There are no more processes.");
                }
                else
                {
                    truncatedInput = contents.trim().replaceAll("  +", "\t"); //Regex for replacing whitespace with frequency > 1
                    tokenized = truncatedInput.split("\t");
                    currentProcess = new Process(tokenized);
                    processQueue.insert(currentProcess.getPriority(), currentProcess);

                    System.out.printf("Added Process: \"%s\"\n", currentProcess.getName());
                    System.out.print("Would you like to add another process? (y for yes and n for no): ");
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
     * Indicates the process to be executed next.
     */
    private static void max() 
    {
        try
        {
            if (processQueue.isEmpty())
            {
                throw new Exception("Error: There are no processes");
            }
            else
            {
                System.out.printf("Top Priority Process:\n%s\n", processQueue.max().getValue());
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes the maximum element from the process manager
     */
    private static void removeMax() 
    {
        try
        {
            if (processQueue.isEmpty())
            {
                throw new Exception("Error: There are no processes");
            }
            else
            {
                System.out.printf("Killing Process \"%s\"\n\n", processQueue.max().getValue().getName());
                processQueue.removeMax();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the priority of a process
     */
    private static void update()
    {
        String response = "", numString;
        int oldPriority = 0, newPriority = 0;
        Process p;

        try
        {
            while (!response.equals("n"))
            {   
                if (processQueue.isEmpty())
                {
                    throw new Exception("There are no more processes to update");
                }

                p = processQueue.max().getValue();
                oldPriority = p.getPriority();
                
                
                System.out.printf("Enter the new priority (between 0 and 31) for \"%s\" >> ", p.getName());
                numString = input.nextLine();
                newPriority = tryParseInt(numString);

                while (newPriority < 0 || newPriority > 31)
                {
                    System.out.print("The priority must be between 0 and 31 >> ");
                    numString = input.nextLine();
                    newPriority = tryParseInt(numString);
                }
                
                p.setPriority(newPriority);
                processQueue.update(oldPriority, newPriority);
                
                System.out.print("Would you like to update another process? (y for yes and n for no): ");
                response = input.nextLine().toLowerCase().trim();
    
                while (!response.equals("y") && !response.equals("n"))
                {
                    System.out.print("Please enter 'y' or 'n': ");
                    response = input.nextLine().toLowerCase().trim();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns true if the input is an integer
     * @param s the input
     * @return true if the input is an integer
     */
    private static boolean isInteger(String s)
    {
        boolean isNumeric = true;
        try
        {
            Integer.parseInt(s);
            return isNumeric;
        }
        catch (Exception e)
        {
            isNumeric = false;
        }
        return isNumeric;
    }

    /**
     * Returns the parsed int if it is valid, otherwise prompts the user until a 
     * valid integer is entered.
     * @param s The string to parse
     * @return the parsed integer
     */
    private static int tryParseInt(String s)
    {
        int parsed = 0;
        String userInput = s;

        while (!isInteger(userInput))
        {
            System.out.print("Please enter an integer value >> ");
            userInput = input.nextLine();
        }
        parsed = Integer.parseInt(userInput);
        return parsed;
    }
}
