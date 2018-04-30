import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class PacmanShape {
	
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
    		{new ImageIcon("char_pics/PacMan1.gif").getImage(), new ImageIcon("char_pics/PacMan2up.gif").getImage(), 
    			new ImageIcon("char_pics/PacMan3up.gif").getImage(), new ImageIcon("char_pics/PacMan4up.gif").getImage()},
    		//down images
    		{new ImageIcon("char_pics/PacMan1.gif").getImage(), new ImageIcon("char_pics/PacMan2down.gif").getImage(), 
    			new ImageIcon("char_pics/PacMan3down.gif").getImage(), new ImageIcon("char_pics/PacMan4down.gif").getImage()},
    		//left images
    		{new ImageIcon("char_pics/PacMan1.gif").getImage(), new ImageIcon("char_pics/PacMan2left.gif").getImage(), 
    			new ImageIcon("char_pics/PacMan3left.gif").getImage(), new ImageIcon("char_pics/PacMan4left.gif").getImage()},
    		//right images	
    		{new ImageIcon("char_pics/PacMan1.gif").getImage(), new ImageIcon("char_pics/PacMan2right.gif").getImage(),
    			new ImageIcon("char_pics/PacMan3right.gif").getImage(), new ImageIcon("char_pics/PacMan4right.gif").getImage() }
  
    	};
    
    //positioning variables
    private int x, y, dx, dy;
    private int req_dx, req_dy, view_dx, view_dy;
    private int block_size;

	private short screenData[][];

	private final static int LEFT_WALL = 1;
	private final static int RIGHT_WALL = 4;
	private final static int TOP_WALL = 2;
	private final static int BOTTOM_WALL = 8;
	

	public PacmanShape (short screenData[][], int brd_x, int brd_y,
			int block_size)
	{
		
		//set the class attributes equal to the passed in variables
		this.screenData = screenData;
		this.x = brd_x * block_size;
		this.y = brd_y * block_size;
		this.block_size= block_size;
		req_dx = 0;
		req_dy = 0;
		view_dx = -1;
		view_dy = 0;
	}

	
	//Draw Properties for Pacman
	public void draw(Graphics2D g2) {
        
		if (view_dx == -1)
            g2.drawImage(pacImages[2][spriteImgIdx], x + 1,y + 1, null);
        else if (view_dx == 1)
        	g2.drawImage(pacImages[3][spriteImgIdx], x + 1,y + 1, null);
        else if (view_dy == -1)
        	g2.drawImage(pacImages[0][spriteImgIdx], x + 1,y + 1, null);
        else
        	g2.drawImage(pacImages[1][spriteImgIdx], x + 1,y + 1, null);

	}
	
    //run the animation with all images
    public void doAnim()
    {
        spriteDelayCount--;

        if (spriteDelayCount <= 0) {
            spriteDelayCount = PACMAN_SPRITE_DELAY;
            spriteImgIdx = spriteImgIdx + spriteIncr;

            if (spriteImgIdx == (PACMAN_SPRITE_NUM_POS - 1) || spriteImgIdx == 0) {
                spriteIncr = -spriteIncr;
            }
        }
    }

	
	public void move() {
		int px, py;
		short ch;

		// if the change in distance is okay, set it
		if (req_dx == -dx && req_dy == -dy)
		{
			dx = req_dx;
			dy = req_dy;
			view_dx = dx;
			view_dy = dy;
		}

		// check pacmans location and set the px, py values
		if (x % block_size == 0 && y % block_size == 0)
		{
			px = x / block_size;
			py = y / block_size;
			ch = screenData[py][px];

			if (req_dx != 0 || req_dy != 0)
			{
				if (!((req_dx == -1 && req_dy == 0 && (ch & LEFT_WALL) != 0)
						|| (req_dx == 1 && req_dy == 0 && (ch & RIGHT_WALL) != 0)
						|| (req_dx == 0 && req_dy == -1 && (ch & TOP_WALL) != 0)
						|| (req_dx == 0 && req_dy == 1 && (ch & BOTTOM_WALL) != 0)))
				{
					dx = req_dx;
					dy = req_dy;
					view_dx = dx;
					view_dy = dy;
				}
			}

			// Check for standstill
			if ((dx == -1 && dy == 0 && (ch & LEFT_WALL) != 0)
					|| (dx == 1 && dy == 0 && (ch & RIGHT_WALL) != 0)
					|| (dx == 0 && dy == -1 && (ch & TOP_WALL) != 0)
					|| (dx == 0 && dy == 1 && (ch & BOTTOM_WALL) != 0))
			{
				dx = 0;
				dy = 0;
			}
			
			if (req_dx == 0 && req_dy == 0)
			{
				dx=0;
				dy=0;
			}
		}
		x = x + PACMAN_SPEED * dx;
		y = y + PACMAN_SPEED * dy;

	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void updateReq(int req_dx, int req_dy) {
		this.req_dx = req_dx;
		this.req_dy = req_dy;
	}
	
	

}
