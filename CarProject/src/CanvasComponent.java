import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
   An icon that contains a drawable shape.
*/
public class CanvasComponent extends JComponent
{
   public CanvasComponent(ArrayList<MoveableShape> shapes, int width, int height)
   {
      super();

	  this.shapes = shapes;
      setMinimumSize(new Dimension(width,height));
      setPreferredSize(new Dimension(width,height));
   }
   

   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      for (MoveableShape shape: shapes) {
    	  shape.draw(g2);
      }
     
   }

   private ArrayList<MoveableShape> shapes;
}