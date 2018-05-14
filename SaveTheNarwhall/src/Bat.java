import java.awt.Image;

import javax.swing.ImageIcon;

public class Bat extends Enemy {
	
	private static Image Images[] = {new ImageIcon("char_pics/rBat.png").getImage(), 
			new ImageIcon("char_pics/lBat.png").getImage(),
			new ImageIcon("char_pics/dBat.png").getImage(),
			new ImageIcon("char_pics/uBat.png").getImage()};
	
	public Bat(int screenData[][], int level, int brd_x, int brd_y,
			int width, Character target) {
		
		super(screenData, level, brd_x, brd_y, width, target, Images);
		
		this.movementSpeed = 5;
		this.pauseTime = 20;
		this.health= 10;
		this.damage=100;
	}
	
	public String getType() {
		return "Bat";
	}
	
	@Override
	public Projectile attack() {
		// Throwaway Projectile
		return new Projectile(1,1,1,1,null);

	}

}
