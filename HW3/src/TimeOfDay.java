

public class TimeOfDay {
	private final int hours;
	private final int minutes;
	private final int seconds;
	
	
	public TimeOfDay(int hours, int minutes, int seconds) {
		if (hours >=0 && hours < 24) {
			this.hours = hours;
		}
		else {
			this.hours = 0;
			System.out.println("Hours should be between 0 and 24");
		}
		
		
		if (minutes >=0 && minutes < 60) {
			this.minutes = minutes;
		}
		else {
			this.minutes = 0;
			System.out.println("Minutes should be between 0 and 59");
		}
		
		
		if (minutes >=0 && minutes < 60) {
			this.seconds = seconds;
		}
		else {
			this.seconds = 0;
			System.out.println("Seconds should be between 0 and 59");
		}
		
	}
	
	public TimeOfDay addSeconds(int seconds) {
		//need to add something to handle going into new day
		TimeOfDay newTime = new TimeOfDay(hours, minutes, this.seconds+seconds);
		return newTime;
	}
	
	public int secondsFrom(TimeOfDay other) {
		return 0;
	}
	
	
}
