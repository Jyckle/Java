
public class LevelTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DungeonGenerator d = new DungeonGenerator(3,30,24,15);
		int levels[][][]= d.getLevels();
		
		for (int z=0; z<3; z++) {
			for (int i=0; i< 30 ; i++)
	        {
	            for (int j=0; j < 30 ; j++)
	                System.out.print(levels[z][i][j] + " ");
	 
	            System.out.println();
	        }
			 System.out.println();
		}
	}

}
