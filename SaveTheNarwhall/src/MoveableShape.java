import java.awt.*;

/**
   A shape that can be moved around.
 */
public interface MoveableShape
{
	// this method is to draw the shape
	void draw (Graphics2D g2);		

	//this method is to define the movement for the characters
	void move ();

	// this method is to determine if the given r,c fall within the shape's coordinates
	boolean contains (int r, int c);	
	
	void removeSpawn();
}
