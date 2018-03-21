import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class IconTester
{
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ();
		
		int carSize = 750;
		
		final Icon car = new CarIcon(carSize);
		final JLabel label = new JLabel (car);
		frame.add (label);

//		ImageIcon image = new ImageIcon ("157055.jpg");
//		JLabel imgLabel = new JLabel (image);
//		JLabel msgLabel = new JLabel ("Cheers!");		
//		JPanel panel = new JPanel();
//		frame.add(panel);
//		panel.add(msgLabel);
//		panel.add(imgLabel);
		
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize(carSize*8/7,carSize*2/3);
		frame.setLocationRelativeTo(null);
		frame.setVisible (true);
	}
}

