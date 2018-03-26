import java.awt.Graphics2D;

public interface MoveableShape {
	
	public void draw(Graphics2D g);
	
	public void translate(int dx, int dy);
	
}
