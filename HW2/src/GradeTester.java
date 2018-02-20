import java.util.*;
import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeTester {

	public static void main(String[] args) {
		
		StudentGrades a = new StudentGrades("hw02_prob2_input.txt");
		ArrayList<Student> students = a.getStudents();
		
//		Comparator<Student> names = new StudentComparatorNames(true);
//		Collections.sort(students,names);
//		
//		Comparator<Student> means = new StudentComparatorMeans(false);
//		Collections.sort(students,means);
//		
//		for (Student s: students) {
//			System.out.println(s.getName() + "\nMin: " + s.getMin() + " Max: "
//					+ s.getMax() + " Mean: " + s.getMean());
//		}
//		
		
		JFrame frame = new JFrame();
		JButton gradeButton = new JButton("Sort by Grades");
		JButton nameButton = new JButton("Sort by Names");
		
		gradeButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
					Comparator<Student> means = new StudentComparatorMeans(false);
					Collections.sort(students,means);
					String gradesString = "";
					
					for (Student s: students) {
						gradesString += "\n" + s.getName() 
						+ ": " + String.format("%.2f", s.getMean());
					}
					
					JOptionPane.showMessageDialog(
							null,
							gradesString,
							"Student Grades",
							JOptionPane.INFORMATION_MESSAGE
							);
				}
		});
		
		nameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				Comparator<Student> names = new StudentComparatorNames(true);
				Collections.sort(students,names);
				String gradesString = "";
				
				for (Student s: students) {
					gradesString += "\n" + s.getName() + ": "
					+ String.format("%.2f", s.getMean());
				}
				
				JOptionPane.showMessageDialog(
						null,
						gradesString,
						"Student Names",
						JOptionPane.INFORMATION_MESSAGE
						);
			}
	});
		
		
		frame.setLayout(new FlowLayout());

		frame.add(gradeButton);
		frame.add(nameButton);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
//	public class ButtonListener implements ActionListener
//	{
//		
//	}
}
