import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
   A car that can be moved around.
 */
public class TopHatShape implements MoveableShape
{
	/**
      Constructs a car item.
      @param x the left of the bounding rectangle
      @param y the top of the bounding rectangle
      @param width the width of the bounding rectangle
	 */
	public TopHatShape(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics2D g2)
	{
		Rectangle2D.Double hatRect = new Rectangle2D.Double(
				x + width/3,
				y + height/8,
				width/3,
				height/2);
		Rectangle2D.Double bandRect = new Rectangle2D.Double(
				x + width/3,
				y + 5*height/8, 
				width/3, height/8);
		Rectangle2D.Double brimRect = new Rectangle2D.Double(
				x + width/6,
				y + 3*height/4, 
				width*2/3, height/6);
		
		g2.setColor(Color.BLACK);
		g2.fill(hatRect);
		g2.fill(brimRect);
		
		g2.setColor(Color.GREEN);
		g2.fill(bandRect);
	}

	public void translate (int dx, int dy) {
		x += dx;
		y += dy;
	}

	private int x;
	private int y;
	private int width;
	private int height;
}