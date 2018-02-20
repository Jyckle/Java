import java.util.Comparator;

public class StudentComparatorMeans implements Comparator<Student> {
	
	public StudentComparatorMeans(boolean ascending) {
		if (ascending) sort = 1;
		else sort = -1;
	}
	
	public int compare(Student student1, Student student2) {
		if(student1.getMean() < student2.getMean()) return sort * -1;
		if(student1.getMean() > student2.getMean()) return sort * 1;
		return 0;
	}
	
	private int sort;
}