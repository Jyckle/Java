/*
 * this class is the main manager of all things contained within the board
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel
{
    //declares the variables to be used for the screen size
	private final static int N_BLOCKS = 30;
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
    private int currLevel = 0;
    private int numGhosts = 5;
    private int pacsLeft, score;
    
    private int startX=7;
    private int startY=7;
    
    //keeps track of the ghosts
    private ArrayList<MoveableShape> ghost;
    
    private Character pacman;
    	    
    private Timer timer;
    
    private int[][] screenData;
    
    
    
    private int[][][] levels;
    
    //Symbolic constants for checking values
    public final static int YUMMY_BIT = 16;
    public final static int LEFT_WALL = 1;
    public final static int RIGHT_WALL = 4;
    public final static int TOP_WALL = 2;
    public final static int BOTTOM_WALL = 8;
    public final static int UP_STAIRS = 64;
    public final static int DOWN_STAIRS = 128;
    public final static int POWER_BIT = 32;
    public final static int YUMMY_BITS_PRESENT = 48;
    public final static int HIDDEN = 256;
    public final static int ROOM_TILE = 512;
    public final static int OUT_ROOM_TILE = 1024;
    public final static int CLEAR_ALL = 2147483647;
    public final static int REMOVE_YUMMY_BIT = CLEAR_ALL-YUMMY_BIT;
    public final static int UNHIDE = CLEAR_ALL-HIDDEN;
    
    
    //when power bit is eaten, scared becomes true
    private boolean scared = false;
    private final static int POWER_TIME = 5;
    private int scaredTimers = 0;
    
    //integer to check for movement
    private boolean moving[] = {false, false, false, false};
    
    //attacking
    private int damage= 0;
    private boolean attacking = false;
    private Image attackImage = new ImageIcon("char_pics/fire.png").getImage();

    
      
    
    //calls all of the necessary methods for instantiating a Board
    public Board(int[][][] levels)
    {
        this.levels= levels;
        
    	setMinimumSize (new Dimension (SCREEN_SIZE, SCREEN_SIZE + BORDER_SIZE));
        setPreferredSize (new Dimension (SCREEN_SIZE, SCREEN_SIZE + BORDER_SIZE));

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
        screenData = new int[N_BLOCKS][N_BLOCKS];
        
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
        currLevel = 0;
        moving[0] = false;
        moving[1] = false;
        moving[2] = false;
        moving[3] = false;
        
        initLevel();
    }
    
    //transfers level data to the screen, continues with the level
    private void initLevel()
    {
        for (int r = 0; r < N_BLOCKS; r++)
            for (int c = 0; c < N_BLOCKS; c++)
            	screenData[r][c] = levels[currLevel][r][c];

        continueLevel();
    }
    
    
    //adds the ghosts, and sets the original position of pacman
    private void continueLevel()
    {
    	ghost = new ArrayList<MoveableShape>();
    	
    	for (int i = 0; i < numGhosts; i++)
    		ghost.add (new GhostShape (screenData, currLevel, 4, 4, BLOCK_SIZE));

        dying = false;
        
        pacman = new Character(screenData, startX, startY, BLOCK_SIZE);
        
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
    	int ch = screenData[pacman.getY()/BLOCK_SIZE][pacman.getX()/BLOCK_SIZE];
    	if ((ch & POWER_BIT) != 0) {
    		screenData[pacman.getY()/BLOCK_SIZE][pacman.getX()/BLOCK_SIZE] = (int) (ch & REMOVE_YUMMY_BIT);
            score++;
            scared();
    	}
    	else if (( ch & YUMMY_BIT) != 0)
        {
        	screenData[pacman.getY()/BLOCK_SIZE][pacman.getX()/BLOCK_SIZE] = (int) (ch & REMOVE_YUMMY_BIT);
            score++;
        }
    	
    	String s = "Health: " + pacman.getHealth();
        Font smallFont = new Font ("Helvetica", Font.BOLD, 14);
        g.setFont (smallFont);
        g.setColor (new Color (96, 128, 255));
        g.drawString (s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (int i = 0; i < pacsLeft; i++)
            g.drawImage (new ImageIcon("env_pics/heart.png").getImage(), i * 28 + 8, SCREEN_SIZE + 1, 22, 22, this);
    }
    
    //timer for power bit
    public void scared()
    {
    	scared=true;
    	scaredTimers++;
    	new java.util.Timer().schedule( 
    	        new java.util.TimerTask() {
    	            public void run() {
    	                if(scaredTimers>1) {
    	                	cancel();
    	                	scaredTimers--;
    	                }	
    	                else {
    	                	scared=false;
    	                	scaredTimers--;
    	                }
    	                    
    	            }
    	        }, 
    	        POWER_TIME*1000);      
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
        
        int px = pacman.getX()/BLOCK_SIZE;
        int py = pacman.getY()/BLOCK_SIZE;
        int ch = screenData[py][px];
    	
        //unhide blocks as seen
        for (int x =-1; x<=1; x++) {
			for (int y =-1; y<=1; y++) {
				if((px +x)>= 0 && (px+x)<N_BLOCKS && (py +y)>= 0 && (py+y)<N_BLOCKS) {
					if ((screenData[py+y][px+x] & HIDDEN) !=0) {
						screenData[py+y][px+x]= screenData[py+y][px+x] & UNHIDE;
					}
						
				}
			}
		}
        
        //make sure character gets placed one position back from where they were on the last floor
        if ((ch & UP_STAIRS) != 0) {
    		if (currLevel <(levels.length-1))
    			currLevel++;
    		int dir = pacman.getDirection();
    		switch (dir) {
        	case 0: 
        		startX= px;
        		startY= py+1;
        		break;
        	case 1: 
        		startX= px;
        		startY= py-1;
        		break;
        	case 2: 
        		startX= px-1;
        		startY= py;
        		break;
        	case 3: 
        		startX= px+1;
        		startY= py;
        		break;
    		}
    		for (int r = 0; r < N_BLOCKS; r++)
                for (int c = 0; c < N_BLOCKS; c++)
                	levels[currLevel-1][r][c]= screenData[r][c];
    		initLevel();
    	}
        //make sure character gets placed one position back from where they were on the last floor
    	if ((ch & DOWN_STAIRS) != 0) {
    		if (currLevel >0)
    			currLevel--;
    		int dir = pacman.getDirection();
    		switch (dir) {
        	case 0: 
        		startX= px;
        		startY= py+1;
        		break;
        	case 1: 
        		startX= px;
        		startY= py-1;
        		break;
        	case 2: 
        		startX= px-1;
        		startY= py;
        		break;
        	case 3: 
        		startX= px+1;
        		startY= py;
        		break;
    		}
    		for (int r = 0; r < N_BLOCKS; r++)
                for (int c = 0; c < N_BLOCKS; c++)
                	levels[currLevel+1][r][c]= screenData[r][c];
    		initLevel();
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
        	ghost.get(i).move();

        	ghost.get(i).draw (g2d);
            
            if (ghost.get(i).contains (pacman.getX(), pacman.getY()))
            {
                if (!scared)
                {
                	if (pacman.removeHealth(100))
                		dying = true;
                	else {
                		ghost.remove(i);
                		ghost.add (new GhostShape (screenData, currLevel, 4, 4, BLOCK_SIZE));
                	}
                }
                else {
                	score += 50;
            		ghost.remove(i);
            		ghost.add (new GhostShape (screenData, currLevel, 4, 4, BLOCK_SIZE));
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
        Color mazeColor = new Color (104, 104, 104);

        //walk through all blocks on the screen
        for (r = 0; r < SCREEN_SIZE; r += BLOCK_SIZE)
        {
            for (c = 0; c < SCREEN_SIZE; c += BLOCK_SIZE)
            {
            	int gr = r / BLOCK_SIZE;
            	int gc = c / BLOCK_SIZE;

                g2d.setColor (mazeColor);
                g2d.setStroke (new BasicStroke(2));
                
                g2d.fillRect(c, r, getWidth(), getHeight());
                //g2d.drawImage (new ImageIcon("env_pics/rockFloor.jpeg").getImage(), c, r, 24, 24, this);
                
                if ((screenData[gr][gc] & ROOM_TILE) != 0)
                {
                	g2d.setColor(Color.GREEN);
                	g2d.fillRect(c, r, BLOCK_SIZE,BLOCK_SIZE );
                }
                
                if ((screenData[gr][gc] & UP_STAIRS) != 0) {
//                	g2d.setColor (Color.GRAY);
//                	g2d.fillRect (c, r, 24, 24);
                	g2d.drawImage (new ImageIcon("env_pics/ladder.png").getImage(), c, r, 24, 24, this);
                }

                if ((screenData[gr][gc] & DOWN_STAIRS) != 0) {
                	g2d.setColor (Color.BLACK);
                	g2d.fill(new Ellipse2D.Double(c,r,24,24));
                }
                
                if ((screenData[gr][gc] & LEFT_WALL) != 0)
                	g2d.drawImage (new ImageIcon("env_pics/vbrick.jpg").getImage(), c, r, this);
                	//g2d.drawLine (c, r, c, r + BLOCK_SIZE - 1);

                if ((screenData[gr][gc] & TOP_WALL) != 0)
                	g2d.drawImage (new ImageIcon("env_pics/hbrick.jpg").getImage(), c, r, this);
                	//g2d.drawLine (c, r, c + BLOCK_SIZE - 1, r);

                if ((screenData[gr][gc] & RIGHT_WALL) != 0)
                	g2d.drawImage (new ImageIcon("env_pics/vbrick.jpg").getImage(), c+BLOCK_SIZE-3, r, this);
                	//g2d.drawLine (c + BLOCK_SIZE - 1, r, c + BLOCK_SIZE - 1,
                      //      r + BLOCK_SIZE - 1);

                if ((screenData[gr][gc] & BOTTOM_WALL) != 0)
                	g2d.drawImage (new ImageIcon("env_pics/hbrick.jpg").getImage(), c, r+BLOCK_SIZE-3, this);
                	//g2d.drawLine (c, r + BLOCK_SIZE - 1, c + BLOCK_SIZE - 1,
                      //      r + BLOCK_SIZE - 1);

                if ((screenData[gr][gc] & YUMMY_BIT) != 0)
                { 
                    g2d.setColor (dotColor);
                    g2d.fillRect (c + 11, r + 11, 2, 2);
                }
                if ((screenData[gr][gc] & POWER_BIT) != 0)
                {
                	 g2d.setColor (Color.GREEN);
                	 g2d.fill(new Ellipse2D.Double(c+6,r+6,10,10));
                }
                //comment out this block to reveal entire map
//                if ((screenData[gr][gc] & HIDDEN) != 0)
//                {
//                	g2d.setColor(Color.BLACK);
//                	g2d.fillRect(c, r, BLOCK_SIZE,BLOCK_SIZE );
//                }
            }
        }
    }
        
    
    //draw all the components of the board
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor (Color.BLACK);
        g2d.fillRect (0, 0, getWidth(), getHeight());

        drawMaze (g2d);
        drawScore (g2d);
        attack(g2d);

        if (inGame)
            playGame (g2d);
        else
            showIntroScreen (g2d);
        
    }
    
    public void attack(Graphics g2d) {
    	damage = pacman.getDamage();
    	int dir = pacman.getDirection();
    	int dx =0;
    	int dy =0;
    	switch (dir) {
    	case 0: 
    		dy = -1; 
    		dx=0;
    		break;
    	case 1: 
    		dy = 1; 
    		dx=0;
    		break;
    	case 2: 
    		dx = 1; 
    		dy=0;
    		break;
    	case 3: 
    		dx = -1;
    		dy=0;
    		break;
    	//default: dx=0; dy=0;
    	}
    	if ((dy !=0 || dx!=0) && attacking) {
    		g2d.drawImage (attackImage, pacman.getX() + BLOCK_SIZE*dx, 
    				pacman.getY() + BLOCK_SIZE*dy, 22, 22, this);
    		
    		for (int i = 0; i < numGhosts; i++)
            {      	          
                if (ghost.get(i).contains (pacman.getX() + BLOCK_SIZE*dx, pacman.getY() + BLOCK_SIZE*dy))
                {
                    ghost.remove(i);
                    ghost.add (new GhostShape (screenData, currLevel, 4, 4, BLOCK_SIZE));
                }   
            }
    	}
    	
    	
    	
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
                    moving[0]= true;
                }
                else if (key == KeyEvent.VK_RIGHT)
                {
                    pacman.updateReq(1, 0);
                    moving[1]= true;
                }
                else if (key == KeyEvent.VK_UP)
                {
                    pacman.updateReq(0, -1);
                    moving[2]= true;
                }
                else if (key == KeyEvent.VK_DOWN)
                {
                	pacman.updateReq(0, 1);
                	moving[3]= true;
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
                
                if (key == KeyEvent.VK_SPACE)
                {
                	 attacking=true;
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

            switch (key) {
            	case KeyEvent.VK_LEFT: moving[0]= false;
            	case KeyEvent.VK_RIGHT: moving[1]= false;
            	case KeyEvent.VK_UP: moving[2]= false;
            	case KeyEvent.VK_DOWN: moving[3] = false;
            	case KeyEvent.VK_SPACE: attacking = false;
            }
            
           if (!moving[0] && !moving[1] && !moving[2] && !moving[3]) {
        	   pacman.updateReq(0, 0);
           }
        }
    }
}
