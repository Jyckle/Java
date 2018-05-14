import java.awt.Image;

import javax.swing.ImageIcon;

public class Snake extends Enemy {
	private static Image Images[] = {new ImageIcon("char_pics/snakeRight.png").getImage(), 
			new ImageIcon("char_pics/snakeLeft.png").getImage(),
			new ImageIcon("char_pics/snakeDown.png").getImage(),
			new ImageIcon("char_pics/snakeUp.png").getImage()};
	
	public Snake(int screenData[][], int level, int brd_x, int brd_y,
			int width, Character target) {
		
		super(screenData, level, brd_x, brd_y, width, target, Images);
		
		this.movementSpeed = 1;
		this.pauseTime = 1;
		this.health = 20;
		this.damage= 40;
	}
	
	public String getType() {
		return "Snake";
	}
	
	@Override
	public Projectile attack() {
		// Throwaway Projectile
		return new Projectile(1,1,1,1,null);
	}

}
