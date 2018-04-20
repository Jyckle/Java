import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.ImageIcon;

/* 
 * this class is an implementation of the MoveableShape Class
 * it defines all relevant methods for the Ghosts contained within the game
 */

public class GhostShape implements MoveableShape
{
	//declare all the attributes of the GhostShape Class
	private int x, y;
	private int dx, dy;
	private int width;

	private int speed = 3;
	private int maxLevel;

	private short screenData[][];

	//Class constructor
	public GhostShape (short screenData[][], int level, int brd_x, int brd_y,
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
	}
	
	//defines all movement properties for the GhostShape
	public void move ()
	{
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

			if ((screenData[py][px] & 1) == 0 && dx != 1)
			{
				sdx[count] = -1;
				sdy[count] = 0;
				count++;
			}

			if ((screenData[py][px] & 2) == 0 && dy != 1)
			{
				sdx[count] = 0;
				sdy[count] = -1;
				count++;
			}

			if ((screenData[py][px] & 4) == 0 && dx != -1)
			{
				sdx[count] = 1;
				sdy[count] = 0;
				count++;
			}

			if ((screenData[py][px] & 8) == 0 && dy != -1)
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

	public boolean contains (int pacx, int pacy)
	{
		if (pacx > (x - 12) && pacx < (x + 12)
				&& pacy > (y - 12) && pacy < y + 12)
			return true;
		else
			return false;
	}

	public void draw (Graphics2D g2)
	{
		Ellipse2D.Double head = new Ellipse2D.Double (x, y, 0.9*width, 0.9*width);
		Rectangle2D.Double body = new Rectangle2D.Double (x, y+width/3, 0.9*width, 2*width/3);
		g2.setColor(Color.MAGENTA);
		g2.fill(head);
		g2.fill(body);
	}
}
