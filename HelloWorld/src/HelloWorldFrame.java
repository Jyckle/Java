import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;


public class HelloWorldFrame {
	public static void main (String args[])  
	{
		String hello = "Hello, World";

		final JFrame frame = new JFrame (hello);
		
		JPanel panel = new JPanel ();
		frame.add (panel);
		
		JLabel text = new JLabel (hello + "!");
		panel.add(text);
		
		JButton wButton = new JButton ("Click Me");
		panel.add(wButton);
		
		wButton.addActionListener(
				new ActionListener()
				{	
					public void actionPerformed(ActionEvent e)
					{
						JOptionPane.showMessageDialog(
								null, 
								"Hello! Hope you had a fabulous break",
								"Welcome Back", 
								JOptionPane.PLAIN_MESSAGE);
					}
				});
		
		frame.setSize(200,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
