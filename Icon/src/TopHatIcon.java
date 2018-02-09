import java.awt.Component;
import java.awt.Graphics;

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
		
	}

}
