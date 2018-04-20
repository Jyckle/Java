import java.awt.*;

public abstract class GamePiece implements PlayableGamePiece // implementation // parent to redgamepieces // abstract
// follows the template class
// first off this is an abstract class 
// second there is at least one method (move) that defines a pattern of methods to be called that can be implemented differently for the colors
// ie red going up and black going down
{
	protected final static double DIST_FROM_EDGE = 0.1;
	protected final static int LINE_WIDTH = 5;

	protected boolean selected = false;
	protected Position pos = null;
	protected Player player = Player.EMPTY;
	protected boolean king = false;
	protected boolean removed = false;  //added
	
	protected GameSquare[][] squares;

	public GamePiece (int row, int col, Player p, GameSquare[][] squares) {
		this.squares = squares;
		pos = new Position (row, col);
		player = p;
	}

	public GamePiece (Position pos, Player p, GameSquare[][] squares) {
		this (pos.r, pos.c, p, squares);
	}

	public Position pos () {
		return pos;
	}

	public Player getPlayer () 
	{
		return player;
	}
	
	public boolean isKing () 
	{
		return king;
	}

	public void setPieceSelected (boolean b)
	{
		if ((b == true) && (player != Player.EMPTY))
			selected = true;
		else
			selected = false;
	}
	

	public boolean isPieceSelected() 
	{
		return selected;
	}

	public boolean validMove (Position targetPos)
	{
		if (validNonJump (targetPos))
			return true;
		else if (validJump (targetPos))
			return true;
				
		return false;
	}
	
	protected abstract boolean validNonJump (Position targetPos);	 // abstract
	
	protected abstract boolean validJump (Position targetPos);
	
	protected abstract void setKing(Position targetPos);
	
	protected abstract boolean hasJump();
	
	public void move (Position targetPos)
	{
		if (validNonJump (targetPos))
		{
			squares[pos.r][pos.c].removePiece ();
			pos = targetPos;
			squares[targetPos.r][targetPos.c].setPiece (this);
			setKing(targetPos);
			removed=false;
		}
		else if (validJump (targetPos))
		{
			squares[(pos.r+targetPos.r)/2][(pos.c+targetPos.c)/2].removePiece();
			squares[pos.r][pos.c].removePiece();
			removed = true;
			pos = targetPos;
			squares[targetPos.r][targetPos.c].setPiece(this);
			setKing(targetPos);
			
		}
		
		
		
	}
	
	public abstract void drawPiece (Graphics2D g2, int x, int y, int width, int height);
	
	public boolean getRemoved()
	{
		return removed;
	}
}
