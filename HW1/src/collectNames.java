import java.util.ArrayList;
import java.util.Scanner;

public class collectNames {
	
	private ArrayList<String> nms;
	
	public ArrayList<String> storeNames() {
		
		nms = new ArrayList<String>();
		
		Scanner in = new Scanner(System.in);
		String check = new String();
		
		//Obtain first value
		System.out.print("Enter a name (or type 'exit' to quit): ");
		check = in.nextLine();
		
		while (check.equals("exit")== false)
		{
			nms.add(check);
			System.out.print("Enter a name (or type 'exit' to quit): ");
			check = in.nextLine();
		}
		
		in.close();
		
		return nms;
	}

}
