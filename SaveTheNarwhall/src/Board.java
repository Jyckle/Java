/*
 * this class is the main manager of all things contained within the board
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel
{
    //declares the variables to be used for the screen size
	public final static int N_BLOCKS = 30;
    public final static int BLOCK_SIZE = 24;
    private final static int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final static int BORDER_SIZE = 25;   
    
    //booleans to check for if ingame or dying
    private boolean inGame = false;
    private boolean winGame = false;
    private boolean dying = false;
    
    private int spiderTime=0;
    private int spiderAttackTime=40;
    private int dragonTime=0;
    private int dragonAttackTime=2;
    private int shootTime=0;
    private int shootTimeLimit=10;
    
    //to keep track of level and number of enemies
    private int currLevel = 0;
    private int numEnemies= 0;
    private int livesLeft, score;
    
    private int startX=7;
    private int startY=7;
    
    private int attackDir;
    
    //keeps track of the enemies
//    private ArrayList<MoveableShape> enemies;
    private ArrayList<Enemy> enemies;
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    private ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
    
    private Character knight;
    	    
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
    public final static int SNAKE_SPAWN = 2048;
    public final static int SPIDER_SPAWN = 4096;
    public final static int BAT_SPAWN = 8192;
    public final static int DRAGON_SPAWN = 16384;
    public final static int BLOCKED = 32768;
    public final static int CLEAR_ALL = 2147483647;
    public final static int REMOVE_YUMMY_BIT = CLEAR_ALL-YUMMY_BIT;
    public final static int UNHIDE = CLEAR_ALL-HIDDEN;
    
    
    //integer to check for movement
    private boolean moving[] = {false, false, false, false};
        
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
        livesLeft = 3;
        score = 0;
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
        
    	knight = new Character(screenData, startX, startY, BLOCK_SIZE);

        //count how many enemies are in the level
        numEnemies =0;
//        enemies = new ArrayList<MoveableShape>();
        enemies = new ArrayList<Enemy>();
        for (int r = 0; r < N_BLOCKS; r++) {
            for (int c = 0; c < N_BLOCKS; c++) {
            	if ((screenData[r][c] & SNAKE_SPAWN)!=0) {
            		numEnemies++;
            		enemies.add(new Snake(screenData, currLevel, c, r, BLOCK_SIZE,this.knight));
            	}
            	else if ((screenData[r][c] & SPIDER_SPAWN)!=0) {
            		numEnemies++;
            		enemies.add(new Spider(screenData, currLevel, c, r, BLOCK_SIZE,this.knight));
            	}
            	else if ((screenData[r][c] & BAT_SPAWN)!=0){
            		numEnemies++;
            		enemies.add(new Bat(screenData, currLevel, c, r, BLOCK_SIZE,this.knight));
            	}
            	else if ((screenData[r][c] & DRAGON_SPAWN)!=0){
            		numEnemies++;
            		enemies.add(new Dragon(screenData, currLevel, c-10, r-10, BLOCK_SIZE*20,this.knight));
            	}
      
            }
            
        }
        
        continueLevel();
    }
    
    
    //Sets the position of the character
    private void continueLevel()
    {
        dying = false;
        
        knight.setPos(startX, startY);
        
    }
    
    // runs all the game functions
    private void playGame (Graphics2D g2d)
    {
        if (dying)
            death();
        else
        {
            moveCharacter();
            drawCharacter (g2d);
            moveEnemies (g2d);
            checkMaze ();
        }
    }
    
    // sets up the intro screen
    private void showIntroScreen (Graphics2D g2d)
    {
        g2d.setColor (new Color (0, 32, 48));
        g2d.fillRect (50, SCREEN_SIZE / 2 -250 , SCREEN_SIZE - 100, 500);
        g2d.setColor (Color.white);
        g2d.drawRect (50, SCREEN_SIZE / 2 - 250, SCREEN_SIZE - 100, 500);

        String s = "Your mission, should you choose to accept it, is to";
        String t = "SAVE THE NARWHALL";
        String u = "Use the arrow keys to move, WASD to aim, and space bar to attack";
        String v = "Explore and progress to the top of the castle";
        String w = "Destroy those who stand in your way";
        String x = "The Narwhall awaits your rescue";
        String y = " Press s to start.";
        Font small = new Font ("Helvetica", Font.BOLD, 14);
        Font large = new Font ("Impact", Font.BOLD, 25);
        FontMetrics metr = getFontMetrics (small);
        FontMetrics metr2 = getFontMetrics (large);

        g2d.setColor (Color.white);
        g2d.setFont (small);
        g2d.drawString (s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 3);
        
        g2d.setFont (large);
        g2d.drawString (t, (SCREEN_SIZE - metr2.stringWidth(t)) / 2, SCREEN_SIZE / 3 +50);
        
        g2d.setFont (small);
        
        g2d.drawString (u, (SCREEN_SIZE - metr.stringWidth(u)) / 2, SCREEN_SIZE / 3 +100);
        g2d.drawString (v, (SCREEN_SIZE - metr.stringWidth(v)) / 2, SCREEN_SIZE / 3 +125);
        g2d.drawString (w, (SCREEN_SIZE - metr.stringWidth(w)) / 2, SCREEN_SIZE / 3 +150);
        g2d.drawString (x, (SCREEN_SIZE - metr.stringWidth(x)) / 2, SCREEN_SIZE / 3 +175);
        g2d.drawString (y, (SCREEN_SIZE - metr.stringWidth(y)) / 2, SCREEN_SIZE / 3 +300);
        
    }
    
 // sets up the end game screen
    private void showWinScreen (Graphics2D g2d)
    {
        g2d.setColor (new Color (0, 32, 48));
        g2d.fillRect (10, 10 , SCREEN_SIZE - 20, SCREEN_SIZE - 20);
        g2d.setColor (Color.white);
        g2d.drawRect (10, 10, SCREEN_SIZE - 20, SCREEN_SIZE - 20);

        String t = "CONGRATULATIONS!!!!";
        String u = "Final Score: " + score;
        String y = " Thank you for saving me, brave adventurer!";
        String z = "Close the window to exit";
        Font small = new Font ("Helvetica", Font.BOLD, 14);
        Font large = new Font ("Impact", Font.BOLD, 25);
        FontMetrics metr = getFontMetrics (small);
        FontMetrics metr2 = getFontMetrics (large);
        
        g2d.setColor (Color.white);
        
        g2d.setFont (large);
        g2d.drawString (t, (SCREEN_SIZE - metr2.stringWidth(t)) / 2, SCREEN_SIZE / 6);
        
        g2d.drawImage(new ImageIcon("char_pics/narwhall.png").getImage(), SCREEN_SIZE/4+50, SCREEN_SIZE/4, this);
        
        g2d.setFont (small);
        g2d.drawString (u, (SCREEN_SIZE - metr.stringWidth(u)) / 2, SCREEN_SIZE / 6 + 25);
        g2d.drawString (y, (SCREEN_SIZE - metr.stringWidth(y)) / 2, SCREEN_SIZE / 3 +300);
        g2d.drawString (z, (SCREEN_SIZE - metr.stringWidth(z)) / 2, SCREEN_SIZE / 3 +325);
        
        
    }
    
    
    //draws the score in the bottom right corner of the frame
    private void drawScore (Graphics2D g)
    {
    	
    	String s = "Health: " + knight.getHealth();
    	String t = "Score: " + score;
        Font smallFont = new Font ("Helvetica", Font.BOLD, 14);
        g.setFont (smallFont);
        g.setColor (Color.RED);
        g.drawString (s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
        g.setColor (Color.BLACK);
        g.drawString (t, SCREEN_SIZE / 2 + 200, SCREEN_SIZE + 16);

        for (int i = 0; i < livesLeft; i++)
            g.drawImage (new ImageIcon("env_pics/heart.png").getImage(), i * 28 + 8, SCREEN_SIZE + 1, BLOCK_SIZE, 22, this);
    }
    
    
    // check for various conditions
    private void checkMaze()
    {
        boolean finished = false;
        
        int px = knight.getX()/BLOCK_SIZE;
        int py = knight.getY()/BLOCK_SIZE;
        int ch = screenData[py][px];
    	
        //unhide blocks as seen
        for (int x =-4; x<=4; x++) {
			for (int y =-4; y<=4; y++) {
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
    		int dir = knight.getDirection();
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
    		int dir = knight.getDirection();
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
    	
    	//handle projectile explosions
    	if (projectiles.size() >= 1) {
        	for (int i=0; i<projectiles.size();i++) {
        		int x = projectiles.get(i).getX()/BLOCK_SIZE;
        		int y =projectiles.get(i).getY()/BLOCK_SIZE;
        		int dir = projectiles.get(i).getDir();
        		if(x>=0 && x<N_BLOCKS-1 && y>=0 && y<N_BLOCKS-1) {
        			if((((screenData[y][x]& RIGHT_WALL)!=0)&& (dir==1))||
        					(((screenData[y][x+1]& LEFT_WALL)!=0)&& (dir==1))||
        					(((screenData[y][x]& RIGHT_WALL)!=0) && (dir==0))||
        					(((screenData[y+1][x]& TOP_WALL)!=0)&& (dir==3))||
        					(((screenData[y][x]& BOTTOM_WALL)!=0)&& (dir==3))||
        					(((screenData[y][x]& BOTTOM_WALL)!=0)&& (dir==2))||
        					(((screenData[y+1][x]& HIDDEN)!=0)&& (dir==3))||
        					(((screenData[y][x+1]& HIDDEN)!=0)&& (dir==1))||
        					((screenData[y][x]& HIDDEN)!=0)) {
            			projectiles.remove(i);
            			break;
            		}
        		}
        		        		
            }
        }
    	
    	//handle projectile explosions
    	if (enemyProjectiles.size() >= 1) {
        	for (int i=0; i<enemyProjectiles.size();i++) {
        		int x = enemyProjectiles.get(i).getX()/BLOCK_SIZE;
        		int y =enemyProjectiles.get(i).getY()/BLOCK_SIZE;
        		int dir = enemyProjectiles.get(i).getDir();
        		if(x>=0 && x<N_BLOCKS-1 && y>=0 && y<N_BLOCKS-1) {
        			if((((screenData[y][x]& RIGHT_WALL)!=0)&& (dir==1))||
        					(((screenData[y][x+1]& LEFT_WALL)!=0)&& (dir==1))||
        					(((screenData[y][x]& RIGHT_WALL)!=0) && (dir==0))||
        					(((screenData[y+1][x]& TOP_WALL)!=0)&& (dir==3))||
        					(((screenData[y][x]& BOTTOM_WALL)!=0)&& (dir==3))||
        					(((screenData[y][x]& BOTTOM_WALL)!=0)&& (dir==2))||
        					(((screenData[y+1][x]& HIDDEN)!=0)&& (dir==3))||
        					(((screenData[y][x+1]& HIDDEN)!=0)&& (dir==1))||
        					((screenData[y][x]& HIDDEN)!=0)) {
            			enemyProjectiles.remove(i);
            			break;
            		}
        		}
        		        		
            }
        }
    	
    	if (currLevel == levels.length-1 && numEnemies <= 0) {
    		winGame=true;
    	}
    		
        if (finished)
        {
            score += 50;

            initLevel();
        }
    }
    
    //reduces number of lives, if none left, then end the game
    private void death()
    {
        livesLeft--;

        if (livesLeft == 0)
            inGame = false;

        continueLevel();
    }
    
    //move the enemies and check for collision with character
    private void moveEnemies (Graphics2D g2d)
    {
        for (int i = 0; i < numEnemies; i++)
        {      	
        	enemies.get(i).move();

        	enemies.get(i).draw (g2d);
        	
        	//let Spiders shoot 
        	if (Objects.equals(enemies.get(i).getType(),"Spider")) {
        		if(spiderTime>=spiderAttackTime) {
        			enemyProjectiles.add(enemies.get(i).attack());
        			spiderTime=0;
        		}
        		spiderTime++;
        	}
        	
        	if (Objects.equals(enemies.get(i).getType(),"Dragon")) {
        		if(dragonTime>=dragonAttackTime) {
        			enemyProjectiles.add(enemies.get(i).attack());
        			dragonTime=0;
        		}
        		dragonTime++;
        	}
            
        	if (enemies.get(i).contains (knight.getX(), knight.getY()))
        	{

        		if (knight.removeHealth(100))
        			dying = true;        		
        		else{
        			numEnemies--;
        			enemies.get(i).removeSpawn();
        			enemies.remove(i);
        		}

        	}
            
            if (projectiles.size() >=1) {
            	for (int j=0; j<projectiles.size();j++) {
                	if (enemies.get(i).contains (projectiles.get(j).getX(), projectiles.get(j).getY()))
                    {
                		enemies.get(i).removeHealth(projectiles.get(j).getDamage());
                		projectiles.remove(j);
                		if (enemies.get(i).checkDeath()) {
                			if(Objects.equals(enemies.get(i).getType(),"Dragon")){
                				score += 1000;
                    			numEnemies--;
                        		enemies.get(i).removeSpawn();
                        		enemies.remove(i);
                           		winGame=true;
                        		inGame=false;
                        		break;
                			}
                			else {
                				score += 50;
                    			numEnemies--;
                        		enemies.get(i).removeSpawn();
                        		enemies.remove(i);
                        		break;
                			}
                		}              			
                    }	
                }
            }                      
        }
        
        
    }
    
    //pacman's movement properties
    private void moveCharacter()
    {    	 
    	knight.move();
    	shootTime++;
    	
    	
    	
    	if (enemyProjectiles.size() >=1) {
        	for (int j=0; j<enemyProjectiles.size();j++) {
            	if (knight.contains(enemyProjectiles.get(j).getX(), enemyProjectiles.get(j).getY()))
                {
            		if (knight.removeHealth(enemyProjectiles.get(j).getDamage()))
            			dying=true;
            		enemyProjectiles.remove(j);             			
                }	
            }
        } 
    }
    
    //draw the various different directions of movement
    private void drawCharacter (Graphics2D g2d)
    {
       knight.draw(g2d);
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
                
                if ((screenData[gr][gc] & SNAKE_SPAWN) != 0)
                {
                	g2d.setColor(Color.GREEN);
                	g2d.fillRect(c, r, BLOCK_SIZE,BLOCK_SIZE );
                }
                
                if ((screenData[gr][gc] & BAT_SPAWN) != 0)
                {
                	g2d.setColor(Color.RED);
                	g2d.fillRect(c, r, BLOCK_SIZE,BLOCK_SIZE );
                }
                if ((screenData[gr][gc] & SPIDER_SPAWN) != 0)
                {
                	g2d.setColor(Color.BLUE);
                	g2d.fillRect(c, r, BLOCK_SIZE,BLOCK_SIZE );
                }
                
                //color inside of rooms
                if ((screenData[gr][gc] & ROOM_TILE) != 0)
                {
                	g2d.setColor(new Color(81,34,0));
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
                if ((screenData[gr][gc] & HIDDEN) != 0)
                {
                	g2d.setColor(Color.BLACK);
                	g2d.fillRect(c, r, BLOCK_SIZE,BLOCK_SIZE );
                }
            }
            
        }
        
        if (projectiles.size() >= 1) {
        	for (int i=0; i<projectiles.size();i++) {
        		projectiles.get(i).move();
            	projectiles.get(i).draw(g2d);
            }
        }
        
        if (enemyProjectiles.size() >= 1) {
        	for (int i=0; i<enemyProjectiles.size();i++) {
        		enemyProjectiles.get(i).move();
            	enemyProjectiles.get(i).draw(g2d);
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

        if (inGame)
            playGame (g2d);
        else if (winGame) {
        	showWinScreen(g2d);
        }
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
                    knight.updateReq(-1, 0);
                    moving[0]= true;
                }
                else if (key == KeyEvent.VK_RIGHT)
                {
                    knight.updateReq(1, 0);
                    moving[1]= true;
                }
                else if (key == KeyEvent.VK_UP)
                {
                    knight.updateReq(0, -1);
                    moving[2]= true;
                }
                else if (key == KeyEvent.VK_DOWN)
                {
                	knight.updateReq(0, 1);
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
                else if (key == KeyEvent.VK_A)
                {
                	//aim left
                	attackDir =0;
                }
                else if (key == KeyEvent.VK_W)
                {
                	//aim up
                	attackDir = 2;
                }
                else if (key == KeyEvent.VK_S)
                {
                	//aim down
                	attackDir = 3;
                }
                else if (key == KeyEvent.VK_D)
                {
                	//aim right
                	attackDir = 1;
                }
                if (key == KeyEvent.VK_SPACE && shootTime>= shootTimeLimit)
                {
                	 projectiles.add(knight.attack(attackDir));
                	 shootTime=0;
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
            }
            
           if (!moving[0] && !moving[1] && !moving[2] && !moving[3]) {
        	   knight.updateReq(0, 0);
           }
        }
    }
}
