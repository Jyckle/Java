/*
 This is the main class which runs the overall program
 This inherits from JFrame, instantiates a board, and runs the main program. 
 */
import java.awt.*;
import javax.swing.*;

public class SaveTheNarwhal extends JFrame {

    //Constructor class, calls the method initUI
	public SaveTheNarwhal() {
        
        initUI();
    }
    
	//Adds the board to the frame, sets the title and default close operation, and sets the location to the center of the screen
    private void initUI() {
        
    	DungeonGenerator d = new DungeonGenerator(8,30,24,25);
		int levels[][][]= d.getLevels();
    	
        add (new Board(levels));
        
        setTitle ("SaveTheNarwhall");
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo (null);
        setVisible (true);        
    }
    
    //this is the actual "main" clause which runs. 
    public static void main (String[] args) {
    	
    	//this adds the anonymous class Runnable to the event queue, with a method that creates an instance of PacMan
        EventQueue.invokeLater (new Runnable() {
        	public void run() {
        		SaveTheNarwhal ex = new SaveTheNarwhal();
        		ex.setVisible (true);
        	}
        });
    }
}

