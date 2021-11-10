package ProcessScheduler;

import java.util.*;
class PriorityComparator implements Comparator<Process>{      
private static int weightThreshold = 11;                             // cutoff burst/weight level to move process up the priority queue
private static int weightMoves     = 0;                              // number of processes moved due to the above circumstance
   /*************************************************************************
    * comparator method, used to sort each priority queue into order based on
    * their burst. As a solution to the starvation of longer processes it
    * also moves processes higher up the queue if their burst is above a
    * certain level.
    *************************************************************************/
   public int compare(Process pA, Process pB) {
      if(pA.getBurst() > pB.getBurst()){                             // if old
          return 1;                                                  // move process up queue
      }else if(pB.getWeight() > weightThreshold){                    // if the burst of the process is over the cutoff then move up queue
          System.out.println("Process "+pB.getID()+" moved up queue due to high burst of "+ pB.getBurst());
          weightMoves++;                                             // increment weightMoves
          return 1;                                                  // move process up queue
      }else if(pA.getBurst() < pB.getBurst()){
          return -1;                                                 // move process down queue
      }
      return 0; 
  }
  
  /**************************************************************************
   * method to return the number of processes moved due to high weight/burst. 
   **************************************************************************/
  public static int getWeightMoves(){
      return weightMoves;
  }
  
  /**************************************************************************
   * method to reset the number of processes moved due to high weight/burst. 
   **************************************************************************/
  public static void resetWeightMoves(){
      weightMoves = 0;
  }
  
  /**************************************************************************
   * method to set the burst cutoff level that results in processes being 
   * moved due to high weight/burst.
   **************************************************************************/
  public static void setWeight(int WT){
      weightThreshold = WT;
  }
}