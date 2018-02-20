import java.util.Comparator;

public class StudentComparatorNames implements Comparator<Student> {
	
	public StudentComparatorNames(boolean ascending) {
		if (ascending) sort = 1;
		else sort = -1;
	}
	
	public int compare(Student student1, Student student2) {
		return sort * student1.getName().compareTo(student2.getName());
	}
	
	private int sort;
}
