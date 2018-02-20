import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.Icon;

public class ColorIcon implements Icon {
	
	private Graphics2D g2;
	private Color color = Color.RED;
	
	public ColorIcon(int aSize)
	{
		size = aSize;
	}
	
	public int getIconHeight() {
		return size;
	}

	public int getIconWidth() {
		return size;
	}
	
	public void setIconColor(Color color) {
		this.color = color;
		
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		g2 = (Graphics2D) g;
		Ellipse2D.Double planet = new Ellipse2D.Double(x,y,
				size,size);
		g2.setColor(color);
		g2.fill(planet);
//		g2.draw(planet);
	}
	
	private int size;

}

