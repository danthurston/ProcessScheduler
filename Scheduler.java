package ProcessScheduler;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Write a description of class Scheduler here.
 *
 * @author Dan Thurston
 * @version 1
 */
public class Scheduler{
    private static PriorityQueue<Process> highQueue =                    // high priority queue constructor
              new PriorityQueue<Process>(1, new PriorityComparator());             
    private static PriorityQueue<Process> medQueue =                     // medium priority queue constructor
              new PriorityQueue<Process>(1, new PriorityComparator());              
    private static PriorityQueue<Process> lowQueue =                     // low priority queue constructor
              new PriorityQueue<Process>(1, new PriorityComparator());
    private ArrayList<Process> ganttList = new ArrayList<Process>();    // ArrayList holding the processes in order of execution for display on the Gantt chart
    private LinkedList<Integer> turnList  = new LinkedList<Integer>();  // LinkedList holding the turnaround numbers in 
    private int turnaround;                                             // difference between arrival and completion time
    private int turnaroundAddr;                                         // addition of turnaround times for averaging
    private int numProcesses;                                           // process counter for average calculation
    private int waitTime;                                               // difference between turnaround and burst time.
    private int waitAddr;                                               // addition of wait times for averaging
    
    private int weightMoves = 0;                                        // number of processes moved due to weight
    private int medMoves    = 0;                                        // number of medium priority processes moved due to age 
    private int lowMoves    = 0;                                        // number of low priority processes moved due to age 
    
    private String completionTime;                                      // time of completion 
    private boolean executed = false;                                   // boolean holding whether the final statistics have been printed already
    private int medAgeThreshold = 30;                                   // change to alter the age to move up a queue 
    private int lowAgeThreshold = 35;                                   // change to alter the age to move up a queue
    public Scheduler(PriorityQueue<Process> LQ, PriorityQueue<Process> MQ, PriorityQueue<Process> HQ){
        /* pass received queues to their respective Scheduler queues */
        highQueue = HQ;
        medQueue  = MQ;
        lowQueue  = LQ;
        
        turnList.add(0);                                                // add 0 to the begginning of turnList for the Gantt Chart
        UI.textArea.append(" ID\tArrival \tBurst\tPriority\tCompletion\tWait\tTurnaround\tAge\n");
        Schedule();                                                     // call schedule method to begin process execution
    }
    
    /*******************************************************************
     * Overloaded constructor: Used when user alters thresholds via UI
     * and reloads the program (via UI reload button).
     *******************************************************************/
    public Scheduler(PriorityQueue<Process> LQ, PriorityQueue<Process> MQ, PriorityQueue<Process> HQ, int lowThresh, int medThresh){
        /* clear all lists and queues ready for next schedule*/
        ganttList.clear();
        turnList.clear();
        lowQueue.clear();
        medQueue.clear();
        highQueue.clear();
        
        /* pass received queues to their respective Scheduler queues */
        lowQueue  = LQ;
        medQueue  = MQ;
        highQueue = HQ;
        
        /* set thresholds */
        medAgeThreshold = medThresh;
        lowAgeThreshold = lowThresh;
        
        turnList.add(0);                                                // add 0 to the begginning of turnList for the Gantt Chart
        UI.textArea.append(" ID\tArrival \tBurst\tPriority\tCompletion\tWait\tTurnaround\tAge\n");
        Schedule();                                                     // call schedule method to begin process execution 
    }
    
    /********************************************************************************************
     * method that checks if each queue is empty and if not then sends it to be printed.
     * If all queues are empty then the final stats and gantt chart are printed.
     ********************************************************************************************/
    public void Schedule(){
        if(!highQueue.isEmpty()){                                       // check if high priority queue is empty. If so..
            executeQueue(highQueue);                                    // send highQueue to be executed
        }
        if(!medQueue.isEmpty()){                                        // check if medium priority queue is empty. If so..
            executeQueue(medQueue);                                     // send medQueue to be executed
        }
        if(!lowQueue.isEmpty()){                                        // check if low priority queue is empty. If so..
            executeQueue(lowQueue);                                     // send lowQueue to be executed
        }        
        if(!executed){                                                  // checks if the below information has been executed already
            printStats();                                               // call method to print statistics of schedule 
            printGantt();                                               // call method to print
            executed = true;                                            // set variable to true so above information is not executed more than once (due to multiple calls of this method)
        }
    }
    
    /*****************************************************************************
     * method to execute a specific queue. It removes each process from its queue,
     * calculates all relevant statistics, prints the processes data to the UI
     * and ages all processes.
     *****************************************************************************/
    public void executeQueue(PriorityQueue<Process> queueX){
        while(!queueX.isEmpty()){                                       // loop through the queue until it is empty
            Process current = queueX.poll();                            // get (and remove) first Process in queue
            ganttList.add(current);                                     // add Process to ArrayList for Gantt Chart
            numProcesses   += 1;                                        // increment Process counter
            waitTime        = turnaround;                               // set waitTime (wait is equal to turnaround prior to addition of current burst)
            waitAddr       += waitTime;                                 // add wait to waitAddr for average wait calculation
            turnaround     += current.getBurst();                       // set turnaround by adding current burst
            turnaroundAddr += turnaround;                               // add turnaround to turnaroundAddr for average turnaround calculation
            completionTime  = calculateCompletion(current);             // calculate process completion time
            turnList.add(turnaround);                                   // add turnaround integer to turnList LinkedList
            current.printData();                                        // print data of current process
            UI.textArea.append(completionTime + "\t" + waitTime + "\t" + turnaround + "\t\t" +  current.getAge() + "\n");
            ageProcesses(current.getBurst());                           // call method to age all processes
        }
    }
    
    /******************************************************************
     * The implementation of aging. Essentially the solution to the
     * issue of lower-priority process starvation and is also utilized
     * in the calculation of weight.
    ******************************************************************/
    public void ageProcesses(int ageBy){
        /* iterate through all processes in the high queue */
        for(Process proc : highQueue){
            proc.setAge(turnaround);                                   // set age to turnaround
        }
        /* iterate through all processes in the medium queue */
        for(Process proc : medQueue){
            proc.setAge(turnaround/2);                                 // set age to turnaround divided by 2
            if(proc.getAge() > medAgeThreshold){                       // if the age of the current process is above the threshold then..
                System.out.println("Process "+ proc.getID() +" moved from priority 2 to 1 due to high age of "+ proc.getAge());
                Process newProc = proc;                                // store current process as new Process object (newProc)
                newProc.setPriority(1);                                // change newProc priority from 2 to 1 
                medQueue.remove(proc);                                 // remove current process from medium queue
                highQueue.add(newProc);                                // add new Process (newProc) to high queue
                medMoves++;                                            // increment medium age move counter
                Schedule();                                            // call schedule method again to restart loop
            }
        }
        /* iterate through all processes in the low queue */
        for(Process proc : lowQueue){
            proc.setAge(turnaround/3);                                 // set age to turnaround divided by 3
  
            if(proc.getAge() > lowAgeThreshold){                       // if the age of the current process is above the threshold then..
                System.out.println("Process "+ proc.getID() +" moved from priority 3 to 2 due to high age of "+ proc.getAge());
                Process newProc = proc;                                // store current process as new Process object (newProc)
                newProc.setPriority(2);                                // change newProc priority from 3 to 2
                lowQueue.remove(proc);                                 // remove current process from low queue
                medQueue.add(newProc);                                 // add new Process (newProc) to medium queue
                lowMoves++;                                            // increment medium age move counter
                Schedule();                                            // call schedule method again to restart loop
            }
        }
    }
    
    /****************************************************************************
     * method to print the various statistics gathered throughout the running of 
     * the program.
     ****************************************************************************/
    public void printStats(){           
        int avgTurnaround = turnaroundAddr / numProcesses;            // calculate the average turnaround time
        int avgWait       = waitAddr       / numProcesses;            // calculate the average wait time
        weightMoves = PriorityComparator.getWeightMoves();            // get number of processes moved due to weight
        for(int x = 0; x <= 105; x++){                                 // print top horizontal line to contain stats
            UI.textArea.append("_");
        }
        /* print all statistics, with alignment */
        UI.textArea.append("\n Number of Processes    : "  + numProcesses);                  
        UI.textArea.append("\t\t\tMedium Priority Processes Moved Up: " + medMoves);
        UI.textArea.append("\n Average Turnaround Time: "  + avgTurnaround);
        UI.textArea.append("\t\t\tLow Priority Processes Moved Up   : " + lowMoves);
        UI.textArea.append("\n Average Waiting Time   : "  + avgWait);
        UI.textArea.append("\t\t\tProcesses Moved due to High Weight: " + weightMoves +"\n");
        for(int x = 0; x <= 105; x++){                                 // print bottom horizontal line to contain stats
            UI.textArea.append("¯");
        }
    }
    
    /***********************************************************************
     * method to calculate the completion time of each process.
     * The methodology used to calculate the completion time is essentially
     * to separate the arrival time into hh, mm, ss then add the turnaround
     * (ie. the time it takes to complete). This is followed by a series of
     * if statements to check if each number increment is over 60 and if so
     * to adjust the time accordingly.
     ***********************************************************************/
    public String calculateCompletion(Process x){
        String complete = "";                                          // instantize complete string to store completion time
        String arr = x.getArrival();                                   // get the current Process arrival time
        String[] arrivalArr = arr.split(":");                          // split the string into hh, mm, ss
        /* store each number in an integer variable */
        int hNum = Integer.parseInt(arrivalArr[0]);
        int mNum = Integer.parseInt(arrivalArr[1]);
        int sNum = Integer.parseInt(arrivalArr[2]);
        sNum    += turnaround;                                         // add turnaround to lowest number variable
        if(sNum < 60){                                                 // if sNum is less than 60 then no alteration needed so..
            complete  = hNum + ":" + mNum + ":" + sNum;                // concatenate numbers into string
        }else if(sNum >= 60){
            if(sNum >= 60 && sNum < 120){                              // if sNum is more than 60 but less than 120 then..
                sNum -= 60;                                            // minus 60
                mNum += 1;                                             // add 1 to mNum (to simulate the increment of a minute)
            }else if (sNum >= 120){                                    // if sNum is over 120 then..
                sNum -= 120;                                           // minus 120
                mNum += 2;                                             // add 2 to mNum
            }
            if(mNum < 60 && sNum < 10){                                // if mNum under 60 and sNum under 10
                complete = hNum + ":" + mNum + ":" + "0" + sNum;       // concatenate to string whilst adding a leading 0 to sNum
            }else if(mNum >= 60){                                      // if mNum is more than (or equal to) 60
                mNum -= 60;                                            // minus 60 from mNum
                hNum += 1;                                             // add 1 to hNum
                if(sNum < 10){                                         // if sNum is less than 10
                    complete = hNum +":"+ "0" + mNum +":"+ "0" + sNum; // concatenate to string whilst adding a leading 0 to mNum and sNum
                }else{
                    complete = hNum +":"+ "0" + mNum +":"+ sNum;       // concatenate to string whilst adding a leading 0 to mNum
                }
            }else{
                complete  = hNum + ":" + mNum + ":" + sNum;            // concatenate to string
            }
        }
        return complete;                                               // return completed string
    }
    
    /**********************************************************************
     * method to print a Gantt Chart based on the result of the scheduler.
     * the chart is formed by printing a space for each burst and labelling
     * with the processes ID and on the line below the turnaround time is 
     * printed.
     **********************************************************************/
    public void printGantt(){
        int lines = turnaround + (numProcesses * 4) + 3;              // calculate number of lines needed to contain chart
        UI.textArea.append("\n\n Gantt Chart:\n");
        for(int x = 0; x <= lines; x++){                              // print bottom horizontal line to contain chart
            UI.textArea.append("_");
        }
        UI.textArea.append("\n");
        for(Process gProc : ganttList){                               // loop through all processes to print top row of chart
            String gID = gProc.getID();                               // get the ID of the current Process
            int x      = gProc.getBurst();                            // get the burst of the current Process as 'x' (to be used as the number of spaces printed)
            x  -= 2;                                                  // minus 2 from 'x' to make room for the process ID
            UI.textArea.append("|");                                  // print process separator
            for(int i = 0; i <= (x / 2); i++){                        // print first half of spaces (representing burst)
                UI.textArea.append(" ");
            }
            UI.textArea.append("P" + gID);                            // print Process ID
            for(int i = 0; i <= (x / 2); i++){                        // print second half of spaces
                UI.textArea.append(" ");
            }
        }
        UI.textArea.append("|");
        UI.textArea.append("\n");
        for(Process gProc : ganttList){                               // loop through all processes to print bottom row of chart
            int ganttTurn = turnList.poll();                          // get (and remove) first turnaround time from turnList
            int x         = gProc.getBurst();                         // get burst time of first process
            x  -= 2;                                                  // minus 2 from 'x' to align with first line of chart
            UI.textArea.append("" + ganttTurn);                       // print turnaround
            for(int i = 0; i <= x; i++){                              // print number of spaces relative to burst
                UI.textArea.append(" ");
            }
            /* if statements to fix alignment issues when turnaround is varying digits long */
            if(ganttTurn < 10){
                UI.textArea.append("    ");
            }else if(ganttTurn < 100){
                UI.textArea.append("   ");
            }else{
                UI.textArea.append("  ");
            }
        }
        int ganttTurn = turnList.poll();                              // get final turnaround time
        UI.textArea.append("" + ganttTurn);                           // print final turnaround time
        UI.textArea.append("\n");
        for(int x = 0; x <= lines; x++){                              // print bottom horizontal line to contain chart
            UI.textArea.append("¯");
        }
        }
}