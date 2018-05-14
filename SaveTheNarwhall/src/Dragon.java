import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Dragon extends Enemy {
	
	private int attackPower =150;
	
	private static Image Images[] = {new ImageIcon("char_pics/dragon.png").getImage(), 
			new ImageIcon("char_pics/dragon.png").getImage(),
			new ImageIcon("char_pics/dragon.png").getImage(),
			new ImageIcon("char_pics/dragon.png").getImage()};
	
	private static Image attackImages[] = {new ImageIcon("char_pics/fire.png").getImage(), 
			new ImageIcon("char_pics/fire.png").getImage(),
			new ImageIcon("char_pics/fire.png").getImage(),
			new ImageIcon("char_pics/fire.png").getImage()};
	
	public Dragon(int screenData[][], int level, int brd_x, int brd_y,
			int width, Character target) {
		
		super(screenData, level, brd_x, brd_y, width, target, Images);
		
		this.movementSpeed = 0;
		this.pauseTime = 20;
		this.health = 500;
	}
	
	@Override
	public Projectile attack() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int choose = rand.nextInt(2);
		dir = rand.nextInt(4);
		int cx = rand.nextInt(20*24)+x;
		int cy= rand.nextInt(20*24)+y;
		return new Projectile(dir,cx,cy,attackPower,attackImages);
		
	}
	
	public String getType() {
		return "Dragon";
	}
	
	public boolean contains (int r, int c) {
		if (r > (x - 12) && r < (x + 12+19*24)
				&& c > (y - 12) && c < y + 12+19*24)
			return true;
		else
			return false;
	}

}
