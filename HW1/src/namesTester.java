import java.util.ArrayList;
import java.util.Collections;

public class namesTester {

	public static void main(String[] args) {
		collectNames cn = new collectNames();
		
		ArrayList<String> sn = cn.storeNames();
		Collections.sort(sn);
				
		System.out.println(sn);

	}

}
