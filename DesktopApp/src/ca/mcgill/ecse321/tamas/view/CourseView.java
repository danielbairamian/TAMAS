package ca.mcgill.ecse321.tamas.view;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;

public class CourseView extends JFrame {
	private JLabel courseLabel;
	//private JLabel course;
	private JLabel instructorLabel;
	//private JLabel instructor;
	private JLabel budgetLabel;
	//private JLabel start;
	private JLabel labLabel;
	//private JLabel end;
	private JLabel tutorialLabel;
	//private JLabel deadline;
//	private JScrollPane scroll;
//	private JTextArea description;
	
	private Course theCourse;
	private Department department;
	private DepartmentController cont;
	
	public CourseView(Course course){
		
		theCourse = course;
		cont = new DepartmentController(department);
		initComponents();
	}
	
	public void initComponents(){
		courseLabel = new JLabel("Course: " + theCourse.getInstructor().getCourse().getId().trim());
		//course = new JLabel(jobPosting.getInstructor().getCourse().getId().trim());
		
		instructorLabel = new JLabel("Instructor: " + theCourse.getInstructor().getName());
		//instructor = new JLabel("");
		
		budgetLabel = new JLabel("Given Budget: " + theCourse.getBudgetGiven());
		//start = new JLabel("");
		
		labLabel = new JLabel("Number of Labs: " + theCourse.getNumLabs());
		//end = new JLabel("");
		
		tutorialLabel = new JLabel("Application Deadline: " + theCourse.getNumTutorials());
		//deadline = new JLabel("");
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(courseLabel)
						.addComponent(instructorLabel)
						.addComponent(budgetLabel)
						.addComponent(labLabel)
						.addComponent(tutorialLabel)
						)
				);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(courseLabel)
						//.addComponent(course)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(instructorLabel)
						//.addComponent(instructor)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(budgetLabel)
						//.addComponent(start)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(labLabel)
						//.addComponent(end)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(tutorialLabel)
						//.addComponent(deadline)
						)
				);
		
		pack();
		
	}
}
