import javax.swing.*;
import java.awt.event.*;

public class QuizGUI
{
    public static void main (String args[])
    {
        final JFrame frame = new JFrame ("Quiz");

        JPanel panel = new JPanel ();
        frame.add (panel);

	JButton wButton = new JButton ("Start Quiz");
	panel.add (wButton);

        JLabel text = new JLabel ("This quiz has 1 question");
        panel.add (text);

	wButton.addActionListener (
		new ActionListener()
		{
			int qNum = 1;
			Object selected;

			public void actionPerformed (ActionEvent e)
			{
				String question = "T/F:  The earth is flat.";
				Object options[] = { "False", "True" };

				selected = JOptionPane.showOptionDialog (
					null,
					question,
					"Question " + qNum,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, null );

				System.out.println ("Chose:  " + selected);
			}
		});

	frame.setSize (200, 200);
        frame.setVisible (true);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }
}

