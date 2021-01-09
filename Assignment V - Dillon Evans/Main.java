/**
 * Author: Dillon Evans
 * Email: <dillon.e.evans@okstate.edu>
 * Date: November 16th, 2020
 * Program Description: This program simulates an Operating System's process scheduler by
 * implementing an AVL tree or a Red-Black Tree. Processes are read in from a file, 
 * and executed based on the minimum virtual runtime. The minimum process will be executed until there 
 * are no more processes left in the tree to execute.
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The driver class
 * @author Dillon Evans
 */
public class Main {
   
    /**
     * Rrogram Entry Point
     * @param args Console Line Arguments
     */
    public static void main(String[] args) 
    {
        TreeMap<Integer, Process> processMap = promptTreeMap();
        initializeTaskScheduler(processMap);
        runTaskScheduler(processMap);
    }

    /**
     * Prompts the user to decide on which self balancing tree they would like to implement 
     * @return a new TreeMap implementing the specified self balancing tree.
     */
    public static TreeMap<Integer, Process> promptTreeMap()
    {
        Scanner scan = new Scanner(System.in);
        String response;
        boolean isAVL;
        System.out.print("This Program Simulates an Operating System's Process Scheduler. ");
        System.out.println("By Default, This Simulation Utilizes an AVL Tree. \nHowever, Linux's \"Completely Fair Scheduler\" Utilizes a Red-Black Tree.");
        System.out.print("Enter 'AVL' to use an AVL Tree, or 'RB' to use a Red-Black Tree >> ");
        response = scan.nextLine().toUpperCase().trim(); 
        while (!response.equals("AVL") && !response.equals("RB"))
        {
            System.out.print("Please enter AVL or RB >> ");
            response = scan.nextLine().toUpperCase().trim();
        }
        isAVL = response.equals("AVL");
        scan.close();
        return isAVL ? new AVLTree<>() : new RedBlackTree<>();
    }

    /**
     * Initializes the TreeMap by inserting processes from the file
     * @param processMap The TreeMap to utilize in the scheduler
     */
    public static void initializeTaskScheduler(TreeMap<Integer,Process> processMap)
    {
        try
        {
            String filePath = "processesInformation.txt", input, truncatedInput, processName;
            String[] tokenized;
            int burstTime, virtualRuntime;
            Path file = Paths.get(filePath);
            InputStream inStream = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader inReader = new BufferedReader(new InputStreamReader(inStream)); 
            Process currentProcess;
            Pattern p = Pattern.compile("Process [A-Z],\\d+,\\d+");
            Matcher m;

            inReader.readLine();
            while ((input = inReader.readLine()) != null)
            {  
                truncatedInput = input.trim().replaceAll("\t+", ","); 
                m = p.matcher(truncatedInput);
                
                //Ensures that only valid data entries will be parsed
                if (m.matches()) 
                {
                    //Parse the input into tokens using commas. 
                    tokenized = truncatedInput.split(",");
                    processName = tokenized[0];
                    burstTime = Integer.parseInt(tokenized[1]);
                    virtualRuntime = Integer.parseInt(tokenized[2]);
                    currentProcess = new Process(processName, burstTime, virtualRuntime);
                    processMap.put(virtualRuntime, currentProcess);
                    System.out.printf("Added %s\n", processName);
                }
            }
            inReader.close();
            inStream.close();
        }
        catch (IOException e)
        {
            System.out.println("The specified file could not be found or read.");
        }
    }

    /**
     * Performs the Task Scheduling Simulation
     * @param processMap The TreeMap utilized for storing processes
     */
    public static void runTaskScheduler(TreeMap<Integer,Process> processMap)
    {
        Process min;

        /*Insert and remove processes until the tree is empty. 
        That is, until every process has been executed. */
        while(!processMap.isEmpty())
        {
            min = processMap.removeMinimum();
            System.out.printf("Executing %s, Burst Time: %d ms, Virtual Runtime: %d ms \n", min.getProcessName(), min.getBurstTime(), min.getVirtualRuntime());
            if (min.getBurstTime() > 0) 
            {
                min.setVirtualRuntime(min.getVirtualRuntime() + 1);
                min.setBurstTime(min.getBurstTime() - 1);
                processMap.put(min.getVirtualRuntime(), min);
            }
        } 
    }
}