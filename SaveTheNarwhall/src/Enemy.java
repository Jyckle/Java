import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public abstract class Enemy implements MoveableShape {
	//This class is to define the basic properties for all enemies within the game
	protected Character target;
	protected int health=10;
	protected int movementSpeed;
	protected int pauseTime=10;
	
	private Image Images[];
	
	protected int x, y;
	private int dx, dy;
	protected int dir;
	private int width;
	
	private int spawnX;
	private int spawnY;
	
	protected int damage;
	private int time = 0;
	
	private int screenData[][];

	public Enemy(int screenData[][], int level, int brd_x, int brd_y,
			int width, Character target, Image[] Images) {
		
		//set the class attributes equal to the passed in variables
		this.screenData = screenData;
		this.x = brd_x * Board.BLOCK_SIZE;
		this.y = brd_y * Board.BLOCK_SIZE;
		this.spawnX = brd_x;
		this.spawnY = brd_y;
		this.width = width;
		this.target = target;
		this.Images = Images;

	}

	public void move() {
		
		//find out which direction should be moved in
		int difX, difY;
		difX = target.getX()-x;
		difY = target.getY()-y;
		
		int px, py;

		//if x and y are evenly divisible into the width, set the position to those values
		if (x % width == 0 && y % width == 0)
		{
			px = (x / width);
			py = (y / width);

			//four different 
			if ((screenData[py][px] & Board.LEFT_WALL) == 0 && difX<0)
			{
				dx=-1;
			}
			else if ((screenData[py][px] & Board.RIGHT_WALL) == 0 && difX>0)
			{
				dx=1;
			}
			else {
				dx=0;
			}
			
			
			if ((screenData[py][px] & Board.TOP_WALL) == 0 && difY<0)
			{
				dy=-1;
			}
			else if ((screenData[py][px] & Board.BOTTOM_WALL) == 0 && difY>0)
			{
				dy=1;
			}
			else {
				dy=0;
			}
			
			//if hidden don't move
			if ((screenData[py][px] & Board.HIDDEN) != 0) {
				dx =0;
				dy =0;
			}
		}
		
		if ((dx ==1 && (dy ==1 ||dy== -1)) ||
			(dx ==-1 && (dy ==1 ||dy== -1))	) {
			Random rand = new Random();
			
			int pick= rand.nextInt(2);
			if(pick ==1) {
				dx=0;
			}
			else {
				dy=0;
			}
		}
		
		if(dx==1) {
			dir = 1;
		}
		else if(dx==-1) {
			dir = 0;
		}
		else if(dy==1) {
			dir=3;
		}
		else if (dy==-1) {
			dir=2;
		}
		
		if(time >= pauseTime) {
			if (x+dx*movementSpeed >= 0  && x+dx*movementSpeed <= (Board.N_BLOCKS-1)*Board.BLOCK_SIZE
					&& y+dy*movementSpeed >= 0  && y+dy*movementSpeed <= (Board.N_BLOCKS-1)*Board.BLOCK_SIZE) {
				x = x + (dx * movementSpeed);
				y = y + (dy * movementSpeed);
			}
		}
		
		if(time >= pauseTime*2) {
			time=0;
		}
		
		time++;
		
		

	}

	public void draw (Graphics2D g2) {
		if ((screenData[y/Board.BLOCK_SIZE][x/Board.BLOCK_SIZE] & Board.HIDDEN) != 0) {
			
		}
		else {
			if (dx ==1) {
				//right image
				g2.drawImage(Images[0], x, y, width, width, null);
			}
			else if (dx == -1) {
				//left image
				g2.drawImage(Images[1], x, y, width, width, null);
			}
			else if (dy==1) {
				g2.drawImage(Images[2], x, y, width, width, null);
			}
			else if (dy==-1) {
				g2.drawImage(Images[3], x, y, width, width, null);
			}
			else{
				g2.drawImage(Images[0], x, y, width, width, null);
			}
		}
		
	}

	public boolean contains (int r, int c) {
		if (r > (x - 12) && r < (x + 12)
				&& c > (y - 12) && c < y + 12)
			return true;
		else
			return false;
	}

	public int getHealth() {
		return health;
	}

	public void removeHealth(int damage) {
		health = health - damage;
	}

	public abstract Projectile attack();
	
	public abstract String getType();
		
	public boolean checkDeath() {
		if (health <=0) {
			return true;
		}
		else 
			return false;
	}
	
	public void removeSpawn() {
		screenData[spawnY][spawnX] = screenData[spawnY][spawnX] & 
				Board.CLEAR_ALL-Board.BAT_SPAWN-Board.SPIDER_SPAWN - Board.SNAKE_SPAWN;
	}
	
	public int getDamage() {
		return damage;
	}

}
