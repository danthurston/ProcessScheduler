package ProcessScheduler;

/*************************************************************
 * Process object class that creates instances of Processes. 
 *************************************************************/
public class Process{    
    /* instance variables */
    private int priority;                              // priority of process
    private String ID;                                 // ID of process
    private String arrivalTime;                        // arrival time of process
    private int burst;                                 // burst time of process
    private int age;                                   // age of process
    
    /************************************************************************
     * Process constructor. Sets instance variables.
     ************************************************************************/
    public Process(String newID, String arrTime, int burTime, int Pri){
        /* set instance variables for new Process */
        this.ID          = newID;
        this.arrivalTime = arrTime;
        this.burst       = burTime;
        this.priority    = Pri;
        this.age         = 0;
    }
    
    // getter methods to return process info of choice..
    public String getID(){                              // method to retrieve the ID of the current process.
        return this.ID;                                 // returns the ID of the current Process.
    }
    public int getPriority(){                           // method to retrieve the priority of the current Process.
        return this.priority;                           // returns the priority of the current Process.
    }
    public String getArrival(){                         // method to retrieve the ID of the current Process.
        return this.arrivalTime;                        // returns the ID of the current Process.
    }
    public int getBurst(){                              // method to retrieve the priority of the current Process.
        return this.burst;                              // returns the priority of the current Process.
    }
    public int getAge(){                                // method to retrieve the priority of the current Process.
        return this.age;                                // returns the priority of the current Process.
    }
    public int getWeight(){                             // method to retrieve the priority of the current Process.
        return this.burst;                              // returns the weight of the current Process.
    }    
    
    // setter methods for altering process info of choice..
    public void setPriority(int newPri){                // method to set priority of specified Process. sent var 'newPri'as the new priority.
        this.priority = newPri;                         // sets the specified Process priority as 'newPri'.
    }
    public void setAge(int newAge){                     // method to set priority of specified Process. sent var 'newPri'as the new priority.
        this.age = newAge;                              // sets the specified Process priority as 'newPri'.
    }
    
    public void printData(){
        /* print all process information to the UI */
        UI.textArea.append(" " + this.ID    + "\t");
        UI.textArea.append(this.arrivalTime + "\t");
        UI.textArea.append(this.burst       + "\t");
        UI.textArea.append(this.priority    + "\t\t");
    }
}