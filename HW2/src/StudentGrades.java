import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class StudentGrades {
	private double min, max, mean;
	private String name = "";
	private ArrayList<Double> values = new ArrayList<Double>();
	private ArrayList<Student> students = new ArrayList<Student>();
	
	public StudentGrades(String filename) {
		try {
			Scanner input = new Scanner(new FileReader(filename));
			while(input.hasNextLine())
			{
				String a = input.nextLine();
				if(isNumeric(a)) {
					values.add(Double.parseDouble(a));
				}
				else if(name=="") {
					name = a;
				}
				else{
					Student b= new Student(
							name, 
							this.calcMin(),
							this.calcMax(),
							this.calcMean()
							);
					students.add(b);
					values.clear();
					name = a;
				}
				
			}
			Student b= new Student(
					name, 
					this.calcMin(),
					this.calcMax(),
					this.calcMean()
					);
			students.add(b);
			input.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isNumeric (String s)
    {
        try {
    	    Double.parseDouble (s);
            // if this doesn't throw exception, s is numeric
            return true;
        }
        catch (NumberFormatException e) {
            // s is not numeric
            return false;
        }
    }
	
	private double calcMin() {
		min = values.get(0);
		for(double a: values) {
			if(a < min) {
				min = a;
			}
		}
		return min;
	}
	
	private double calcMax() {
		max = values.get(0);
		for(double a: values) {
			if(a > max) {
				max = a;
			}
		}
		return max;
	}
	
	private double calcMean() {
		mean = 0;
		for(double a: values) {
			mean += a;
		}
		mean = mean/values.size();
		return mean;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}
}

