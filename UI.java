package ProcessScheduler;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JToolBar;
/*****************************************************************************
 * This is the UI class. It handles all (or as much as possible) of the
 * front-end implementation. It inherits from the JPanel Java class.
 *****************************************************************************/
 @SuppressWarnings("serial")
public class UI extends JPanel{
    public static JTextArea textArea = new JTextArea();                   // main text area of UI used to display all data
    /**********************************************************************
     * creates the toolbar for the UI.
     **********************************************************************/
    public static JToolBar toolbar(JFrame frame){
        Dimension screenSize = 
                     Toolkit.getDefaultToolkit().getScreenSize();         // set dimension variable 'screenSize' as actual screensize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
               
        JToolBar toolbar = new JToolBar();                                // toolbar constructor
        toolbar.setRollover(true);                                        // turn on the rollover visual effect
        
        JButton reloadButt = new JButton("Reload");                       // construct 'Reload' JButton
        toolbar.add(reloadButt);                                          // add button to toolbar
        toolbar.addSeparator();                                   
        
        JButton keyButt = new JButton("UI Key");                          // construct 'UI Key' JButton
        toolbar.add(keyButt);                                             // add button to toolbar
        toolbar.addSeparator();                                   
        
        JLabel lowAgeMessage = new JLabel("Low Age Threshold: ");         // construct label indentifier
        toolbar.add(lowAgeMessage);                                       // add JLabel message to toolbar
        JTextField lowAgeThreshold = new JTextField("35");                // construct low age threshold text input area (with default value)
        toolbar.add(lowAgeThreshold);                                     // add input area to toolbar
        toolbar.addSeparator();toolbar.addSeparator();
        
        JLabel medAgeMessage = new JLabel("Med Age Threshold: ");         // construct label indentifier
        toolbar.add(medAgeMessage);                                       // add JLabel message to toolbar
        JTextField medAgeThreshold = new JTextField("30");                // construct medium age threshold text input area (with default value) 
        toolbar.add(medAgeThreshold);                                     // add input area to toolbar
        toolbar.addSeparator();toolbar.addSeparator();
        
        JLabel weightMessage = new JLabel("Weight (Burst) Threshold: ");  // construct label indentifier
        toolbar.add(weightMessage);                                       // add JLabel message to toolbar
        JTextField weightThreshold = new JTextField("11");                // construct weight threshold text input area (with default value)
        toolbar.add(weightThreshold);                                     // add input area to toolbar
        toolbar.addSeparator();toolbar.addSeparator();
        
        JComboBox<String> clearReload = 
              new JComboBox<String>(new String[]{"Clear","Don't Clear"});         // construct combo box (with options)
        toolbar.add(clearReload);                                         // add combo box to toolbar
        toolbar.addSeparator();toolbar.addSeparator();
        
        Container contentPane = frame.getContentPane();                   // get the contentpane of frame
        contentPane.add(toolbar, BorderLayout.NORTH);                     // add toolbar to contentPane and set position
        frame.setSize(screenSize.width, 150);                             // set frame size
        toolbar.setFloatable(false);                                      // fix toolbar position
        
        reloadButt.addActionListener( new ActionListener() {              // add action listener
        @Override                                                         // override other buttons(/methods with same name)
              public void actionPerformed(ActionEvent e){                 // when reload button is clicked:
                  System.out.println("\n\n\n");
                  int LAT = Integer.parseInt(lowAgeThreshold.getText());  // get the value in the UI low threshold TextField
                  int MAT = Integer.parseInt(medAgeThreshold.getText());  // get the value in the UI medium threshold TextField
                  int WT  = Integer.parseInt(weightThreshold.getText());  // get the value in the UI weight threshold TextField
                  String clear = clearReload.getSelectedItem().toString();// get the value in the UI 'clear/don't clear' TextField
                  if(clear.equals("Clear")){                              // if the value 'clear' is chosen by the user then..
                      UI.textArea.setText("");                            // clear the main UI TextArea
                  }else{
                      UI.textArea.append("\n\n\n");
                  }
                  Main.main(LAT, MAT, WT);                                // call the overloaded main class to restart the scheduling process with new parameters
        }
        });
        
        keyButt.addActionListener( new ActionListener() {                 // add action listener
        @Override                                                         // override other buttons(/methods with same name)
              public void actionPerformed(ActionEvent e){                 // when UI Key button is clicked:
                  /* display dialog box containing information regarding the toolbar functionality */
                  JOptionPane.showMessageDialog(frame,                    
                  "Low Age Threshold: Age processes will be moved from low to medium queue\nMed Age Threshold: Age processes will be moved from medium to high queue\nWeight (Burst) Threshold: Burst level processes will be moved up within its queue\nClear/Don't Clear: Choose whether to have the main UI text area cleared upon reload",
                  "User Interface Key",
                  JOptionPane.INFORMATION_MESSAGE);
        }
        });
        return toolbar;                                                   // return the toolbar to main to be placed into frame
    }
    
    /************************************************************************************
     * UI constructor. This is the main UI text area where all of the data is printed.
     * It is essentially a text area, housed in a scroll pane (to enable scrolling).
     * This object is then added to the UI frame by the main class method that called it.
     ************************************************************************************/
    public UI() {                               
        Dimension dims = new Dimension(850, 650);                        // instantize dimension for text area
        JScrollPane sp = new JScrollPane(textArea);                      // create scrollpane and nest text area within to enable scrolling 
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setEditable(false);                                     // make text area for printing only (not user editable)
        sp.setPreferredSize(dims);                                       // set dimension for text area
        sp.setMinimumSize(dims);                                         // set minimum dimension for tiles 
        textArea.setFont(new Font("monospaced", Font.PLAIN, 14));        // set font for text area output as monospace to allow perfect alignment       
        add(sp);                                                         // add the scrollpane to the UI object
        }        
    }