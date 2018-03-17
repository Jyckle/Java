
public class TimeofDayTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimeOfDay morning = new TimeOfDay(8,20,34);
		TimeOfDay night = morning.addSeconds(3600*10);
		System.out.println("morning: " + morning.getTime());
		System.out.println("night: " + night.getTime());
		System.out.println("Seconds from morning to night: " + night.secondsFrom(morning));
	}

}
