package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.controller.StudentDepartmentController;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.JobPosting;
import ca.mcgill.ecse321.tamas.model.Student;

public class ApplyForAJobView extends JFrame{
	
	// UI elements
	private JLabel errorMessage;

	//course details
	private JLabel courseNameLabel;
	
	//job type
	private JLabel jobOfferingsLabel;
	
	//application elements
	private JTextField studentEmailTextField;
	private JLabel studentEmailLabel;

	private JTextArea studentExperienceTextArea;
	private JLabel studentExperienceLabel;

	private JComboBox<Integer> preferenceComboBox;
	private JLabel preferenceLabel;

	private JButton sendApplicationButton;

	// data elements
	private String error = null;
	private Integer selectedCourseIndex;
	private JobPosting selectedJobIndex;
	
	
	//TODO send this to the controller
	private Integer selectedPreference;
	private String enteredEmail;
	private String enteredExperience;
	
	private JLabel studentsLabel;
	private JComboBox<String> students;

	private Course selectedCourse ;
	private Department department;

	public ApplyForAJobView(Department department, Integer selectedCourseIndex, JobPosting selectedJobIndex) {
		this.department = department;
		this.selectedJobIndex = selectedJobIndex;
		this.selectedCourseIndex = selectedCourseIndex;
		initComponents();
		refreshData();
	}

	private void initComponents(){
		//set the size of the view
		this.setSize(1000, 600);

		//elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		//application form
		studentEmailTextField = new JTextField();
		studentEmailLabel = new JLabel();

		studentExperienceTextArea = new JTextArea();
		studentExperienceLabel = new JLabel();

		preferenceComboBox = new JComboBox<Integer>();
		preferenceComboBox.addItem(1);
		preferenceComboBox.addItem(2);
		preferenceComboBox.addItem(3);
		preferenceLabel = new JLabel();

		sendApplicationButton = new JButton();

		//course elements
		courseNameLabel = new JLabel();
		
		//job offering elements
		jobOfferingsLabel = new JLabel();
		
		students = new JComboBox<>();
		studentsLabel = new JLabel("Student:");
		
		//application for global settings and listeners
		studentEmailLabel.setText("Email:");
		studentExperienceLabel.setText("Experience:");
		preferenceLabel.setText("Preference:");
		sendApplicationButton.setText("Apply");
		
		selectedCourse = department.getCourse(selectedCourseIndex);
		
		populateStudentDropDown();
		if(students.getSelectedIndex() >= 0){
			department.setCurrentStudent(department.getStudent(students.getSelectedIndex()));
		}
		
		sendApplicationButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					sendApplicationActionPerformed(evt);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		studentExperienceTextArea.setWrapStyleWord(true);
		studentExperienceTextArea.setLineWrap(true);

		preferenceComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				selectedPreference = cb.getSelectedIndex() + 1;
			}
		});
		
		students.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(students.getSelectedIndex() >= 0){
					department.setCurrentStudent(department.getStudent(students.getSelectedIndex()));
				}
			}
		});


		//course global settings TODO get the course name
		courseNameLabel.setText(department.getCourse(selectedCourseIndex).getId());
		
		//job offering global settings
		jobOfferingsLabel.setText("Jobs:");

		//horizontal line elements
		JSeparator horizontalLine = new JSeparator();

		//layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(errorMessage)

						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(studentsLabel)
										.addComponent(courseNameLabel)
										.addComponent(studentEmailLabel)
										.addComponent(studentExperienceLabel)
										.addComponent(preferenceLabel)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(students)
										.addComponent(studentEmailTextField)
										.addComponent(studentExperienceTextArea)
										.addComponent(preferenceComboBox)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(sendApplicationButton)
										)
								)
						)
				);

		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[]{studentEmailTextField, sendApplicationButton, preferenceComboBox,students});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]{sendApplicationButton, preferenceComboBox,students});

		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(errorMessage)
						.addGroup(layout.createParallelGroup()
								.addComponent(studentsLabel)
								.addComponent(students)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(courseNameLabel)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(studentEmailLabel)
								.addComponent(studentEmailTextField)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(studentExperienceLabel)
								.addComponent(studentExperienceTextArea)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(preferenceLabel)
								.addComponent(preferenceComboBox)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(sendApplicationButton)
								)
						)


				);

	}

	private void refreshData(){
		//error
		errorMessage.setText(error);
		students.removeAllItems();
		populateStudentDropDown();
		if(error == null || error.length() == 0){
			//application
			studentEmailTextField.setText("");
			studentExperienceTextArea.setText("");
			preferenceComboBox.removeAllItems();
			preferenceComboBox.addItem(1);
			preferenceComboBox.addItem(2);
			preferenceComboBox.addItem(3);	
			
		}
	}
	
	public void populateStudentDropDown(){
		for(Student s : department.getStudents()){
			students.addItem(s.getName());
		}
	}

	private void sendApplicationActionPerformed(java.awt.event.ActionEvent evt) throws ParseException {
		// clear error message
		error = "";

		enteredEmail = studentEmailTextField.getText();
		enteredExperience = studentExperienceTextArea.getText();

		//checking for basic input errors
		if(enteredEmail == null || enteredEmail.trim().equals("")){
			error += "Please provide your email. ";
		}
		if(enteredExperience == null || enteredExperience.trim().equals("")){
			error += "Please provide your experience. ";
		}	
		
		//call the controller
		StudentDepartmentController sdc = new StudentDepartmentController(department);
		if(error.trim().length() == 0){
			try{
				sdc.applyForJob(selectedJobIndex, enteredEmail, enteredExperience, selectedPreference);
			} catch(InvalidInputException e){
				error = e.getMessage();
			}
		}	
		
		//update visuals
		refreshData();

	}


}
