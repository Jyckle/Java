import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

/**
   This program implements an animation that moves
   a car shape.
*/
public class AnimationTester
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();

      final MoveableShape shape
            = new CarShape(0, 0, CAR_WIDTH);

      CanvasIcon icon = new CanvasIcon(shape,
            ICON_WIDTH, ICON_HEIGHT);

      final JLabel label = new JLabel(icon);
      frame.setLayout(new FlowLayout());
      frame.add(label);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      
      final int DELAY = 10;
      
      final Timer t = new Timer(DELAY,null);
      t.addActionListener(new ActionListener() {
    	  int time=0;
    	  public void actionPerformed(ActionEvent event) {
    		  shape.translate(2, 0);
    		  label.repaint();
    		  time += DELAY;
    		  
    		  if (time > 100*DELAY)
    			  t.stop();
    	  }
      });
      t.start();
      
//      final Timer t = new Timer(DELAY, new ActionListener() {
//    	  public void actionPerformed(ActionEvent event) {
//    		  shape.translate(2, 0);
//    		  frame.repaint();
//    	  }
//      });
//      t.start();
      
  
   }

   private static final int ICON_WIDTH = 500;
   private static final int ICON_HEIGHT = 500;
   private static final int CAR_WIDTH = 100;
}
