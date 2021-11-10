package ProcessScheduler;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.util.ArrayList;
/***********************************************************************************
 * the main class of the program. Begins the sequence of events that result in the
 * final program.
 ***********************************************************************************/
public class Main{
    /*******************************************************************************
     * initial method called to bein the running of the program. Calls methods to
     * get the process data, place into an array and then send to the AssignQueue
     * class to be placed into its priority queue.
     *******************************************************************************/
    public static void main(String[] args) {
       setUI();                                                // calls method to setup the user interface
       ArrayList<Process> finalArray = getData();              // fill array with data from CSV file
       new AssignQueue(finalArray);  						   // send array to have contents assigned to one of the three queues
    }
    
    /********************************************************************************
     * Overloaded main method. Used to restart the program and include the data
     * input by the user via the UI toolbar text fields. The UI is also not reset so
     * as to retain the original frame and just clear the text area (executed in UI
     * class).
     ********************************************************************************/
    public static void main(int lowThresh, int medThresh, int weightThresh){  
      ArrayList<Process> finalArray = getData();               // fill array with data from CSV file                     
      new AssignQueue(finalArray,    						   // send array to have contents assigned to one of the three queues
                          lowThresh, medThresh, weightThresh);
    }
    
    /********************************************************************************
     * method that compiles the user interface.
     ********************************************************************************/
    public static void setUI(){                         
        JFrame f = new JFrame("Process Scheduler");            // new frame to house the UI
        JToolBar tools = UI.toolbar(f);                        // get toolbar from 'UI' class
        UI ui = new UI();                                      // UI constructor
         
        f.add(tools,BorderLayout.PAGE_START);                  // add toolbar to frame
        f.add(ui);                                             // add UI (text area) to frame
        f.pack();                                              // pack frame components
        f.setLocationRelativeTo(null);                         // ensure position  
        f.setVisible(true);                                    // set frame visible
    }
    
    /*********************************************************************************
     * method that calls the ReadCSV class to import the process data held in the CSV.
     *********************************************************************************/
    public static ArrayList<Process> getData(){
        ArrayList<Process> arrayCSV = new ArrayList<Process>();// array to store the processes that have been imported via CSV
        try{
            arrayCSV = ReadCSV.importCSV();                    // fill array with data returned from the CSV importer class
        } catch (Exception e){
            System.out.print("Failure in readCSV class");      // if the importCSV method fails then an error is displayed
        }
        return arrayCSV;                                       // return the data-filled array
    }
}