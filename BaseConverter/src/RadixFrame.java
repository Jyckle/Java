import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class RadixFrame extends JFrame {
	private int base;
	
	public RadixFrame() {
		super("Base Converter");
		
		JButton toRadButton = new JButton("Convert To Radix ");
		JButton toDecButton = new JButton("Convert To Decimal");

		final int FIELD_WIDTH = 20;
		final JTextField radText = new JTextField(FIELD_WIDTH);
		final JTextField decText = new JTextField(FIELD_WIDTH);
		final JTextField baseText = new JTextField(FIELD_WIDTH);
		radText.setText("Radix value here");
		decText.setText("Decimal value here");
		baseText.setText("Base value here");


		toRadButton.addActionListener (new toRadixListener (radText, decText, baseText));
		toDecButton.addActionListener (new ButtonListener (decText, "Some Number in Decimal"));

		setLayout(new FlowLayout());

		add(toRadButton);
		add(toDecButton);
		add(decText);
		add(baseText);
		add(radText);
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	   }
	
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
		
	 
	 private class toRadixListener implements ActionListener
	   {
		   String rad;
		   final JTextField radField;
		   final JTextField decField;
		   final JTextField baseField;
		   String dec;

		   public toRadixListener (final JTextField radField, final JTextField decField, final JTextField baseField)
		   {
			   this.radField = radField;
			   this.decField = decField;
			   this.baseField = baseField;
		   }

		   public void actionPerformed(ActionEvent e) {
			   base = Integer.parseInt(baseField.getText());
			   DecimalRadixConv n = new DecimalRadixConv(base);
			   dec = decField.getText();
			   rad = n.ToRadix(Integer.parseInt(dec));
			   radField.setText (rad);
		   }
	   }
}
