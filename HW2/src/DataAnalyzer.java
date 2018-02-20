import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class DataAnalyzer {
	
	private double min, max, mean;
	private ArrayList<Double> values = new ArrayList<Double>();
	
	public DataAnalyzer(String filename) {
		try {
			Scanner input = new Scanner(new FileReader(filename));
			while(input.hasNextDouble())
			{
				values.add(input.nextDouble());
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public double getMin() {
		min = values.get(0);
		for(double a: values) {
			if(a < min) {
				min = a;
			}
		}
		return min;
	}
	
	public double getMax() {
		max = values.get(0);
		for(double a: values) {
			if(a > max) {
				max = a;
			}
		}
		return max;
	}
	
	public double getMean() {
		mean = 0;
		for(double a: values) {
			mean += a;
		}
		mean = mean/values.size();
		return mean;
	}
}
