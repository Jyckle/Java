import java.awt.Image;

import javax.swing.ImageIcon;

public class Spider extends Enemy {
	private int attackPower = 20;
	private int attackTime = 20;
	private int time=0;
	
	private static Image Images[] = {new ImageIcon("char_pics/rSpider.png").getImage(), 
			new ImageIcon("char_pics/lSpider.png").getImage(),
			new ImageIcon("char_pics/dSpider.png").getImage(),
			new ImageIcon("char_pics/uSpider.png").getImage()};
	
	private static Image attackImages[] = {new ImageIcon("char_pics/web.png").getImage(), new ImageIcon("char_pics/web.png").getImage(), 
			new ImageIcon("char_pics/web.png").getImage(), new ImageIcon("char_pics/web.png").getImage()};
	
	public Spider(int screenData[][], int level, int brd_x, int brd_y,
			int width, Character target) {
		
		super(screenData, level, brd_x, brd_y, width, target, Images);
		
		this.movementSpeed = 1;
		this.pauseTime = 15;
		this.health = 30;
		this.damage = 50;
	}
	
	public String getType() {
		return "Spider";
	}
	
	@Override
	public Projectile attack() {
		return new Projectile(dir,x,y,attackPower,attackImages);
	}

}
