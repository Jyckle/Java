import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
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
      
      final MoveableShape shape2
            = new NarwhallShape(600,600,3);
      
      final MoveableShape shape3
            = new TopHatShape(800,-300, 100,150);
      
      final MoveableShape shape4
            = new WaterShape(-100,-1200,1000,200);
      
      ArrayList<MoveableShape> shapes = new ArrayList<MoveableShape>();
      shapes.add(shape4);
      shapes.add(shape);
      shapes.add(shape2);
      shapes.add(shape3);

      CanvasIcon icon = new CanvasIcon(shapes,
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
    		  shape.translate(2,1);
    		  shape2.translate(-1, 0);
    		  shape3.translate(-2, 2);
    		  shape4.translate(0, 6);
    		  label.repaint();
    		  time += DELAY;
    		  
    		  if (time > 300*DELAY)
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

   private static final int ICON_WIDTH = 700;
   private static final int ICON_HEIGHT = 700;
   private static final int CAR_WIDTH = 100;
}
