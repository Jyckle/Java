import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GoodbyeHelloFrame extends JFrame
{
   public GoodbyeHelloFrame (String title)
   {
	   super(title);

	   JButton helloButton = new JButton("Say Hello");
	   JButton goodbyeButton = new JButton("Say Goodbye");

	   final int FIELD_WIDTH = 20;
	   final JTextField textField = new JTextField(FIELD_WIDTH);
	   textField.setText("Click a button!");

      // button listeners defined as anonymous classes
//	   helloButton.addActionListener(new ActionListener()
//      		{    	  
//    	  		public void actionPerformed(ActionEvent e) {
//    	  			textField.setText ("Hello");
//    	  		}
//      		});
//
//      goodbyeButton.addActionListener(new ActionListener()
//      		{    	  
//	  			public void actionPerformed(ActionEvent e) {
//	  				textField.setText ("Goodbye");
//	  			}
//      		});

	   helloButton.addActionListener (new ButtonListener (textField, "Hello"));
	   goodbyeButton.addActionListener (new ButtonListener (textField, "Goodbye"));

	   setLayout(new FlowLayout());

	   add(helloButton);
	   add(goodbyeButton);
	   add(textField);

	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   pack();
	   setVisible(true);
   }
   
   // given similarity, defined button listeners as a named class
   private class ButtonListener implements ActionListener
   {
	   String msg;
	   final JTextField textField;

	   public ButtonListener (final JTextField textField, String msg)
	   {
		   this.textField = textField;
		   this.msg = msg;
	   }

	   public void actionPerformed(ActionEvent e) {
		   textField.setText (msg);
	   }
   }
}
