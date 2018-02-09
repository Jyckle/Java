import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

public class TopHatIcon implements Icon {
	
	private int height;
	private int width;
	
	public TopHatIcon(int weight, int height)
	{
		this.width = weight;
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
		
		Rectangle2D.Double bodyRect = new Rectangle2D.Double(
				x + width/3,
				y + height/8,
				width/3,
				height/2);
		
		
	}

}
