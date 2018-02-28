import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class RadixFrame extends JFrame {
	private int base;
	
	public RadixFrame() {
		super("Base Converter");
		
		char c = 0x2192;
		char c2 = 0x2190;
		JButton toRadButton = new JButton(String.valueOf(c));
		JButton toDecButton = new JButton(String.valueOf(c2));

		final JTextField radText = new JTextField("Radix Number");
		final JTextField decText = new JTextField("Decimal Number");
		final JTextField baseText = new JTextField("Base");
//		radText.setText("Radix value here");
//		decText.setText("Decimal value here");
//		baseText.setText("Base value here");
		

		toRadButton.addActionListener (new toRadixListener (radText, decText, baseText));
		toDecButton.addActionListener (new ButtonListener (decText, "Some Number in Decimal"));

		setLayout(new FlowLayout());

		add(decText);
		add(toRadButton);
		add(baseText);
		add(toDecButton);
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
