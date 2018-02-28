
public class RadixDecimalConv {
	
	private int base = 10;
	private char maxDigit = '9';
	private char maxADigit = 0;
	
	public RadixDecimalConv(int base) {
		this.base = base;
		
		if (base <= 10)
			maxDigit = Character.toChars((base -1)+ '0')[0];
		else if (base < 36)
			maxADigit = Character.toChars((base - (10+1))+'a')[0];
	}
	
	
	public int convertRadixNum (String radNum) {
		int decNum = 0;
		
		String lcRadNum = radNum.toLowerCase();
		
		for(int i = 0; i < lcRadNum.length(); i++) {
			decNum *= base;
//			int baseValue = getDecValue(lcRadNum.charAt(i));
			
//			decNum += baseValue;
		}
		
		return decNum;
	}
	
//	private int getDecValue(char digit)
//	{
//		if(digit >= '0' && digit <= maxDigit)
//			return digit - '0';
//		else if (digit)
//	}
}
