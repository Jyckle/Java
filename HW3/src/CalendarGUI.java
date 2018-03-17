import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CalendarGUI {
	private GregorianCalendar current;
	
	public static void main (String[] args)
	{
		CalendarGUI g = new CalendarGUI ();

		JFrame frame = new JFrame ("Days Between");
		//frame.setSize(300,300);
		
		g.current = new GregorianCalendar();
		
		int cYear = g.current.get(1);
		int cMonth = g.current.get(4);
		int cDay = g.current.get(5);
		
		
		final JTextField year = new JTextField (Integer.toString(cYear),4);
		final JTextField month = new JTextField (Integer.toString(cMonth),2);
		final JTextField day = new JTextField (Integer.toString(cDay),2);
		
		final JLabel daysBetween = new JLabel("Days Between");

		// add focus listeners to fields (so text selected when field clicked)
		year.addFocusListener (g.new JTextFieldFocusListener (year));
		month.addFocusListener (g.new JTextFieldFocusListener (month));
		day.addFocusListener (g.new JTextFieldFocusListener (day));

		JButton calcButton = new JButton ("Days Between");

		calcButton.addActionListener (new ActionListener()
		{
			public void actionPerformed (ActionEvent ae) {
				int nYear = Integer.parseInt(year.getText());
				int nMonth = Integer.parseInt(month.getText());
				int nDay = Integer.parseInt(day.getText());
				
				GregorianCalendar newCal = new GregorianCalendar(nYear,nMonth-1,nDay);
				long dif = newCal.getTimeInMillis() - g.current.getTimeInMillis();
				dif = dif/(86400000);
				int days = (int) dif;
				
				daysBetween.setText(Integer.toString(days));
				
				g.current = newCal;

			}
		});


		JPanel datePanel = new JPanel ();
		datePanel.add (year);
		datePanel.add (month);
		datePanel.add (day);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add (calcButton);
		
		JPanel daysPanel = new JPanel();
		daysPanel.add(daysBetween);

		frame.add (datePanel, BorderLayout.NORTH);
		frame.add (buttonPanel, BorderLayout.CENTER);
		frame.add (daysPanel, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected class JTextFieldFocusListener implements FocusListener
	{
		JTextField textField;

		public JTextFieldFocusListener (JTextField textField) {
			this.textField = textField;
		}

		public void focusLost (final FocusEvent pE) {}
		public void focusGained (final FocusEvent pE) {
			textField.selectAll();
		}		
	}
}

