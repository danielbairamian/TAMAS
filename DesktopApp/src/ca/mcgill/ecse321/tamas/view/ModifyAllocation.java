package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
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

public class ModifyAllocation extends JFrame{
	private JLabel errorMessage;
	private JLabel courseLabel;
	private JComboBox<String> courses;
	private JLabel givenBudgetLabel;
	private JLabel remainingBudgetLabel;
	//private JComboBox<String> students;
	//private JComboBox<String> evals;
//	private JTextArea desc;
//	private JScrollPane scroll;
	//private List<Registration> regList;
	private List<Registration>students;
	private JButton viewDetails;
	private JButton approveButton;
	private  int courseSelected;
	private JList studentList;
	private DefaultListModel studentModel;
	private JScrollPane scroll;
	
	
	private Student student;
	private String error = "";
	private Department department;
	private InstructorDepartmentController cont;
	private DepartmentController contDep;
	
	public ModifyAllocation(Department dep){
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
		approveButton = new JButton("Approve");
		students = new ArrayList<>();
		studentModel = new DefaultListModel<>();
		studentList = new JList<>(studentModel);
		scroll = new JScrollPane(studentList);
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
//		for(Course c : department.getCourses()){
//			courses.addItem(c.getId());
		
//		}
				
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				
				.addGroup(layout.createParallelGroup()
						.addComponent(errorMessage)
						.addComponent(courseLabel)
						.addComponent(givenBudgetLabel)
						.addComponent(remainingBudgetLabel)
						.addGroup(layout.createSequentialGroup()
								.addComponent(approveButton)
								.addComponent(viewDetails)
								)
						
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(courses)
						.addComponent(scroll)
						
						)
				
				);
		layout.linkSize(SwingConstants.VERTICAL, new Component[] {courses});
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addComponent(courseLabel)
						.addComponent(courses)
						)
				.addComponent(givenBudgetLabel)
				.addComponent(remainingBudgetLabel)
				.addComponent(scroll)
				.addGroup(layout.createParallelGroup()
						.addComponent(approveButton)
						.addComponent(viewDetails)
						)
				
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
					student = students.get(studentList.getSelectedIndex()).getStudent();
					new DetailsPage(department, contDep.getCourseAt(courses.getSelectedIndex()),students.get(studentList.getSelectedIndex()).getJob() , false).setVisible(true);
				}
			}
		});
		
		approveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(studentList.getSelectedIndex() != -1){
					student = students.get(studentList.getSelectedIndex()).getStudent();
					approvaAllocation();
				}
				refreshStudents();
			}
		});
		
		
	}
	
	public void refreshBudget(){
		if(courses.getSelectedIndex() != -1){
			givenBudgetLabel.setText("Given Budget: " + contDep.getCourseAt(courses.getSelectedIndex()).getBudgetGiven());
			remainingBudgetLabel.setText("Remaining Budget: " +  contDep.getCourseAt(courses.getSelectedIndex()).getBudgetActual());
		}
		
	}
	
	
	public void approvaAllocation(){
		/*
		 * student = students.get(studentList.getSelectedIndex());
			new DetailsPage(department, contDep.getCourseAt(courses.getSelectedIndex()), student.getRegistration(0).getJob(), false).setVisible(true);
		 *
		 *
		 *
		 */
		InstructorDepartmentController instDep = new InstructorDepartmentController(department);
		student = students.get(studentList.getSelectedIndex()).getStudent();
		Course selectedCouse = contDep.getCourseAt(courses.getSelectedIndex());
		Job selectedJob = students.get(studentList.getSelectedIndex()).getJob();
		
		try {
			instDep.modifyAllocation(selectedCouse, selectedJob, selectedJob.getWorkHours(), selectedJob.getHourlyRate());
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
	//	refreshData();
	}
	
	public void refreshStudents(){
		studentModel.removeAllElements();
		studentList.removeAll();
		students.clear();
		if(courses.getSelectedIndex() != -1){
			students = cont.getInitialApprovedStudents((department.getCourse(courses.getSelectedIndex())));
				for(Registration r :students){
				}
		}
	
		if(courses.getSelectedIndex() >= 0){
				for(Registration r : students){
					if(r.getJob().getJobPosting().getInstructor().getCourse() == department.getCourse(courses.getSelectedIndex())){
						if(r.getJob().getJobState() == Job.JobState.Offered){
							studentModel.addElement(r.getStudent().getName());
							
						}
					}
				}
			
		}
		
		studentList.setModel(studentModel);
		refreshBudget();
	}
	
	public void refreshData(){
		if(error.trim().equals("") ){
			courses.removeAllItems();
			if(department.getCourses().size() > 0){
				
				for(Course c : department.getCourses()){
					courses.addItem(c.getId());
				}
			}
		}
		errorMessage.setText(error);
		error = "";
	
	}
	
}
