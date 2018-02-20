
public class Student {
	private String name;
	private double min, max, mean;
	
	public Student(String name, double min, double max, double mean) {
		this.name = name;
		this.min = min;
		this.max = max;
		this.mean = mean;
	}
	
	//begin name section
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	//begin min section
	public double getMin() {
		return min;
	}
	
	public void setMin(Double min) {
		this.min = min;
	}
	
	
	//begin max section
	public double getMax() {
		return max;
	}
	
	public void setMax(Double max) {
		this.max = max;
	}
	
	
	//begin mean section
	public double getMean() {
		return mean;
	}
	
	public void setMean(Double mean) {
		this.mean = mean;
	}
	
}
