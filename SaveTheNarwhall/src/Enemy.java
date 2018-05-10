import java.awt.Graphics2D;

public class Enemy implements MoveableShape {
	//This class is to define the basic properties for all enemies within the game
	private Character player;

	private int x, y;
	private int dx, dy;
	private int width;
	private int speed = 3;
	private int maxLevel;

	private short screenData[][];

	public Enemy(short screenData[][], int level, int brd_x, int brd_y,
			int width, Character player) {
		
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
		this.player = player;

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
	}

	public void move() {
		int count = 0;
		int px, py;

		int sdx[] = new int[4];
		int sdy[] = new int[4];

		//if x and y are evenly divisible into the width, set the position to those values
		if (x % width == 0 && y % width == 0)
		{
			px = x / width;
			py = y / width;

			count = 0;

			//four different 
			if ((screenData[py][px] & Board.LEFT_WALL) == 0 && dx != 1)
			{
				sdx[count] = -1;
				sdy[count] = 0;
				count++;
			}

			if ((screenData[py][px] & Board.TOP_WALL) == 0 && dy != 1)
			{
				sdx[count] = 0;
				sdy[count] = -1;
				count++;
			}

			if ((screenData[py][px] & Board.RIGHT_WALL) == 0 && dx != -1)
			{
				sdx[count] = 1;
				sdy[count] = 0;
				count++;
			}

			if ((screenData[py][px] & Board.BOTTOM_WALL) == 0 && dy != -1)
			{
				sdx[count] = 0;
				sdy[count] = 1;
				count++;
			}

			if (count != 0)
			{
				count = (int) (Math.random() * count);

				dx = sdx[count];
				dy = sdy[count];
			}
			else
			{
				dx = -dx;
				dy = -dy;
			}

		}

		x = x + (dx * speed);
		y = y + (dy * speed);
	}

	public void draw (Graphics2D g2) {

	}

	public boolean contains (int r, int c) {
		return true;
	}

	public int getHealth() {
		return 5;
	}

	public void setHealth(int health) {

	}

	public void removeHealth(int damage) {

	}

	public void attack() {

	}
	
	public void removeSpawn() {
		
	}

}
