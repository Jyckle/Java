
public enum Player {
	EMPTY, RED, BLACK;
	
	public static Player switchPlayer (Player p)
	{
		if (p == Player.RED)
			return Player.BLACK;
		else if (p == Player.BLACK)
			return Player.RED;
		else
			return Player.EMPTY;
	}
	
	public static Player getOpponent (Player p) {
		return switchPlayer (p);
	}
}
