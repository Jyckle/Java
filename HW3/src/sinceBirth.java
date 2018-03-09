import java.util.GregorianCalendar;

public class sinceBirth {
	
	public static void main(String[] args) {
		sinceBirth a = new sinceBirth();
		System.out.println(a.fromBirth(1997,7,10));
		
		
	}
	
	public int fromBirth(int year, int month, int dayOfMonth) {
		GregorianCalendar current = new GregorianCalendar();
		GregorianCalendar birth = new GregorianCalendar(year,month-1,dayOfMonth);
		
		
		long dif = current.getTimeInMillis() - birth.getTimeInMillis();
		
		
		dif = dif/(86400000);
		
		int days = (int) dif;
		
		return days;
		
		
	}
}
