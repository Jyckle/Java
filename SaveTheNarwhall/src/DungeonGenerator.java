import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DungeonGenerator {
	//This class is designed to build the various levels of the dungeon procedurally
	
	//number of attempts to make at placing rooms
	private final static int MAX_ATTEMPTS = 50;
	private final static int MAX_ROOM_SIZE = 6;
	private int numRooms = 15;
	
	//number of levels to generate
	private int numLevels = 1;
	
	//declares the variables to be used for the screen size
	private int nBlocks = 30;
	private int blockSize = 24;
	
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
	
	
	public DungeonGenerator(int numLevels, int nBlocks, int blockSize, int numRooms) {
		this.numLevels=numLevels;
		this.nBlocks=nBlocks;
		this.blockSize=blockSize;
		this.numRooms= numRooms;
		
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
		
		int[][][] rooms= buildRooms();
		for (int i =0; i<numRooms;i++) {
			placeRoom(rooms[i],currLevel);
		}
		
		//add stairs and ladder
		Random rand = new Random();

		x_stair_loc = rand.nextInt(nBlocks);
		y_stair_loc = rand.nextInt(nBlocks);
		
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
		
		x_hole_loc = x_stair_loc;
		y_hole_loc = y_stair_loc;
		
		//add the level to the list of levels
		levels[levelIndex]=currLevel;
	}
	
	
	private int[][][] buildRooms(){
		int[][][] rooms = new int[numRooms][][];
		for (int i=0; i<numRooms; i++) {
			rooms[i]=randomRoom();
		}
		return rooms;
	}
	
	private int[][] randomRoom(){
		//randomly pick a room size between 4 and 7
		Random rand = new Random();
		int room_size = rand.nextInt(MAX_ROOM_SIZE) + 4;
		//set the max number of tiles to 2 less than that
		int maxTiles = room_size-2;
		//create an empty array of the correct size
		int[][] room = new int[room_size][room_size];
		
		//iterate through the inner 5 rows
		for (int x=1; x<room_size-1; x++) {
			//set the number of tiles in this row
			int tiles= rand.nextInt(maxTiles)+1;
			//for each tile, randomly pick a location
			for (int i=0; i<tiles; i++) {
				int y = rand.nextInt(maxTiles)+1;
				
				boolean placed=false;
				//test until tile is placed
				while (!placed) {
					//if very first tile just place
					if (x==1&&i==0) {
						room[x][y]= room[x][y]+Board.ROOM_TILE;
						placed = true;
					}
					//if already a room tile there, don't place
					else if((room[x][y] & Board.ROOM_TILE)!=0) {
						if(y>=maxTiles)
							y=1;
						else
							y=y+1;
					}
					//if not a room tile there, but none in connecting spaces, still don't place
					else if(((room[x+1][y]&Board.ROOM_TILE)==0)&&
							((room[x-1][y]&Board.ROOM_TILE)==0)&&
							((room[x][y+1]&Board.ROOM_TILE)==0)&&
							((room[x][y-1]&Board.ROOM_TILE)==0)) {
						
						if(y>=maxTiles)
							y=1;
						else
							y=y+1;
					}
					//otherwise place it
					else {
						room[x][y]= room[x][y]+Board.ROOM_TILE;
						placed = true;
					}
				}
			}		
		}
		
//		//iterate through and fill internal gaps
//		for (int x=1; x<room_size-1; x++) {
//			for (int y=1; y<room_size-1; y++) {
//				if(((room[x][y] & Board.ROOM_TILE)==0) &&
//					((room[x+1][y] & Board.ROOM_TILE)!=0) &&
//					((room[x-1][y] & Board.ROOM_TILE)!=0) &&
//					((((room[x][y+1] & Board.ROOM_TILE)!=0)) || ((room[x][y-1] & Board.ROOM_TILE)!=0))) {
//					room[x][y]= room[x][y]+Board.ROOM_TILE;
//				}
//			}
//		}
		
		for (int i =0; i < room_size; i++) {
			for (int j=0; j < room_size; j++) {
				if ((room[i][j] & Board.ROOM_TILE)==0) {
					room[i][j] = room[i][j] + Board.OUT_ROOM_TILE;
				}
			}
		}
		
		addWalls(room, room_size);
		
		addDoors(room, room_size);
		
		return room;
	}
	
	//add the walls to a room
	private void addWalls(int[][] room, int room_size) {
		
		for (int i =0; i < room_size; i++) {
			for (int j=0; j < room_size; j++) {
				//for room tiles, if bordered by outer tile, add wall along that side
				if ((room[i][j] & Board.ROOM_TILE)!=0) {
					if ((room[i-1][j] & Board.OUT_ROOM_TILE)!=0) {
						room[i][j]=room[i][j] + Board.TOP_WALL;
					}
					if ((room[i+1][j] & Board.OUT_ROOM_TILE)!=0) {
						room[i][j]=room[i][j] + Board.BOTTOM_WALL;
					}
					if ((room[i][j-1] & Board.OUT_ROOM_TILE)!=0) {
						room[i][j]=room[i][j] + Board.LEFT_WALL;
					}
					if ((room[i][j+1] & Board.OUT_ROOM_TILE)!=0) {
						room[i][j]=room[i][j] + Board.RIGHT_WALL;
					}
					
				}
				else if ((room[i][j] & Board.OUT_ROOM_TILE)!=0) {
					if(i-1 >=0) {
						if ((room[i-1][j] & Board.ROOM_TILE)!=0) {
							room[i][j]=room[i][j] + Board.TOP_WALL;
						}
					}
					
					if(i+1 <= room_size-1) {
						if ((room[i+1][j] & Board.ROOM_TILE)!=0) {
							room[i][j]=room[i][j] + Board.BOTTOM_WALL;
						}
					}
					
					if(j-1 >=0) {
						if ((room[i][j-1] & Board.ROOM_TILE)!=0) {
							room[i][j]=room[i][j] + Board.LEFT_WALL;
						}
					}
					
					if(j+1 <= room_size-1) {
						if ((room[i][j+1] & Board.ROOM_TILE)!=0) {
							room[i][j]=room[i][j] + Board.RIGHT_WALL;
						}
					}
				}
			}
		}
		
	}
	
	//add a door to left or right side and top or bottom side
	public void addDoors(int[][] room, int room_size) {
		Random rand = new Random();

		int side1 = rand.nextInt(2);
		int side2 = rand.nextInt(2);
		ArrayList<int[]> left = new ArrayList<int[]>();
		ArrayList<int[]> right = new ArrayList<int[]>();
		ArrayList<int[]> up = new ArrayList<int[]>();
		ArrayList<int[]> down = new ArrayList<int[]>();


		for (int i =0; i < room_size; i++) {
			for (int j=0; j < room_size; j++) {
				//if left
				if (side1 ==0) {
					if (((room[i][j] & Board.ROOM_TILE)!=0) && ((room[i][j] & Board.LEFT_WALL)!=0) ) {
						int[] val = {i,j};
						left.add(val);
					}
				}
				//if right
				if (side1 ==1) {
					if (((room[i][j] & Board.ROOM_TILE)!=0) && ((room[i][j] & Board.RIGHT_WALL)!=0) ) {
						int[] val = {i,j};
						right.add(val);
					}
				}
				//if up
				if (side2==0) {
					if (((room[i][j] & Board.ROOM_TILE)!=0) && ((room[i][j] & Board.TOP_WALL)!=0) ) {
						int[] val = {i,j};
						up.add(val);
					}
				}
				//if down
				if (side2==1) {
					if (((room[i][j] & Board.ROOM_TILE)!=0) && ((room[i][j] & Board.BOTTOM_WALL)!=0) ) {
						int[] val = {i,j};
						down.add(val);
					}
				}
			}
		}
		
		if (side1 ==0) {
			int index = rand.nextInt(left.size());
			int[] arr = left.get(index);
			int i = arr[0];
			int j = arr[1];
			room[i][j]=room[i][j] & (Board.CLEAR_ALL-Board.LEFT_WALL);
			room[i][j-1]= room[i][j-1] & (Board.CLEAR_ALL-Board.RIGHT_WALL);
		}
		if (side1 ==1) {
			int index = rand.nextInt(right.size());
			int[] arr = right.get(index);
			int i = arr[0];
			int j = arr[1];
			room[i][j]=room[i][j] & (Board.CLEAR_ALL-Board.RIGHT_WALL);
			room[i][j+1]= room[i][j+1] & (Board.CLEAR_ALL-Board.LEFT_WALL);
		}
		if (side2 ==0) {
			int index = rand.nextInt(up.size());
			int[] arr = up.get(index);
			int i = arr[0];
			int j = arr[1];
			room[i][j]=room[i][j] & (Board.CLEAR_ALL-Board.TOP_WALL);
			room[i-1][j]= room[i-1][j] & (Board.CLEAR_ALL-Board.BOTTOM_WALL);
		}
		if (side2 ==1) {
			int index = rand.nextInt(down.size());
			int[] arr = down.get(index);
			int i = arr[0];
			int j = arr[1];
			room[i][j]=room[i][j] & (Board.CLEAR_ALL-Board.BOTTOM_WALL);
			room[i+1][j]= room[i+1][j] & (Board.CLEAR_ALL-Board.TOP_WALL);
		}
		
	}
		
	
	//place the room in the level
	private void placeRoom(int[][] room, int[][] currLevel) {
		int width = room.length;
		int height = room[0].length;
		
		Random rand = new Random();
		
		int attempts= 0;
		boolean placed = false;
		
		//try until the room is placed or no more attempts are allowed
		while(attempts < MAX_ATTEMPTS && !placed) {
			int x = rand.nextInt(nBlocks-width);
			int y = rand.nextInt(nBlocks-height);
			
			boolean failed = false;
			
			//iterate through the region where the room should be placed and check for conflicts
			for(int a = 0; a<width; a++) {
				if (failed) {
					break;
				}
				for(int b=0; b<height; b++) {
					if ((currLevel[x+a][y+b] & Board.ROOM_TILE) !=0 || (currLevel[x+a][y+b] & Board.OUT_ROOM_TILE) !=0) {
						failed = true;
						break;
					}
				}
			}
			
			//if conflicts are present, don't place
			if (failed) {
				attempts++;
			}
			//otherwise place
			else {
				for(int a = 0; a<width; a++) {
					for(int b=0; b<height; b++) {
						currLevel[x+a][y+b]=currLevel[x+a][y+b]+room[a][b];
					}
				}
				placed = true;
			}
				
			
			
			
		}
		
		
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
	

	    		
}
