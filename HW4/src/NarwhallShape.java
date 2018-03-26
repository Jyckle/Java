import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
   A car that can be moved around.
 */
public class NarwhallShape implements MoveableShape
{
	/**
      Constructs a car item.
      @param x the left of the bounding rectangle
      @param y the top of the bounding rectangle
      @param width the width of the bounding rectangle
	 */
	public NarwhallShape(int x, int y, int width)
	{
		this.x = x;
		this.y = y;
		this.width = width;
	}

	public void draw(Graphics2D g2)
	{
		int[] xBod = {x + 20*width,x + -4*width,x + -16*width,x + -24*width,x + -32*width,x + -36*width,x + -18*width};
		int[] yBod = {y + 5*width,y + -46*width, y + -54*width, y + -55*width, y + -50*width, y + -40*width,y + 5*width};
		Polygon narwhalBody = new Polygon(xBod, yBod,yBod.length);

		int[] xHorn = {x + -32*width,x + -40*width,x + -24*width};
		int[] yHorn = {y + -50*width,y + -85*width,y + -55*width};
		Polygon narwhalHorn = new Polygon(xHorn, yHorn,yHorn.length);

		Ellipse2D.Double narwhalEye1
		= new Ellipse2D.Double(x + -26*width, y + -40*width,3*width,3*width);
		Ellipse2D.Double narwhalEye2
		= new Ellipse2D.Double(x + -13*width, y + -41*width,3*width,3*width);


		// S
		Point2D.Double r1
		= new Point2D.Double(x + -28*width,y + -30*width);
		// M
		Point2D.Double r2
		= new Point2D.Double(x + -24*width,y + -24*width);
		// I
		Point2D.Double r3
		= new Point2D.Double(x + -16*width,y + -20*width);
		// L
		Point2D.Double r4
		= new Point2D.Double(x + -12*width,y + -22*width);
		// E
		Point2D.Double r5
		= new Point2D.Double(x + -8*width,y + -25*width);
		// Y
		Point2D.Double r6
		= new Point2D.Double(x + -4*width,y + -33*width);
		
		
		Line2D.Double Smile1
		= new Line2D.Double(r1, r2);
		Line2D.Double Smile2
		= new Line2D.Double(r2, r3);
		Line2D.Double Smile3
		= new Line2D.Double(r3, r4);
		Line2D.Double Smile4
		= new Line2D.Double(r4, r5);
		Line2D.Double Smile5
		= new Line2D.Double(r5, r6);


		g2.setColor(Color.GRAY);
		g2.fill(narwhalBody);
		g2.setColor(Color.WHITE);
		g2.fill(narwhalHorn);
		g2.setColor(Color.BLACK);
		g2.draw(narwhalHorn);
		g2.draw(narwhalBody);
		g2.fill(narwhalEye1);
		g2.fill(narwhalEye2);
		g2.draw(Smile1);
		g2.draw(Smile2);
		g2.draw(Smile3);
		g2.draw(Smile4);
		g2.draw(Smile5);
	}

	public void translate (int dx, int dy) {
		x += dx;
		y += dy;
	}

	private int x;
	private int y;
	private int width;
}