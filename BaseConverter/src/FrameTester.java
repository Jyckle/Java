import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;

public class FrameTester {

	public static void main(String[] args) {

		FrameTester j = new FrameTester();
		JFrame frame = new JFrame();

		JButton helloButton = new JButton("Say Hello");
		JButton goodbyeButton = new JButton("Say Goodbye");

		final int FIELD_WIDTH = 20;
		final JTextField textField = new JTextField(FIELD_WIDTH);
		textField.setText("Click a button!");

//		helloButton.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e) {
//				textField.setText("Hello");
//			}
//		});
//
//		goodbyeButton.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e) {
//				textField.setText("Goodbye");
//			}
//		});

		helloButton.addActionListener(j.new ButtonListener(textField, "Hello"));
		goodbyeButton.addActionListener(j.new ButtonListener(textField, "Goodbye"));

		
		frame.setLayout(new FlowLayout());

		frame.add(helloButton);
		frame.add(goodbyeButton);
		frame.add(textField);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);


	}

	public class ButtonListener implements ActionListener
	{
		String msg;
		final JTextField textField;
		
		public ButtonListener (final JTextField textField, String msg)
		{
			this.textField = textField;
			this.msg = msg;
		}
		public void actionPerformed(ActionEvent e) {
			textField.setText(msg);
		}
	}

}
