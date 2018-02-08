package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.controller.StudentDepartmentController;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.Registration;
import ca.mcgill.ecse321.tamas.model.Student;


public class ViewJobOffer extends JFrame{


	//UI Elements
	private JLabel errorMessage;

	//students elements
	private JLabel studentLabel;
	private JComboBox<String> studentComboBox;


	//job offers
	private JLabel jobOffersLabel;
	private JComboBox<String> jobOffersComboBox;
	private JButton acceptButton;
	private JButton declineButton;


	//data elements
	private DepartmentController departmentController;
	private Department department;
	private String error = null;
	private int selectedStudentIndex = -1;
	private Student selectedStudent;
	private Job selectedJob;
	private int selectedJobIndex = -1;


	public ViewJobOffer(Department department){
		this.department = department;
		this.departmentController = new DepartmentController(department);

		initComponents();
		refreshData();
	}


	public void initComponents(){
		//elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.red);

		//students elements
		studentLabel = new JLabel();
		studentComboBox = new JComboBox<String>();

		//job offers
		jobOffersLabel = new JLabel();
		jobOffersComboBox = new JComboBox<String>();
		acceptButton = new JButton();
		declineButton = new JButton();

		studentLabel.setText("Students:");
		jobOffersLabel.setText("Job Offers:");
		acceptButton.setText("Accept");
		declineButton.setText("Decline");

		studentComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedStudentIndex = cb.getSelectedIndex();
				if(selectedStudentIndex >= 0){
					populateJobSpinner();
				}
			}
		});


		jobOffersComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
					selectedJobIndex = cb.getSelectedIndex();
			}
		});

		acceptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				acceptJob();
			}
		});

		declineButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				declineJob();
			}
		});

		populateStudentSpinner();
		
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
										
										.addComponent(studentLabel)
										.addComponent(jobOffersLabel)
										.addComponent(acceptButton)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(studentComboBox)
										.addComponent(jobOffersComboBox)
										.addComponent(declineButton)
										)
								)
						)
				
				);

		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {acceptButton, declineButton, studentLabel,jobOffersLabel});
		layout.linkSize(SwingConstants.VERTICAL, new Component[] {studentLabel, studentComboBox, jobOffersComboBox});
		layout.setVerticalGroup(layout.createSequentialGroup()
				
						.addComponent(errorMessage)
						
				.addGroup(layout.createParallelGroup()
						.addComponent(studentLabel)
						.addComponent(studentComboBox)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(jobOffersLabel)
						.addComponent(jobOffersComboBox)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(acceptButton)
						.addComponent(declineButton)
						)

				);
		
		pack();
	}

	private void populateStudentSpinner(){
		studentComboBox.removeAllItems();
		List<Student> students = departmentController.getAllStudents();
		for(Student student : students){
			studentComboBox.addItem(student.getName());
		}		
	}

	private void populateJobSpinner(){
		jobOffersComboBox.removeAllItems();
		if(selectedStudentIndex >= 0){
			List<Registration> registrations =  new ArrayList<Registration>();
			selectedStudent = departmentController.getStudentAt(selectedStudentIndex);
			if(selectedStudent != null){
				//TODO
				department.setCurrentStudent(selectedStudent);
				registrations = selectedStudent.getRegistrations();
				for(Registration registration : registrations){
					if(registration.getJob().getJobState().equals(Job.JobState.DepartmentApproved)){
						String jobOffer="";
						jobOffer += registration.getJob().getJobPosting().getInstructor().getCourse().getId();
						jobOffer += " ";
						jobOffer += registration.getJob().getJobTypeFullName();
						jobOffersComboBox.addItem(jobOffer);
					}
				}
			}else{
				error = "The selected student does not exist.";
			}
		}
		
	}

	public void refreshData(){
		if(error != null){
			errorMessage.setText(error);
			error=null;
		}else{
			errorMessage.setText("");
		}
		studentComboBox.removeAllItems();
		jobOffersComboBox.removeAllItems();
		populateStudentSpinner();
	}

	private void acceptJob(){
		if(selectedStudentIndex >= 0 && selectedJobIndex >= 0){
			selectedStudent = departmentController.getStudentAt(selectedStudentIndex);
			if(selectedStudent.hasRegistrations()){
				selectedJob = selectedStudent.getRegistration(selectedJobIndex).getJob();
			}else{
				error = "This student doesn't have any job offer";
			}

		}else{
			error = "Please select a student and a job offer.";
		}
		if(error == null){
			StudentDepartmentController sdc = new StudentDepartmentController(department);
			try {
				sdc.acceptJobOffer(selectedJob);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}

		refreshData();

	}

	private void declineJob(){
		if(selectedStudentIndex >= 0 && selectedJobIndex >= 0){
			selectedStudent = departmentController.getStudentAt(selectedStudentIndex);
			if(selectedStudent.hasRegistrations()){
				selectedJob = selectedStudent.getRegistration(selectedJobIndex).getJob();
			}else{
				error = "This student doesn't have any job offer";
			}

		}else{
			error = "Please select a student and a job offer.";
		}

		if(error == null){
			StudentDepartmentController sdc = new StudentDepartmentController(department);
			try {
				sdc.declineJobOffer(selectedJob);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}

		refreshData();

	}

}
