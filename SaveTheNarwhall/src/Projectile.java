import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Projectile implements MoveableShape {
	private int dir;
	private int x;
	private int y;
	private int damage;
	private Image Images[];
	
	
	
	public Projectile(int dir, int x, int y, int damage, Image[] images) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.damage=damage;
		this.Images = images;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(Images[dir], x ,y, Board.BLOCK_SIZE, Board.BLOCK_SIZE,null);
	}

	@Override
	public void move() {
		if (dir == 0) {
			//left 
			x += -1*Board.BLOCK_SIZE/3;
		}   
        else if (dir == 1){
			//right
        	x += 1*Board.BLOCK_SIZE/3;
		}   
        else if (dir == 2){
			//up
        	y += -1*Board.BLOCK_SIZE/3;
		}
        else if (dir == 3){
			//down
        	y += 1*Board.BLOCK_SIZE/3;
		}  

	}

	@Override
	public boolean contains(int r, int c) {
		if (r > (x - 12) && r < (x + 12)
				&& c > (y - 12) && c < y + 12)
			return true;
		else
			return false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDir() {
		return dir;
	}
	
	public int getDamage() {
		return damage;
	}
}
