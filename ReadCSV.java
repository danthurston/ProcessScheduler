package ProcessScheduler;

import java.io.*;
import java.util.ArrayList;
/******************************************************************************
 * class to import data from a CSV file and to store it in an ArrayList.
 ******************************************************************************/
public class ReadCSV{
    /* instance variables */
    private static String id;                                                  // id of current process
    private static String arrival;                                             // arrival time of current process
    private static int burst;                                                  // burst time of current process
    private static int pri;                                                    // priority of current process
    private static ArrayList<Process> processArray = new ArrayList<Process>(); // ArrayList to store all of the processes
    private static BufferedReader newCSV;                                      // variable to store the CSV file
    private static Process processBuffer;                                      // temporary process holder. Remade with each iteration and added to processArray.
    
    /********************************************************************************* 
     * method to import CSV, convert each row of the CSV into an individual Process
     * then store them all in an ArrayList and return it. It also prints the original
     * input to the terminal for comparing to result.
     *********************************************************************************/
    public static ArrayList<Process> importCSV() throws Exception{
        processArray.clear();                                                  // ensure array is clear when reloading scheduler via UI button
        try{
            newCSV = new BufferedReader(new FileReader("test.csv"));           // import the CSV file
        }catch(Exception e){
            System.out.println("CSV file could not be found");                 // display error message if csv file cannot be located
        }
        System.out.println("Original Input: ");
        String infoRow = newCSV.readLine();                                    // read first line of CSV file
        while (infoRow != null){                                               // loop until a null/empty row of the CSV file is found
            String[] dataArray = infoRow.split(",");                           // split CSV file by the comma separating the values and place each item into an array
            for(String item:dataArray){                                        // loop over each value of the current CSV line stored in the array
                System.out.print(item + "\t");                                 // print original input in the CSV to terminal for comparative purposes
            }
            if(!dataArray[0].equals("ID")){                                    // skip the title row of the CSV
                /* store each piece of data in its respective variable */
                id      = dataArray[0];                                        
                arrival = dataArray[1];
                burst   = Integer.parseInt(dataArray[2]);
                pri     = Integer.parseInt(dataArray[3]);
                
                processBuffer = new Process(id,arrival,burst, pri);            // create process with data gathered from CSV                   
                processArray.add(processBuffer);                               // add process to processArray
            }           
            System.out.println();                                              // print new line in terminal after each process
            infoRow = newCSV.readLine();                                       // get next line of CSV
        }
        newCSV.close();                                                        // close CSV file
        System.out.println();                                                  // print new line after printing all processes to terminal
        return processArray;                                                   // return the filled ArrayList
    }
}