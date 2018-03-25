import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
   A car that can be moved around.
 */
public class WaterShape implements MoveableShape
{
	/**
      Constructs a car item.
      @param x the left of the bounding rectangle
      @param y the top of the bounding rectangle
      @param width the width of the bounding rectangle
	 */
	public WaterShape(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics2D g2)
	{
		Ellipse2D.Double water = new Ellipse2D.Double(x,y,
				width,height);
		g2.setColor(Color.BLUE);
		g2.fill(water);
//		g2.draw(planet);
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