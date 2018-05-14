import java.util.ArrayList;
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
	
	//variables for where to generate stairs and holes
	private int x_stair_loc;
	private int y_stair_loc;
	private int x_hole_loc;
	private int y_hole_loc;
	
	//Holder for all of the levels generated
	private int[][][] levels;
	
	//container for each level that is generated
	private int[][] level;
	
	//number of enemies to include in the board
	private int numEnemies = 10;
	
	
	public DungeonGenerator(int numLevels, int nBlocks, int numRooms) {
		this.numLevels=numLevels;
		this.nBlocks=nBlocks;
		this.numRooms= numRooms;
		
		levels= new int[numLevels][nBlocks][nBlocks];
		level = new int[nBlocks][nBlocks];
		
		initLevel();
		
		for(int i=0; i<numLevels; i++) {
			if (i==numLevels-1) {
				createFinalLevel(i);
			}
			else {
				numEnemies= numEnemies + 2*i;
				createLevel(i);
			}
		}
	}
	
	//create the final level
	public void createFinalLevel(int levelIndex) {
		int[][] currLevel= new int[nBlocks][nBlocks];
		
		for(int i=0; i<nBlocks; i++) {
			currLevel[i] = level[i].clone();
		}
		
		//unhide all and add Dragon Spawn
		for (int i =0; i<nBlocks; i++) {
			for (int j =0; j<nBlocks; j++) {
				currLevel[i][j]=currLevel[i][j]&(Board.UNHIDE);
				if (i ==nBlocks/2 && j== nBlocks/2) {
					currLevel[i][j]=currLevel[i][j]+Board.DRAGON_SPAWN;
				}
				if (i>nBlocks/7 && i< nBlocks*5/6 && j>nBlocks/7 && j<nBlocks/6*5) {
					currLevel[i][j]=currLevel[i][j]+Board.BLOCKED;
				}
			}
		}
		
		addStairs(currLevel,levelIndex);
		
		//add the level to the list of levels
		levels[levelIndex]=currLevel;
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
		
		//add stairs and hole
		addStairs(currLevel,levelIndex);
		Random rand = new Random();
		
		//add enemies
		int numSpiders = rand.nextInt(numEnemies/4)+2;
		int numBats = rand.nextInt(numEnemies/2)+2;
		int numSnakes = numEnemies-numSpiders-numBats;
			
		//add snake spawns
		for(int i = 0; i < numSnakes; i++) {
			int x = rand.nextInt(nBlocks-2)+1;
			int y = rand.nextInt(nBlocks-2)+1;
			placeEnemy(x,y,currLevel,Board.SNAKE_SPAWN);
		}
		
		//add bat spawns
		for(int i = 0; i < numBats; i++) {
			int x = rand.nextInt(nBlocks-2)+1;
			int y = rand.nextInt(nBlocks-2)+1;
			placeEnemy(x,y,currLevel,Board.BAT_SPAWN);
		}
				
		//add spider spawns
		for(int i = 0; i < numSpiders; i++) {
			int x = rand.nextInt(nBlocks-2)+1;
			int y = rand.nextInt(nBlocks-2)+1;
			placeEnemy(x,y,currLevel,Board.SPIDER_SPAWN);
		}
		
		//add the level to the list of levels
		levels[levelIndex]=currLevel;
	}
	
	
	private void addStairs(int[][] currLevel, int levelIndex) {
		Random rand = new Random();

		x_stair_loc = rand.nextInt(nBlocks);
		y_stair_loc = rand.nextInt(nBlocks);
		
		int[] stair = checkLoc(x_stair_loc,y_stair_loc,currLevel);
		x_stair_loc = stair[0];
		y_stair_loc = stair[1];
		
		if(levelIndex==0) {
			currLevel[x_stair_loc][y_stair_loc]=currLevel[x_stair_loc][y_stair_loc]+Board.UP_STAIRS;
		}
		else if(levelIndex==numLevels-1) {
			currLevel[nBlocks-1][nBlocks/2]=currLevel[nBlocks-1][nBlocks/2]+Board.DOWN_STAIRS;
		}
		else if(levelIndex==numLevels-2) {
			//add the specific location of this stairs
			x_stair_loc = nBlocks-1;
			y_stair_loc = nBlocks/2;
			currLevel[x_stair_loc][y_stair_loc]=currLevel[x_stair_loc][y_stair_loc]+Board.UP_STAIRS;
			currLevel[x_hole_loc][y_hole_loc]=currLevel[x_hole_loc][y_hole_loc]+Board.DOWN_STAIRS;
		}
		else {
			currLevel[x_stair_loc][y_stair_loc]=currLevel[x_stair_loc][y_stair_loc]+Board.UP_STAIRS;
			currLevel[x_hole_loc][y_hole_loc]=currLevel[x_hole_loc][y_hole_loc]+Board.DOWN_STAIRS;
		}
		
		x_hole_loc = x_stair_loc;
		y_hole_loc = y_stair_loc;
		
	}
	
	private int[] checkLoc(int xin, int yin, int[][] currLevel) {
		boolean placed = false;
		int x = xin;
		int y = yin;
		while(!placed) {
			if (((currLevel[x][y] & Board.ROOM_TILE)==0)){
				placed=true;
			}
			else if(y >= nBlocks-1)
			{
				y=0;
				x++;
			}
			else if(x >= nBlocks -1) {
				x=0;
			}
			else {
				y++;
			}
		}
		int[] result= {x,y};
		return result;
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
		
		//iterate through and fill internal gaps
		for (int x=1; x<room_size-1; x++) {
			for (int y=1; y<room_size-1; y++) {
				//check if surrounded on all four sides
				int up = y;
				int down = room_size-y;
				int left = x;
				int right = room_size-x;
				boolean surrounded = true;
				
				//if already a room tile, doesn't matter
				if ((room[x][y] & Board.ROOM_TILE)!=0) {
					surrounded = false;
				}
				//if not already a room tile, check for a hit of a room tile in each direction
				else {
					boolean uhit =false;
					for (int u = 0; u < up; u++) {
						if ((room[x][y-u] & Board.ROOM_TILE)!=0) {
							uhit = true;
						}
					}
					boolean dhit =false;
					for (int u = 0; u < down; u++) {
						if ((room[x][y+u] & Board.ROOM_TILE)!=0) {
							dhit = true;
						}
					}
					boolean lhit =false;
					for (int u = 0; u < left; u++) {
						if ((room[x-u][y] & Board.ROOM_TILE)!=0) {
							lhit = true;
						}
					}
					boolean rhit =false;
					for (int u = 0; u < right; u++) {
						if ((room[x+u][y] & Board.ROOM_TILE)!=0) {
							rhit = true;
						}
					}
					
					if (!(uhit && dhit && lhit && rhit)) {
						surrounded = false;
					}
				}
				
				if (surrounded) {
					room[x][y]= room[x][y]+Board.ROOM_TILE;
				}
			}
		}
		
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
	
	//tool to help place enemies within the level
	private void placeEnemy(int x, int y, int[][] level, int enemyType) {
		boolean placed = false;
		while(!placed) {
			//dont place on top of another enemy or directly around an up or down stair
			if (((level[x][y] & Board.SPIDER_SPAWN)!=0) ||
					((level[x][y] & Board.BAT_SPAWN)!=0) ||
					((level[x][y] & Board.SNAKE_SPAWN)!=0) ||
					((level[x][y] & Board.UP_STAIRS)!=0) ||
					((level[x][y] & Board.DOWN_STAIRS)!=0) ||
					((level[x+1][y] & Board.DOWN_STAIRS)!=0) ||
					((level[x-1][y] & Board.DOWN_STAIRS)!=0) ||
					((level[x][y+1] & Board.DOWN_STAIRS)!=0) ||
					((level[x][y-1] & Board.DOWN_STAIRS)!=0) ||
					((level[x+1][y] & Board.UP_STAIRS)!=0) ||
					((level[x-1][y] & Board.UP_STAIRS)!=0) ||
					((level[x][y+1] & Board.UP_STAIRS)!=0) ||
					((level[x][y-1] & Board.UP_STAIRS)!=0))
					 {
				if(y>=nBlocks-2) {
					y=1;
					x++;
				}
				if(x>=nBlocks-2) {
					x=1;
				}
				y++;
			}
			else {
				level[x][y]=level[x][y] + enemyType;
				placed = true;
			}
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
				level[i][j]=level[i][j]+Board.HIDDEN;
			}
		}
		
		
	}
	

	    		
}
