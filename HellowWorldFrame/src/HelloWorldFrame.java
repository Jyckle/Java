import javax.swing.*;
//import java.awt.BorderLayout;
import java.awt.event.*;


public class HelloWorldFrame extends JFrame {

	public HelloWorldFrame(String title)  
	{
		
		JPanel panel = new JPanel ();
		add (panel);
		
		JLabel text = new JLabel (title + "!");
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
		
		setSize(200,200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
