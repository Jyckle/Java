import java.util.Arrays;
import java.util.Random;

public class DungeonGenerator {
	//This class is designed to build the various levels of the dungeon procedurally
	
	//number of attempts to make at placing rooms
	private final static int ATTEMPTS = 50;
	private boolean attempts_remaining = true;
	private final static int NUM_ROOMS = 15;
	
	//number of levels to generate
	private int numLevels = 1;
	
	//declares the variables to be used for the screen size
	private int nBlocks = 30;
	private int blockSize = 24;
	private int screenSize = nBlocks*blockSize;
	
	//variables for where to generate stairs and holes
	private int x_stair_loc;
	private int y_stair_loc;
	private int x_hole_loc;
	private int y_hole_loc;
	
	//Holder for all of the levels generated
	private int[][][] levels;
	
	//container for each level that is generated
	private int[][] level;
	private boolean created= true;
	
	//container for each room
	private int[][] room;
	
	
	public DungeonGenerator(int numLevels, int nBlocks, int blockSize) {
		this.numLevels=numLevels;
		this.nBlocks=nBlocks;
		this.blockSize=blockSize;
		this.screenSize=nBlocks*blockSize;
		
		levels= new int[numLevels][nBlocks][nBlocks];
		level = new int[nBlocks][nBlocks];
		
		initLevel();
		
		for(int i=0; i<numLevels; i++) {
			createLevel(i);
		}
	}
	
	//method for user to get output
	public int[][][] getLevels() {
		return levels;
	}
	
	//method to actually run creation of levels
	private void createLevel(int levelIndex) {		
		
		int[][] currLevel= new int[nBlocks][nBlocks];
		for(int i=0; i<nBlocks; i++) {
			currLevel[i] = level[i].clone();
		}
//		levels[levelIndex] = Arrays.copyOf(level, level.length);
		
//		for (int i =0; i<nBlocks; i++) {
//			for (int j =0; j<nBlocks; j++) {
//				level[i][j]=level[i][j] & (Board.CLEAR_ALL-Board.UP_STAIRS-Board.DOWN_STAIRS);
//			}
//		}
		
		//add stairs and ladder
		Random rand = new Random();

		x_stair_loc = rand.nextInt(nBlocks-1) + 1;
		y_stair_loc = rand.nextInt(nBlocks-1) + 1;
		
		x_hole_loc = rand.nextInt(nBlocks-1) + 1;
		y_hole_loc = rand.nextInt(nBlocks-1) + 1;
		
		if(levelIndex==0) {
			currLevel[x_stair_loc][y_stair_loc]=currLevel[x_stair_loc][y_stair_loc]+Board.UP_STAIRS;
		}
		else if(levelIndex==numLevels-1) {
			currLevel[x_hole_loc][y_hole_loc]=currLevel[x_hole_loc][y_hole_loc]+Board.DOWN_STAIRS;
		}
		else {
			currLevel[x_stair_loc][y_stair_loc]=currLevel[x_stair_loc][y_stair_loc]+Board.UP_STAIRS;
			currLevel[x_hole_loc][y_hole_loc]=currLevel[x_hole_loc][y_hole_loc]+Board.DOWN_STAIRS;
		}
		
		
		//add the level to the list of levels
		levels[levelIndex]=currLevel;
	}
	
	
	private int[][] buildRoom(){
		
		return room;
	}
	
	
	
	
	//set a border around the outside of the level
	private void initLevel() {
		
		for (int i =0; i<nBlocks; i++) {
			for (int j =0; j<nBlocks; j++) {
				if(i==0) {
					level[i][j]=level[i][j]+Board.TOP_WALL;
				}
				if(i==nBlocks-1) {
					level[i][j]=level[i][j]+Board.BOTTOM_WALL;
				}
				if(j==0) {
					level[i][j]=level[i][j]+Board.LEFT_WALL;
				}
				if(j==nBlocks-1) {
					level[i][j]=level[i][j]+Board.RIGHT_WALL;
				}
				if(i!=0 && i!=nBlocks-1 &&j!=0 && j!=nBlocks-1) {
					level[i][j]=level[i][j]+Board.YUMMY_BIT;
				}
				
				level[i][j]=level[i][j]+Board.HIDDEN;
			}
		}
		
		
	}
	
	private int room7[][]=
		{
				{ 0, 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0, 0} 
		};
	
	private int room6[][]=
		{
				{ 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0, 0} 
		};
	
	private int room5[][]=
		{
				{ 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0}, 
				{ 0, 0, 0, 0, 0},  
				{ 0, 0, 0, 0, 0} 
		};
	
	private int room4[][]=
		{
				{ 0, 0, 0, 0}, 
				{ 0, 0, 0, 0}, 
				{ 0, 0, 0, 0}, 
				{ 0, 0, 0, 0},  
				{ 0, 0, 0, 0} 
		};
	    		
}
