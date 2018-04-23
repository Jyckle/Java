/*
 * this class is the main manager of all things contained within the board
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel
{
    //declares the variables to be used for the screen size
	private final static int N_BLOCKS = 15;
    private final static int BLOCK_SIZE = 24;
    private final static int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final static int BORDER_SIZE = 25;
       
    //variables affecting level and speed
    protected final static int MAX_LEVEL = 6;
    private final static int MAX_GHOSTS = 12;    
    
    //booleans to check for if ingame or dying
    private boolean inGame = false;
    private boolean dying = false;
    
    //to keep track of level and number of ghosts
    private int currLevel = 1;
    private int numGhosts = 5;
    private int pacsLeft, score;
    
    //keeps track of the ghosts
    private ArrayList<MoveableShape> ghost;
    
    private PacmanShape pacman;
    	    
    private Timer timer;
    
    private short[][] screenData;
    
    //contains all the data for the level
    private final short levelData[][] =
	    	{
	    		{ 19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22 },
	    		{ 21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
	    		{ 21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
	    		{ 21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20 },
	    		{ 17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20 },
	    		{ 17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20 },
	    		{ 25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21 },
	    		{ 1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21 },
	    		{ 1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21 },
	    		{ 1, 17, 32, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21 },
	    		{ 1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21 },
	    		{ 1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21 },
	    		{ 1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21 },
	    		{ 1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20 },
	    		{ 9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28 }

	    	};
    private final short level2Data[][] =
    	{
    			
	    		{ 19, 19, 26, 18, 18, 26, 18, 26, 26, 26, 26, 26, 18, 18, 23 },
	    		{ 21, 21, 0 , 17, 20, 0 , 21, 0 , 0 , 0 , 0 , 0 , 17, 20, 21 },
	    		{ 21, 21, 0 , 17, 20, 0 , 17, 18, 22, 0 , 19, 18, 16, 20, 21 },
	    		{ 21, 21, 0 , 17, 20, 0 , 17, 16, 20, 0 , 17, 16, 16, 20, 21 },
	    		{ 21, 21, 0 , 17, 20, 0 , 17, 16, 20, 0 , 17, 16, 16, 20, 21 },
	    		{ 21, 21, 0 , 25, 28, 0 , 17, 16, 20, 0 , 17, 16, 16, 20, 21 },
	    		{ 21, 21, 0 , 0 , 0 , 0 , 17, 16, 20, 0 , 17, 16, 16, 20, 21 },
	    		{ 21, 21, 0 , 19, 22, 0 , 17, 16, 20, 0 , 17, 16, 16, 20, 21 },
	    		{ 21, 21, 0 , 17, 20, 0 , 17, 16, 20, 0 , 17, 16, 16, 20, 21 },
	    		{ 21, 21, 0 , 17, 20, 0 , 17, 16, 20, 0 , 17, 16, 16, 20, 21 },
	    		{ 21, 21, 0 , 17, 20, 0 , 17, 16, 20, 0 , 17, 16, 16, 20, 21 },
	    		{ 21, 21, 0 , 17, 20, 0 , 17, 24, 28, 0 , 25, 24, 16, 20, 21 },
	    		{ 21, 21, 0 , 17, 20, 0 , 21, 0 , 0 , 0 , 0 , 0 , 17 , 20, 21 },
	    		{ 21, 25, 26, 24, 24, 26, 24, 26, 26, 26, 26, 26, 24, 28, 21 },
	    		{ 25, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 28 }
	    	};
    
    //Symbolic constants for checking values
    private final static int YUMMY_BIT = 16;
    private final static int LEFT_WALL = 1;
    private final static int RIGHT_WALL = 4;
    private final static int TOP_WALL = 2;
    private final static int BOTTOM_WALL = 8;
    private final static int REMOVE_YUMMY_BIT = 15;
    private final static int POWER_BIT = 32;
    private final static int YUMMY_BITS_PRESENT = 48;
    
    
    //when power bit is eaten, scared becomes true
    private boolean scared = false;
    
    //calls all of the necessary methods for instantiating a Board
    public Board()
    {
        setMinimumSize (new Dimension (SCREEN_SIZE + BORDER_SIZE, SCREEN_SIZE + BORDER_SIZE));
        setPreferredSize (new Dimension (SCREEN_SIZE + BORDER_SIZE, SCREEN_SIZE + BORDER_SIZE));

        initVariables();
        initBoard();
        initGame();
    }
    
    //sets up the creation of the board
    private void initBoard()
    {
        addKeyListener (new PacKeyAdapter());
        setFocusable (true);
        setBackground (Color.black);
        setDoubleBuffered (true);        
    }
    
    //sets up the necessary variables
    private void initVariables()
    {
        screenData = new short[N_BLOCKS][N_BLOCKS];
        
        timer = new Timer (40, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();				
			}
        });
        timer.start();
    }
    
    //sets the starting values for the game and initiates a level
    private void initGame()
    {
        pacsLeft = 3;
        score = 0;
        numGhosts = 5;
        currLevel = 1;
        
        initLevel();
    }
    
    //transfers level data to the screen, continues with the level
    private void initLevel()
    {
        if (currLevel > 1) {
        	for (int r = 0; r < N_BLOCKS; r++)
                for (int c = 0; c < N_BLOCKS; c++)
                	screenData[r][c] = level2Data[r][c];
        }
        else {
        	for (int r = 0; r < N_BLOCKS; r++)
                for (int c = 0; c < N_BLOCKS; c++)
                	screenData[r][c] = levelData[r][c];
        }
    	

        continueLevel();
    }
    
    //adds the ghosts, and sets the original position of pacman
    private void continueLevel()
    {
    	ghost = new ArrayList<MoveableShape>();
    	
    	for (int i = 0; i < numGhosts; i++)
    		ghost.add (new GhostShape (screenData, currLevel, 4, 4, BLOCK_SIZE));

        dying = false;
        
        pacman = new PacmanShape(screenData, 7, 11, BLOCK_SIZE);
        
    }
    
    // runs all the game functions
    private void playGame (Graphics2D g2d)
    {
        if (dying)
            death();
        else
        {
            movePacman();
            drawPacman (g2d);
            moveGhosts (g2d);
            checkMaze ();
        }
    }
    
    // sets up the intro screen
    private void showIntroScreen (Graphics2D g2d)
    {
        g2d.setColor (new Color (0, 32, 48));
        g2d.fillRect (50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor (Color.white);
        g2d.drawRect (50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Press s to start.";
        Font small = new Font ("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics (small);

        g2d.setColor (Color.white);
        g2d.setFont (small);
        g2d.drawString (s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }
    
    //draws the score in the bottom right corner of the frame
    private void drawScore (Graphics2D g)
    {
    	short ch = screenData[pacman.getY()/BLOCK_SIZE][pacman.getX()/BLOCK_SIZE];
    	if (( ch & YUMMY_BIT) != 0)
        {
        	screenData[pacman.getY()/BLOCK_SIZE][pacman.getX()/BLOCK_SIZE] = (short) (ch & REMOVE_YUMMY_BIT);
            score++;
        }
    	else if ((ch & POWER_BIT) != 0) {
    		screenData[pacman.getY()/BLOCK_SIZE][pacman.getX()/BLOCK_SIZE] = (short) (ch & REMOVE_YUMMY_BIT);
            score++;
            scared=true;
    	}
    	
    	String s = "Score: " + score;
        Font smallFont = new Font ("Helvetica", Font.BOLD, 14);
        g.setFont (smallFont);
        g.setColor (new Color (96, 128, 255));
        g.drawString (s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (int i = 0; i < pacsLeft; i++)
            g.drawImage (new ImageIcon("pacpix/PacMan3left.gif").getImage(), i * 28 + 8, SCREEN_SIZE + 1, this);
    }
    
    // check if all collectibles are gone
    private void checkMaze()
    {
        boolean finished = true;

        for (int r = 0; r < N_BLOCKS; r++)
        	for (int c = 0; c < N_BLOCKS; c++)
        	{
                if ((screenData[r][c] & YUMMY_BITS_PRESENT) != 0)
                    finished = false;
            }
        		
        if (finished)
        {
            score += 50;

            if (numGhosts < MAX_GHOSTS)
                numGhosts++;

            if (currLevel < MAX_LEVEL)
                currLevel++;

            initLevel();
        }
    }
    
    //reduces number of lives, if none left, then end the game
    private void death()
    {
        pacsLeft--;

        if (pacsLeft == 0)
            inGame = false;

        continueLevel();
    }
    
    //move the ghosts and check for collision with pacman
    private void moveGhosts (Graphics2D g2d)
    {
        for (int i = 0; i < numGhosts; i++)
        {
        	ghost.get(i).setScared(scared);
        	
        	ghost.get(i).move();

        	ghost.get(i).draw (g2d);
            
            if (ghost.get(i).contains (pacman.getX(), pacman.getY()))
            {
                if (!scared)
                	dying = true;
                else {
                	score += 50;
            		ghost.remove(i);
            		numGhosts--;
                }
                	
            }
        }
    }
    
    //pacman's movement properties
    private void movePacman()
    {    	 
    	pacman.move();
    }
    
    //draw the various different directions of movement
    private void drawPacman (Graphics2D g2d)
    {
       pacman.draw(g2d);
    }
    
    
    //draw method for the screen
    private void drawMaze (Graphics2D g2d)
    {
        int r, c;
        Color dotColor = new Color (192, 192, 0);
        Color mazeColor = new Color (5, 100, 5);

        //walk through all blocks on the screen
        for (r = 0; r < SCREEN_SIZE; r += BLOCK_SIZE)
        {
            for (c = 0; c < SCREEN_SIZE; c += BLOCK_SIZE)
            {
            	int gr = r / BLOCK_SIZE;
            	int gc = c / BLOCK_SIZE;

                g2d.setColor (mazeColor);
                g2d.setStroke (new BasicStroke(2));

                if ((screenData[gr][gc] & LEFT_WALL) != 0)
                    g2d.drawLine (c, r, c, r + BLOCK_SIZE - 1);

                if ((screenData[gr][gc] & TOP_WALL) != 0)
                    g2d.drawLine (c, r, c + BLOCK_SIZE - 1, r);

                if ((screenData[gr][gc] & RIGHT_WALL) != 0)
                    g2d.drawLine (c + BLOCK_SIZE - 1, r, c + BLOCK_SIZE - 1,
                            r + BLOCK_SIZE - 1);

                if ((screenData[gr][gc] & BOTTOM_WALL) != 0)
                    g2d.drawLine (c, r + BLOCK_SIZE - 1, c + BLOCK_SIZE - 1,
                            r + BLOCK_SIZE - 1);

                if ((screenData[gr][gc] & YUMMY_BIT) != 0)
                { 
                    g2d.setColor (dotColor);
                    g2d.fillRect (c + 11, r + 11, 2, 2);
                }
                if ((screenData[gr][gc] & POWER_BIT) != 0)
                {
                	 g2d.setColor (dotColor);
                     g2d.fillRect (c + 11, r + 11, 5, 5);
                }
            }
        }
    }
        
    
    //draw all the components of the board
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor (Color.black);
        g2d.fillRect (0, 0, getWidth(), getHeight());

        drawMaze (g2d);
        drawScore (g2d);
        pacman.doAnim();

        if (inGame)
            playGame (g2d);
        else
            showIntroScreen (g2d);
    }
    
    //take input from various keyEvents
    protected class PacKeyAdapter extends KeyAdapter
    {
        public void keyPressed (KeyEvent e)
        {
            int key = e.getKeyCode();

            if (inGame)
            {
                if (key == KeyEvent.VK_LEFT)
                {
                    pacman.updateReq(-1, 0);
                }
                else if (key == KeyEvent.VK_RIGHT)
                {
                    pacman.updateReq(1, 0);
                }
                else if (key == KeyEvent.VK_UP)
                {
                    pacman.updateReq(0, -1);
                }
                else if (key == KeyEvent.VK_DOWN)
                {
                	pacman.updateReq(0, 1);
                }
                else if (key == KeyEvent.VK_ESCAPE && timer.isRunning())
                {
                    inGame = false;
                }
                else if (key == KeyEvent.VK_PAUSE)
                {
                    if (timer.isRunning())
                        timer.stop();
                    else
                        timer.start();
                }
            }
            else if (key == 's' || key == 'S')
            {
            	inGame = true;
            	initGame();
            }
        }

        public void keyReleased (KeyEvent e)
        {
            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN)
            {
            	pacman.updateReq(0, 0);
            }
        }
    }
}
