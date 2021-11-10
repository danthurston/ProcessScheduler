package ProcessScheduler;

import java.util.PriorityQueue;
import java.util.ArrayList;
public class AssignQueue{
    private PriorityQueue<Process> highQueue =                                       // high priority queue constructor
              new PriorityQueue<Process>(1, new PriorityComparator());             
    private PriorityQueue<Process> medQueue =                                        // medium priority queue constructor
              new PriorityQueue<Process>(1, new PriorityComparator());              
    private PriorityQueue<Process> lowQueue =                                        // low priority queue constructor
              new PriorityQueue<Process>(1, new PriorityComparator());
              
    private ArrayList<Process> arrProcesses = new ArrayList<Process>();              // ArrayList containing all of the unsorted Process objects 
    
    /*****************************************************************************
     * AssignQueue object constructor. Stores received array to instance variable
     * ArrayList arrProcesses, sorts the processes into its respective queues then
     * sends the queues to the scheduler to be sorted.
     *****************************************************************************/
    public AssignQueue(ArrayList<Process> processArray){
        arrProcesses = processArray;                                                 // store array
        iterateAssign();                                                             // sort all processes into respective queues
        new Scheduler(lowQueue, medQueue, highQueue);       						 // send all three queues to the Scheduler
    }
    
    /***************************************************************************
     * overloaded AssignQueue object constructor. Used when the reload button is
     * activated and includes the parameters from the UI selected by the user.
     * All queues, arrays and relevant counter variables are reset.
     ***************************************************************************/
    public AssignQueue(ArrayList<Process> processArray, int LT, int MT, int WT){
        /* clear all queues, arrays and 'weightMoves' */
        lowQueue.clear();
        medQueue.clear();
        highQueue.clear();
        arrProcesses.clear();
        PriorityComparator.resetWeightMoves();
        
        PriorityComparator.setWeight(WT);                                             // set weight cutoff to user chosen integer
        arrProcesses = processArray;                                                  // store array
        iterateAssign();                                                              // sort all processes into respective queues
        new Scheduler(lowQueue, medQueue, highQueue, LT, MT); 					      // send all three queues and user chosen thresholds to Scheduler
    }
    
    /*********************************************************************************
     * method containing a for/each loop that iterates through every process and
     * adds it to one of the three instance variable queues depending on its priority.
     *********************************************************************************/
    public void iterateAssign(){
        for(Process iteration : arrProcesses){
            if(iteration != null){                                                    // check for null
                if(iteration.getPriority()       == 1){                               // if process priority is 1 then..
                    highQueue.add(iteration);                                         // add to high queue
                }else if(iteration.getPriority() == 2){                               // if process priority is 2 then..
                    medQueue.add(iteration);                                          // add to medium queue
                }else if(iteration.getPriority() == 3){                               // if process priority is 3 then..
                    lowQueue.add(iteration);                                          // add to low queue
                }
            }
        }
    }
}