import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class QuizGUI
{
	private String[] questions = {"Who's on first?", "Second Base?", "Why?","Tell me the pitcher's name", "Center field?","This is a long quiz, how about a shortstop?"};
	private String[] corAns = {"Yes", "What", "Left Field", "Tomorrow", "Because", "I don't care!"};
	private String[] answers;

	public static void main (String args[])
	{
		QuizGUI g = new QuizGUI();

		g.answers = new String[6];

		final JFrame frame = new JFrame ("Quiz");

		JPanel panel = new JPanel ();
		frame.add (panel);

		JButton wButton = new JButton ("Launch Missiles");
		panel.add (wButton);

		JLabel text = new JLabel ("This is not a test");
		panel.add (text);

		wButton.addActionListener (
				new ActionListener()
				{
					int qNum = 1;
					Object selected;

					public void actionPerformed (ActionEvent e)
					{
						while(qNum <= 6)
						{
							String question = g.questions[qNum-1];
//							Object options[] = { "False", "True" };

							selected = JOptionPane.showInputDialog (
									frame,
									question,
									"Question " + qNum,
									1);
							
							if (selected == null)
								selected = "Cancelled";
							
//							selected = JOptionPane.showOptionDialog (
//									null,
//									question,
//									"Question " + qNum,
//									JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
//									null, options, null );

							g.answers[qNum-1]= selected.toString();
//							System.out.println(g);
							qNum++;
						}
						
						System.out.println(g);
						String result = g.calcScore();
						System.out.println(result);
						
					}
					
				});
		
		
//		String result = g.calcScore();
//		System.out.println(result);
		
		
		
		frame.setSize (200, 200);
		frame.setVisible (true);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}


	public String toString()
	{
		String outstr = "";

		outstr += "answers[] = ";
		for (int i = 0; i <  answers.length; i++)
			outstr += " " + answers[i];

		return outstr + "\n";
	}
	
	public String calcScore()
	{
		String txtScore = "";
		double score =0;
		for (int i=0; i < 6; i++) {
			if (answers[i].equals(corAns[i]))
			{
				score ++;
			}
		}
		
		score = score/6.0 *100;
		
		DecimalFormat percentFormat = new DecimalFormat("#.00");
		
		txtScore = percentFormat.format(score) + "% ";
		
		return txtScore;
		
	}

//		public void nextQuest(JButton button, int quesNum)
//		{
//			button.addActionListener(
//					new ActionListener()
//					{
//						int qNum = quesNum++;
//						Object selected;
//						
//						public void actionPerformed (ActionEvent e)
//						{
//							String question = g.questions[0];
//							Object options[] = { "False", "True" };
//
//							selected = JOptionPane.showOptionDialog (
//									null,
//									question,
//									"Question " + qNum,
//									JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
//									null, options, null );
//
//							g.answers[qNum-1]= selected.toString();
//							System.out.println(g);
//						}
//					});
//						
//					}
//					
//					
//					
//					
//		)
//		}


}



