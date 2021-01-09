/**
 * Represents a process in a computer system. 
 * @author Dillon Evans
 */
public class Process implements Comparable<Process>
{
   private final String[] header = {"HandleCount",  "Name", "OtherOperationCount", "OtherTransferCount", "PageFaults",  "PageFileUsage", "ParentProcessId", "Priority", "ProcessId",  "ThreadCount"};
   private int handleCount = 0, otherOpCount = 0, otherTransferCount = 0, pageFaults = 0, pageFileUsage = 0,parentProcessID = 0, priority = 0,  processID = 0, threadCount = 0;
   private String[] data;
   private String name;

   /**
    * Constructs a new Process with the specified data
    * @param data The data to use
    */
   public Process(String[] data)
   {
       try
       {
           handleCount = Integer.parseInt(data[0]);
           name = data[1];
           otherOpCount = Integer.parseInt(data[2]);
           otherTransferCount = Integer.parseInt(data[3]);
           pageFaults = Integer.parseInt(data[4]);
           pageFileUsage = Integer.parseInt(data[5]);
           parentProcessID = Integer.parseInt(data[6]);
           priority = Integer.parseInt(data[7]);
           processID = Integer.parseInt(data[8]);
           threadCount = Integer.parseInt(data[9]);
           this.data = data;
       }
       catch (Exception e)
       {
          System.out.println("Invalid Data Provided."); 
       }
   }

   @Override
   public int compareTo(Process o) {
       if (this.priority > o.priority) {return 1;}
       else if (this.priority < o.priority) {return -1;}
       else {return 0;}
   }

   /**
    * Returns the handle count
    * @return the handle count
    */
   public int getHandleCount() {
       return handleCount;
   }

   /**
    * Returns the name
    * @return the name
    */
   public String getName() {
       return name;
   }

   /**
    * Returns the other op count
    * @return the other op count
    */
   public int getOtherOpCount() {
       return otherOpCount;
   }

   /**
    * Returns the other transfer count
    * @return the other transfer count
    */
   public int getOtherTransferCount() {
       return otherTransferCount;
   }

   /**
    * Returns the number of page faults
    * @return the number of page faults
    */
   public int getPageFaults() {
       return pageFaults;
   }

   /**
    * Returns the page file usage 
    * @return the page file usage
    */
   public int getPageFileUsage() {
       return pageFileUsage;
   }

   /**
    * Returns the parent process ID
    * @return the parent process ID
    */
   public int getParentProcessID() {
       return parentProcessID;
   }

   /**
    * Returns the priority
    * @return the priority
    */
   public int getPriority() {
       return priority;
   }

   /**
    * Returns the process ID
    * @return the process ID
    */
   public int getProcessID() {
       return processID;
   }
   
   /**
    * Returns the thread count
    * @return the thread count
    */
   public int getThreadCount() {
       return threadCount;
   }
   
   @Override
   public String toString() 
   {
       StringBuilder builder = new StringBuilder();
       for (String s : header)
       {
           builder.append(String.format("%-20s ", s));
       }
       builder.append("\n");
       for (String s : data)
       {
           builder.append(String.format("%-20s ", s));
       }
       builder.append("\n");
       return builder.toString();
   }

   /**
    * Sets the priority of the Process
    * @param priority The new priority
    */
   public void setPriority(int priority) 
   {
       data[7] = String.valueOf(priority);
       this.priority = priority;
   }

   /**
    * Sets the handle count of the Process
    * @param handleCount The new handle count
    */
   public void setHandleCount(int handleCount) 
   {
       this.handleCount = handleCount;
   }

   /**
    * Sets the name of the Process
    * @param name The new name
    */
   public void setName(String name) {
       this.name = name;
   }

   /**
    * Sets the other op count of the Process
    * @param otherOpCount The new other op count
    */
   public void setOtherOpCount(int otherOpCount) {
       this.otherOpCount = otherOpCount;
   }

   /**
    * Sets the other transfer count of the Process
    * @param otherTransferCount the new other transfer count
    */
   public void setOtherTransferCount(int otherTransferCount) {
       this.otherTransferCount = otherTransferCount;
   }

   /**
    * Sets the number of page faults of the Process
    * @param pageFaults The new number of pageFaults
    */
   public void setPageFaults(int pageFaults) {
       this.pageFaults = pageFaults;
   }

   /**
    * Sets the page file usage amount
    * @param pageFileUsage the new page file usage amount
    */
   public void setPageFileUsage(int pageFileUsage) {
       this.pageFileUsage = pageFileUsage;
   }

   /**
    * Sets the parent Process ID
    * @param parentProcessID the new parent Process ID
    */
   public void setParentProcessID(int parentProcessID) {
       this.parentProcessID = parentProcessID;
   }

   /**
    * Sets the ID of the Process
    * @param processID the new ID
    */
   public void setProcessID(int processID) {
       this.processID = processID;
   }

   /**
    * Sets the thread count of the Process
    * @param threadCount the new thread count
    */
   public void setThreadCount(int threadCount) {
       this.threadCount = threadCount;
   }
}
