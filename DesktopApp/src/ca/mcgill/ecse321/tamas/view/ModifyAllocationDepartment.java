package ca.mcgill.ecse321.tamas.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.controller.InstructorDepartmentController;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.Registration;
import ca.mcgill.ecse321.tamas.model.Student;

public class ModifyAllocationDepartment extends JFrame{
	private JLabel courseLabel;
	private JComboBox<String> courses;
	private JLabel givenBudgetLabel;
	private JLabel remainingBudgetLabel;
	//private JComboBox<String> students;
	//private JComboBox<String> evals;
//	private JTextArea desc;
//	private JScrollPane scroll;
	private List<Registration> regList;
	private List<Student>students;
	private JButton viewDetails;
	private  int courseSelected;
	private JList studentList;
	private DefaultListModel studentModel;
	private JScrollPane scroll;
	
	
	private Student student;
	
	private Department department;
	private InstructorDepartmentController cont;
	private DepartmentController contDep;
	
	public ModifyAllocationDepartment(Department dep){
		this.department = dep;
		cont = new InstructorDepartmentController(department);
		contDep = new DepartmentController(department);
		initComponents();
	}
	
	public void initComponents(){
		courseLabel = new JLabel("Course: ");
		courses = new JComboBox<>();
		givenBudgetLabel = new JLabel("Given Budget: ");
		remainingBudgetLabel = new JLabel("Remaining Budget: ");
		viewDetails = new JButton("View Details");
		students = new ArrayList<>();
		studentModel = new DefaultListModel<>();
		studentList = new JList<>(studentModel);
		scroll = new JScrollPane(studentList);
		regList = new ArrayList<>();
		
//		for(Course c : department.getCourses()){
//			courses.addItem(c.getId());
//		}
				
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				
				.addGroup(layout.createParallelGroup()
						.addComponent(courseLabel)
						.addComponent(givenBudgetLabel)
						.addComponent(remainingBudgetLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(courses)
						.addComponent(scroll)
						.addComponent(viewDetails)
						)
				);
		layout.linkSize(SwingConstants.VERTICAL, new Component[] {courses});
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(courseLabel)
						.addComponent(courses)
						)
				.addComponent(givenBudgetLabel)
				.addComponent(remainingBudgetLabel)
				.addComponent(scroll)
				.addComponent(viewDetails)
				);
		
		pack();
		
		courses.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				refreshStudents();
			}
		});
		
		viewDetails.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(studentList.getSelectedIndex() != -1){
					student = regList.get(studentList.getSelectedIndex()).getStudent();
					new DetailsPage(department, contDep.getCourseAt(courses.getSelectedIndex()), regList.get(studentList.getSelectedIndex()).getJob(),true).setVisible(true);
				}
			}
		});
		
		
	}
	
	public void refreshBudget(){
		if(contDep.getAllCourses().size() > 0 && courses.getSelectedIndex() >= 0){
			float budget = contDep.getCourseAt(courses.getSelectedIndex()).getBudgetActual();
			for(Student s : students){
				for(Registration r : s.getRegistrations()){
					if(r.getJob().getJobPosting().getInstructor().getCourse().getId() == department.getCourse(courses.getSelectedIndex()).getId()){
						if(r.getJob().getJobState() == Job.JobState.InstructorApproved)
						budget = budget - (float)(r.getJob().getHourlyRate()*r.getJob().getWorkHours());
					}
				}
			}
			givenBudgetLabel.setText("Given Budget: " + contDep.getCourseAt(courses.getSelectedIndex()).getBudgetGiven());
			remainingBudgetLabel.setText("Remaining Budget: " + budget);
		}
	}
	
	public void refreshStudents(){
		studentModel.removeAllElements();
		studentList.removeAll();
		regList.clear();
		if(courses.getSelectedIndex() != -1){
			try {
				students = contDep.getApprovedStudents(department.getCourse(courses.getSelectedIndex()));
			} catch (InvalidInputException e1) {
				// TODO Auto-generated catch block
			}
		}
		if(courses.getSelectedIndex() >= 0){
			for(Student s : students){
				for(Registration r : s.getRegistrations()){
					if(r.getJob().getJobPosting().getInstructor().getCourse().getId() == department.getCourse(courses.getSelectedIndex()).getId()){
						if(r.getJob().getJobState() == Job.JobState.InstructorApproved){
							regList.add(r);
							studentModel.addElement(r.getStudent().getName());
						}
					}
				}
			}
			
	}
		studentList.setModel(studentModel);
		refreshBudget();
	}
	
	public void refreshData(){
		courses.removeAllItems();
		if(department.getCourses().size() > 0){
			
			for(Course c : department.getCourses()){
				courses.addItem(c.getId());
			}
		}
		
	}
}
