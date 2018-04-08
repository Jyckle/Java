import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
   A component that shows a scene composed of shapes.
 */
@SuppressWarnings("serial")
public class CheckerBoard extends JPanel // child class
{
	protected final static int SQUARES_1D = 8;

	private final GameSquare[][] squares;
	
	private int RedPieces = 12;
	private int BlackPieces = 12;

	private Player player;
	private GamePiece selectedPiece = null;
	Color BROWN1 = new Color(128,88,7); //#3
	Color BROWN2 = new Color(240,194,101);
	
	public CheckerBoard ()
	{
		super ();

		setLayout (new GridLayout (8,8,0,0));
		
		squares = new GameSquare[SQUARES_1D][SQUARES_1D];

		int numPieceSpots = 0;
		
		player = Player.RED;


		for (int r = 0; r < SQUARES_1D; r++)
			for (int c = 0; c < SQUARES_1D; c++)
			{
				if ((r + c) % 2 == 1)
					squares[r][c] = new GameSquare (r, c, BROWN1);
					
				
				else
				{
					squares[r][c] = new GameSquare (r, c, BROWN2);

					if (numPieceSpots >= 20) // counts from top going down to the red pieces
						squares[r][c].setPiece (new RedGamePiece (r, c, squares));

					if (numPieceSpots <= 11) // counts from top going down to the black squares pieces
						squares[r][c].setPiece (new BlackGamePiece (r, c, squares));
					
					numPieceSpots++;

					squares[r][c].addMouseListener (new GameSquareMouseListener (squares[r][c]));  // 32 game square mouse listeners (96?)
				}

				add (squares[r][c]);
			}
	}
	

	public class GameSquareMouseListener extends MouseAdapter // MouseListener each mouse adapter implements 3 listeners // inner class // child class
	{
		GameSquare sq;

		public GameSquareMouseListener (GameSquare sq) {
			this.sq = sq;
		}
		
		public void mousePressed (MouseEvent event)
		{
			Point mousePoint = event.getPoint();

			if (sq.contains (mousePoint))
			{
				if (selectedPiece == null)
				{
					if ((sq.getPiece() != null) && (sq.getPiece().getPlayer() == player))
					{
						selectedPiece = sq.getPiece();
						sq.getPiece().setPieceSelected (true);
					}
				}
				else
				{
					Position pos = sq.getPosition();

					if (pos.equals (selectedPiece.pos()))
					{
						selectedPiece.setPieceSelected (false);	 // de-select piece
						selectedPiece = null;					 //  "          "
					}
					else if (selectedPiece.validMove (pos))
					{
						selectedPiece.move (pos);				 // move selected piece
						
						player = Player.switchPlayer(player);
						if ((selectedPiece.getRemoved()) && (player == Player.BLACK))
						{
							BlackPieces = BlackPieces -1;
						}
						else if ((selectedPiece.getRemoved()) && (player == Player.RED))
						{
							RedPieces = RedPieces -1;
						}
						
						selectedPiece.setPieceSelected (false);	 // de-select piece
						selectedPiece = null;
						checkGameEnd();
					}
				}
			}
			
			repaint();
		}
	}
	
	public void checkGameEnd()
	{
		repaint();
		if (RedPieces == 0)
		{
			Object[] options = {"Yes, play again",
            "No, I'm done"};
			int n = JOptionPane.showOptionDialog(null,
					"Would you like to play again?",
					"Black Wins!",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[1]);
			if (n == 0)
			{
				reset();
				JOptionPane.showMessageDialog(null, "Game Reset!");
			}
			else if (n==1)
			{
				Checkers.getFrame().dispose();
			}
		}
		
		else if (BlackPieces == 0)
		{
			Object[] options = {"Yes, play again",
            "No, I'm done"};
			int n = JOptionPane.showOptionDialog(null,
					"Would you like to play again?",
					"Red Wins!",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[1]);
			if (n == 0)
			{
				reset();
				JOptionPane.showMessageDialog(null, "Game Reset!");
				
			}
			else if (n==1)
			{
				Checkers.getFrame().dispose();
			}
		}
	
	}
	
	public void reset() {
		RedPieces = 12;
		BlackPieces = 12;
		
		int numPieceSpots = 0;

		player = Player.RED;


		for (int r = 0; r < SQUARES_1D; r++)
			for (int c = 0; c < SQUARES_1D; c++)
			{
				squares[r][c].removePiece();
				//add (squares[r][c]);
			}
		
		for (int r = 0; r < SQUARES_1D; r++)
			for (int c = 0; c < SQUARES_1D; c++)
			{
				
				if ((r + c) % 2 != 1)
				{
					if (numPieceSpots >= 20) // counts from top going down to the red pieces
						squares[r][c].setPiece (new RedGamePiece (r, c, squares));

					if (numPieceSpots <= 11) // counts from top going down to the black squares pieces
						squares[r][c].setPiece (new BlackGamePiece (r, c, squares));

					numPieceSpots++;

					//squares[r][c].addMouseListener (new GameSquareMouseListener (squares[r][c]));  // 32 game square mouse listeners (96?)
				}

				add (squares[r][c]);
			}
		repaint();
	}
}
