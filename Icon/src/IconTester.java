import javax.swing.*;

public class IconTester {
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(
				null,
				"LOOK AT IT",
				"LOOK AT MY PRETTY HAT",
				JOptionPane.INFORMATION_MESSAGE,
				new TopHatIcon(500,300));
//				new ImageIcon("Wednesday.png"));
//				new MarsIcon(500));
	}
}
