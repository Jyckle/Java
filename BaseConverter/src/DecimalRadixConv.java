import java.util.ArrayList;

public class DecimalRadixConv {
	
	private int base;
	
	//constructor
	public DecimalRadixConv(int baseR){
		base = baseR;
	}
	
	
	public ArrayList<Character> DecimalToRadix(int decNum){
		ArrayList<Character> radixNum = new ArrayList<Character>();
		int q;
		int r;
		do {
			q = decNum/base;
			r = decNum % base;
			radixNum.add((char) r);
		}
		while (q!=0);
		
		return radixNum;
	}
	
	
	
	
}
