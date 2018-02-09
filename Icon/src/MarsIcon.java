import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.Icon;

public class MarsIcon implements Icon {
	
	public MarsIcon(int aSize)
	{
		size = aSize;
	}
	
	public int getIconHeight() {
		return size;
	}

	public int getIconWidth() {
		return size;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double planet = new Ellipse2D.Double(x,y,
				size,size);
		g2.setColor(Color.RED);
		g2.fill(planet);
//		g2.draw(planet);
	}
	
	private int size;

}
