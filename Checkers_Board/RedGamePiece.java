import java.awt.*;
import java.awt.geom.*;

public class RedGamePiece extends GamePiece // child class
{
	public RedGamePiece (int row, int col, GameSquare[][] squares) 
	{
		super (row, col, Player.RED, squares);
	}

	public RedGamePiece (Position pos, GameSquare[][] squares) 
	{
		this (pos.r, pos.c, squares);
	}
	

	protected boolean validNonJump (Position targetPos)
	{
		Position dp = targetPos.offset (pos);
		int dr = dp.r;
		int dc = dp.c;

		if (((dr == -1) || (dr == 1 && isKing())) && ((dc == -1) || (dc == 1)))
		{
			if (squares[pos.r + dr][pos.c + dc].getPiece() == null)
				return true;
		}
		
		return false;
	}
	
	protected boolean validJump (Position targetPos)
	{
		Position dp = targetPos.offset (pos);
		int dr = dp.r;
		int dc = dp.c;
		
		if (((dr == -2) || (dr == 2 && isKing())) && ((dc == -2) || (dc == 2)))
		{
			if ((squares[pos.r + dr/2][pos.c + dc/2].getPiece().getClass() != getClass()) && (squares[pos.r + dr][pos.c + dc].getPiece() == null)) // change null to be other color of square
				return true;
		}
		
		
		return false;
	}
	
	
	protected boolean hasJump ()
	{
		
		boolean result1 = false;
		boolean result2 = false;
		boolean result3 = false;
		boolean result4 = false;
		boolean resultSubmit = false;

		try {
			result1 = validJump(new Position(pos.r-2, pos.c+2));
		}
		catch (ArrayIndexOutOfBoundsException e) {
				//System.out.println("arrayError");
				result1 = false;
			}
		catch (NullPointerException e)
			{
				//System.out.println("pointerError");
				result1 = false;
			}
		finally {} 
		
		
		try {
			result2 = validJump(new Position(pos.r-2, pos.c-2));
		}
		catch (ArrayIndexOutOfBoundsException e) {
				//System.out.println("arrayError");
				result2 = false;
			}
		catch (NullPointerException e)
			{
				//System.out.println("pointerError");
				result2 = false;
			}
		finally {} 
		
		
		try {
			result3 = validJump(new Position(pos.r+2, pos.c+2));
		}
		catch (ArrayIndexOutOfBoundsException e) {
				//System.out.println("arrayError");
				result3 = false;
			}
		catch (NullPointerException e)
			{
				//System.out.println("pointerError");
				result3 = false;
			}
		finally {} 
		
		
		try {
			result4 = validJump(new Position(pos.r+2, pos.c-2));
		}
		catch (ArrayIndexOutOfBoundsException e) {
				//System.out.println("arrayError");
				result4 = false;
			}
		catch (NullPointerException e)
			{
				//System.out.println("pointerError");
				result4 = false;
			}
		finally {} 
		
		
		
		 
		 
		resultSubmit = result1||result2||result3||result4;

		//System.out.println("result");
		return resultSubmit;
		
	}
	
	public void drawPiece (Graphics2D g2, int x, int y, int width, int height)
	{
		Ellipse2D.Double outline = new Ellipse2D.Double (x + width * DIST_FROM_EDGE + LINE_WIDTH / 2,
														 y + height * DIST_FROM_EDGE + LINE_WIDTH / 2,
														 width * (1 - 2 * DIST_FROM_EDGE) - LINE_WIDTH,
														 height * (1 - 2 * DIST_FROM_EDGE) - LINE_WIDTH);

		
		int[] crownX = {50, 60, 65, 80, 75, 25, 20, 35, 40};
		int[] crownY = {50, 30, 52, 40, 65, 65, 30, 52, 30};
		
		for (int i = 0 ; i < crownX.length ; i++)
		{
			crownX[i]=crownX[i]*width/100;
			crownY[i]=crownY[i]*height/100;
		}
		
		Polygon crown = new Polygon(crownX, crownY, crownX.length);
		
		
		
		g2.setColor (Color.RED);

		BasicStroke stroke = new BasicStroke (LINE_WIDTH);
		g2.setStroke (stroke);
		g2.fill (outline);

		if (isKing())
		{
			g2.setColor(Color.YELLOW);
			g2.fill(crown);
			
		}
		
		if (selected)
		{
			g2.setColor (Color.GREEN);
			g2.draw (outline);
		}
	}
	
	public void setKing(Position targetPos)
	{
		if (!isKing())
		{
			if (targetPos.r == 0)
				king = true;
		}
		
	}


	
}
