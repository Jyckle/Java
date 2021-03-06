import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Character {
	
	//variables affecting character
    private final static int CHARACTER_SPEED = 6;
	
    private int currIm = 0;
    
    private Image charImages[] = {new ImageIcon("char_pics/up.png").getImage(), new ImageIcon("char_pics/down.png").getImage(), 
			new ImageIcon("char_pics/right.png").getImage(), new ImageIcon("char_pics/left.png").getImage()};
    
    //positioning variables
    private int x, y, dx, dy;
    private int req_dx, req_dy, view_dx, view_dy;
    private int block_size;
    
	private int screenData[][];
	
	//setup the stats of the character
	private final static int STARTING_HEALTH = 350;
	private int health= STARTING_HEALTH;
	private int attackPower=10;
	
	private Image attackImages[] = {new ImageIcon("char_pics/lSpear.png").getImage(), new ImageIcon("char_pics/rSpear.png").getImage(), 
			new ImageIcon("char_pics/uSpear.png").getImage(), new ImageIcon("char_pics/dSpear.png").getImage()};

	public Character (int screenData[][], int brd_x, int brd_y,
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
		health = STARTING_HEALTH;
		attackPower = 10;
	}

	
	//Draw Properties for character
	public void draw(Graphics2D g2) {
        
		if (view_dx == -1) {
			currIm = 3;
			g2.drawImage(charImages[3], x + 1,y + 1, null);
		}   
        else if (view_dx == 1){
			currIm = 2;
			g2.drawImage(charImages[2], x + 1,y + 1, null);
		}   
        else if (view_dy == -1){
			currIm = 0;
			g2.drawImage(charImages[0], x + 1,y + 1, null);
		}
        else if (view_dy == 1){
			currIm = 1;
			g2.drawImage(charImages[1], x + 1,y + 1, null);
		}  
        else
        	g2.drawImage(charImages[currIm], x + 1,y + 1, null);

	}
	
	public void move() {
		int px, py;
		int ch;

		// if the change in distance is okay, set it
		if (req_dx == -dx && req_dy == -dy)
		{
			dx = req_dx;
			dy = req_dy;
			view_dx = dx;
			view_dy = dy;
		}

		// check characters location and set the px, py values
		if (x % block_size == 0 && y % block_size == 0)
		{
			px = x / block_size;
			py = y / block_size;
			ch = screenData[py][px];

			if (req_dx != 0 || req_dy != 0)
			{
				if (!((req_dx == -1 && req_dy == 0 && ((ch & Board.LEFT_WALL) != 0)) 
						|| (req_dx == 1 && req_dy == 0 && (ch & Board.RIGHT_WALL) != 0)
						|| (req_dx == 0 && req_dy == -1 && (ch & Board.TOP_WALL) != 0)
						|| (req_dx == 0 && req_dy == 1 && (ch & Board.BOTTOM_WALL) != 0)
						|| ((screenData[py+req_dy][px+req_dx] & Board.BLOCKED) !=0)))
				{
					dx = req_dx;
					dy = req_dy;
					view_dx = dx;
					view_dy = dy;
				}
			}

			// Check for standstill
			if ((dx == -1 && dy == 0 && (ch & Board.LEFT_WALL) != 0)
					|| (dx == 1 && dy == 0 && (ch & Board.RIGHT_WALL) != 0)
					|| (dx == 0 && dy == -1 && (ch & Board.TOP_WALL) != 0)
					|| (dx == 0 && dy == 1 && (ch & Board.BOTTOM_WALL) != 0)
					|| ((screenData[py+dy][px+dx] & Board.BLOCKED) !=0))
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
		x = x + CHARACTER_SPEED * dx;
		y = y + CHARACTER_SPEED * dy;

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
	
	public boolean removeHealth(int damage) {
		health = health - damage;
		if (health <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getDirection() {
		return currIm;
		//0 = up, 1 = down
		//2 = right, 3 = left
	}
	
	public Projectile attack(int dir) {
		return new Projectile(dir,x,y,attackPower,attackImages);
	}
	
	public void setPos(int x, int y) {
		this.x = x*block_size;
		this.y =y*block_size;
	}
	
	public boolean contains (int r, int c) {
		if (r > (x - 12) && r < (x + 12)
				&& c > (y - 12) && c < y + 12)
			return true;
		else
			return false;
	}

}
