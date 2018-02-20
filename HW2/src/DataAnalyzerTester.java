
public class DataAnalyzerTester {

	public static void main(String[] args) {
		
		DataAnalyzer d = new DataAnalyzer("hw02_prob1_input.txt");
		System.out.println("Min: " + d.getMin());
		System.out.println("Max: " + d.getMax());
		System.out.println("Mean: " + d.getMean());
		
	}

}
