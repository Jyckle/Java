import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class IconTester {
	public static void main(String[] args) {
		
		ColorIcon I = new ColorIcon(250);
		IconTester j = new IconTester();
		JFrame frame = new JFrame();
		JButton Red = new JButton("RED");
		JButton Blue = new JButton("BLUE");
		JButton Green = new JButton("GREEN");
		
		Red.addActionListener(j.new ButtonListener(Color.RED, I,frame));
		Blue.addActionListener(j.new ButtonListener(Color.BLUE, I,frame));
		Green.addActionListener(j.new ButtonListener(Color.GREEN, I,frame));
		
		final JLabel label = new JLabel(I);
		
		frame.setLayout(new FlowLayout());
		
		frame.add(Red);
		frame.add(Blue);
		frame.add(Green);
		frame.add(label);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
//		JOptionPane.showMessageDialog(
//				null,
//				"Enjoy",
//				"LOOK AT MY PRETTY HAT",
//				JOptionPane.INFORMATION_MESSAGE,
//				new ColorIcon(500));
	}
	
	
	public class ButtonListener implements ActionListener
	{		
		private Color c;
		private ColorIcon I;
		private JFrame frame;
		
		public ButtonListener(Color c, ColorIcon I, JFrame frame)
		{
			this.c = c;
			this.I = I;
			this.frame = frame;
		}
		
		public void actionPerformed(ActionEvent e) {
			I.setIconColor(c);
			frame.repaint();
		}
	}

}
