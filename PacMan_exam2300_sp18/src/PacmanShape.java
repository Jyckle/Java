import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class PacmanShape implements MoveableShape {
	
	//variables affecting pacman character
    private final static int PACMAN_SPRITE_DELAY = 2;
    private final static int PACMAN_SPRITE_NUM_POS = 4;
    private final static int PACMAN_SPEED = 6;
	
    //variables affecting sprite properties
    private int spriteDelayCount = PACMAN_SPRITE_DELAY;
    private int spriteIncr = 1;
    private int spriteImgIdx = 0;
    
    //all images for pacman
    private Image pacImages[][] = 
    	{
    		//up images	
    		{new ImageIcon("pacpix/PacMan1.gif").getImage(), new ImageIcon("pacpix/PacMan2up.gif").getImage(), 
    			new ImageIcon("pacpix/PacMan3up.gif").getImage(), new ImageIcon("pacpix/PacMan4up.gif").getImage()},
    		//down images
    		{new ImageIcon("pacpix/PacMan1.gif").getImage(), new ImageIcon("pacpix/PacMan2down.gif").getImage(), 
    			new ImageIcon("pacpix/PacMan3down.gif").getImage(), new ImageIcon("pacpix/PacMan4down.gif").getImage()},
    		//left images
    		{new ImageIcon("pacpix/PacMan1.gif").getImage(), new ImageIcon("pacpix/PacMan2left.gif").getImage(), 
    			new ImageIcon("pacpix/PacMan3left.gif").getImage(), new ImageIcon("pacpix/PacMan4left.gif").getImage()},
    		//right images	
    		{new ImageIcon("pacpix/PacMan1.gif").getImage(), new ImageIcon("pacpix/PacMan2right.gif").getImage(),
    			new ImageIcon("pacpix/PacMan3right.gif").getImage(), new ImageIcon("pacpix/PacMan4right.gif").getImage() }
  
    	};
    
    //positioning variables
    private int pacman_x, pacman_y, pacman_dx, pacman_dy;
    private int req_dx, req_dy, view_dx, view_dy;

    private Timer timer;

	private short screenData[][];
	private int type= 0;

	private final static int LEFT_WALL = 1;
	private final static int RIGHT_WALL = 4;
	private final static int TOP_WALL = 2;
	private final static int BOTTOM_WALL = 8;

	public PacmanShape (short screenData[][], int level, int brd_x, int brd_y,
			int width)
	{
		//holds all potential speeds that can be used
		int validSpeeds[] = {1, 2, 3, 4, 5, 6, 8, 10, 12};

		//ensures that the maximum level of the board is not exceeded
		if (level > Board.MAX_LEVEL)
			level = maxLevel;

		//set the class attributes equal to the passed in variables
		this.screenData = screenData;
		this.x = brd_x * width;
		this.y = brd_y * width;
		this.width = width;

		//sets the desired speed for a level
		int levelSpeed = level + 2;
		int random = (int) (Math.random() * levelSpeed);

		if (random > speed) {
			random = speed;
		}

		//randomly selects a speed value to use
		speed = validSpeeds[random];

		if (random % 2 == 0)
			dx = 1;
		else
			dx = -1;

		dy = 0;

		//select ghosttype randomly
		type = (int)(Math.random()*4)+1;
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {
		int px, py;
		short ch;

		// if the change in distance is okay, set it
		if (req_dx == -pacman_dx && req_dy == -pacman_dy)
		{
			pacman_dx = req_dx;
			pacman_dy = req_dy;
			view_dx = pacman_dx;
			view_dy = pacman_dy;
		}

		// check pacmans location and set the px, py values
		if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0)
		{
			px = pacman_x / BLOCK_SIZE;
			py = pacman_y / BLOCK_SIZE;
			ch = screenData[py][px];

			if ((ch & YUMMY_BIT) != 0)
			{
				screenData[py][px] = (short) (ch & REMOVE_YUMMY_BIT);
				score++;
			}

			if (req_dx != 0 || req_dy != 0)
			{
				if (!((req_dx == -1 && req_dy == 0 && (ch & LEFT_WALL) != 0)
						|| (req_dx == 1 && req_dy == 0 && (ch & RIGHT_WALL) != 0)
						|| (req_dx == 0 && req_dy == -1 && (ch & TOP_WALL) != 0)
						|| (req_dx == 0 && req_dy == 1 && (ch & BOTTOM_WALL) != 0)))
				{
					pacman_dx = req_dx;
					pacman_dy = req_dy;
					view_dx = pacman_dx;
					view_dy = pacman_dy;
				}
			}

			// Check for standstill
			if ((pacman_dx == -1 && pacman_dy == 0 && (ch & LEFT_WALL) != 0)
					|| (pacman_dx == 1 && pacman_dy == 0 && (ch & RIGHT_WALL) != 0)
					|| (pacman_dx == 0 && pacman_dy == -1 && (ch & TOP_WALL) != 0)
					|| (pacman_dx == 0 && pacman_dy == 1 && (ch & BOTTOM_WALL) != 0))
			{
				pacman_dx = 0;
				pacman_dy = 0;
			}
		}
		pacman_x = pacman_x + PACMAN_SPEED * pacman_dx;
		pacman_y = pacman_y + PACMAN_SPEED * pacman_dy;

	}

	@Override
	public boolean contains(int r, int c) {
		// TODO Auto-generated method stub
		return false;
	}

}
