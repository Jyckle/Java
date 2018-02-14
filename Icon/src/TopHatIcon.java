import java.awt.geom.Rectangle2D;
import java.awt.*;

import javax.swing.Icon;

public class TopHatIcon implements Icon {
	
	private int height;
	private int width;
	
	public TopHatIcon(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public int getIconHeight() {
		return height;
	}

	public int getIconWidth() {
		return width;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		
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

}
