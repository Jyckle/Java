

public class TimeOfDay {
	private final int hours;
	private final int minutes;
	private final int seconds;
	
	
	public TimeOfDay(int hours, int minutes, int seconds) {
		
		//Calculate seconds
		int change = checkBounds(seconds,0,60);
		if (change != 0) {
			this.seconds = calcRem(seconds,0,60);
			minutes = minutes + change;
		}
		else this.seconds = seconds;
		
		change = checkBounds(minutes,0,60);
		if (change != 0) {
			//System.out.println("Please use a number between 0 and 59 in the future");
			this.minutes = calcRem(minutes,0,60);
			hours = hours + change;
		}
		else this.minutes = minutes;
		
		change = checkBounds(hours,0,24);
		if (change != 0) {
			//System.out.println("Please use a number between 0 and 24 in the future");
			this.hours = calcRem(hours,0,24);
		}
		else this.hours = hours;
	
	}
	
	public int getHours() {
		return hours;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public String getTime() {
		return hours + ":" + minutes + ":" + seconds;
	}
	
	public TimeOfDay addSeconds(int seconds) {
		TimeOfDay newTime = new TimeOfDay(hours, minutes, this.seconds+seconds);
		return newTime;
	}
	
	public int secondsFrom(TimeOfDay other) {
		int h = this.getHours() - other.getHours();
		int m = this.getMinutes() - other.getMinutes();
		int s = this.getSeconds() - other.getSeconds();
		s = s + 60*m + h*3600;
		return s;
	}
	
	private int calcRem(int value, int low, int high) {
		if (value >= low && value < high) {
			return value;
		}
		else if (value < low) {
			return -value%(high-low);
		}
		else if (value >=high) {
			return value%(high-low);
		}
		else return 0;
	}
	
	private int checkBounds(int value, int low, int high) {
		if (value >= low && value < high) {
			return 0;
		}
		else if (value < low) {
			return (value)/(high-low);
		}
		else if (value >=high) {
			return value/(high-low);
		}
		else return 0;
	}
}
