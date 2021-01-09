/**
 * Represents a task to be executed by the CPU.
 * @author Dillon Evans
 */
public class Process
{
    private String processName;
    private int burstTime, virtualRuntime;
    
    /**
     * Creates a new Process
     * @param processName The name of the Process
     * @param burstTime The burst time of the Process
     * @param virtualRuntime The virtual runtime of the Process
     */
    public Process(String processName, int burstTime, int virtualRuntime)
    {
        this.processName = processName;
        this.burstTime = burstTime;
        this.virtualRuntime = virtualRuntime;
    }    

    /**
     * Sets the burst time of the process
     * @param burstTime The burst time of the process
     */
    public void setBurstTime(int burstTime) {this.burstTime = burstTime;}

    /**
     * Sets the name of the process
     * @param processName The name of the process
     */
    public void setProcessName(String processName) {this.processName = processName;}

    /**
     * Sets the virtual runtime of the process
     * @param virtualRuntime the virtual runtime of the process
     */
    public void setVirtualRuntime(int virtualRuntime) {this.virtualRuntime = virtualRuntime;}

    /**
     * @return The burst time of the process
     */
    public int getBurstTime() {return burstTime;}

    /**
     * @return The name of the process
     */
    public String getProcessName() {return processName;}

    /**
     * @return The virtual runtime of the process
     */
    public int getVirtualRuntime() {return virtualRuntime;}

    @Override
    public String toString() {return processName;}
}
