import java.util.ArrayList;
import java.util.Collections;

public class DecimalRadixConv {
	
	private int base;
	
	//constructor
	public DecimalRadixConv(int baseR){
		base = baseR;
	}
	
	
	public String ToRadix(int decNum){
		String radixNum = "";
		int q;
		int r;
		char x;
		do {
			q = decNum/base;
			r = decNum % base;
			x = singleDigitRepr(r);
			radixNum = x + radixNum;
			decNum = q;
		}
		while (q!=0);
		
		return radixNum;
	}
	
//	public String ToDecimal(String radNum) {
//		String decNum = "";
//		for(int i = 0; i < radNum.length(); i++) {
//			char cChar = radNum.charAt(i);
//			if(cChar >= 'A' && cChar <= 'Z') {
//				
//			}
//			else if(Integer.parseInt(cChar).isNumeric())
//		}
//		
//		return decNum;
//		
//	}
	
	private char singleDigitRepr(int num)
	{
		if(num <10)
			return (char) ('0' + num);
		else if(num < (10+26))
			return (char) ('A'+ (num - 10));
		else
		{
			System.err.println("Digit too large (base 36 or less please");
			return 0;
		}
		
	}
	
	
	
	
}
