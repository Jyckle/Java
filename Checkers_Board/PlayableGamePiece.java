
/**
   Defines the interface and common functionality of a Checkers game piece.
*/
public interface PlayableGamePiece // interface
{
	public boolean validMove (Position targetPos);
	
	public void move (Position newPos);
	
	public boolean isKing ();
	
	public void setPieceSelected (boolean b);
	public boolean isPieceSelected ();
}
