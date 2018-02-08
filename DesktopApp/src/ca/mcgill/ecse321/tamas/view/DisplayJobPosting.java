package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.JobPosting;

public class DisplayJobPosting extends JFrame {

//	private static final long serialVersionUID = -4426310869335015542L;

	//UI elements
	private JLabel errorMessage;

	//course details
	private JLabel courseNameLabel;
	private JComboBox<String> coursesComboBox;
	
	//job type
	private JLabel jobOfferingsLabel;
	private JComboBox<Integer> jobOfferingsComboBox;

	//instructor
	private JLabel instructorNameLabel;
	private JTextField instructorTextField;

	//job posting
	private JLabel jobDescriptionLabel;
	private JLabel jobDatesLabel;
	private JTextArea jobDescriptionTextArea;
	private JTextArea jobDatesTextArea;

	private JButton openApplicationButton;
	private JButton seeJobPostingButton;

	// data elements
	private String error = null;

	// course
	//private String selectedCourse;
	private Integer selectedCourseIndex;

	//job
	//private String selectedJob;
	private Integer selectedJobIndex;

	private Department department;
	
	private JobPosting job;

	public DisplayJobPosting(Department department){
		this.department = department;
		initComponents();
		refreshData();
	}

	public void initComponents(){
		this.setPreferredSize(new Dimension(1000, 600));

		//elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		//course details
		courseNameLabel = new JLabel();
		coursesComboBox = new JComboBox<String>(new String[0]);

		//job type
		jobOfferingsLabel = new JLabel();
		jobOfferingsComboBox = new JComboBox<Integer>();

		//instructor
		instructorNameLabel = new JLabel();
		instructorTextField = new JTextField();
		instructorTextField.setEditable(false);

		//job posting
		jobDescriptionLabel = new JLabel();
		jobDatesLabel = new JLabel();
		jobDescriptionTextArea = new JTextArea();
		jobDatesTextArea = new JTextArea();
		jobDescriptionTextArea.setEditable(false);
		jobDatesTextArea.setEditable(false);

		jobDescriptionTextArea.setWrapStyleWord(true);
		jobDescriptionTextArea.setLineWrap(true);

		jobDatesTextArea.setWrapStyleWord(true);
		jobDatesTextArea.setLineWrap(true);

		openApplicationButton = new JButton();
		openApplicationButton.setText("Open Application");
		seeJobPostingButton = new JButton();
		seeJobPostingButton.setText("See Posting");
		
	

		openApplicationButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new ApplyForAJobView(department, selectedCourseIndex, job).setVisible(true);;

			}
		});

		seeJobPostingButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				getJobPostingDetails();
			}
		});

		//course global settings
		courseNameLabel.setText("Course:");
		coursesComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				selectedCourseIndex = cb.getSelectedIndex();
				selectedCourseActionPerformed();
			}
		});
		coursesComboBox.setPreferredSize(new Dimension(200, 30));

		//job type global settings
		jobOfferingsLabel.setText("Jobs:");
		jobOfferingsComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				selectedJobIndex = cb.getSelectedIndex();
			}
	});

		//instructor
		instructorNameLabel.setText("Instructor: ");

		//job posting
		jobDescriptionLabel.setText("Description");
		jobDatesLabel.setText("Dates:");

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(

				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(courseNameLabel)
								.addComponent(instructorNameLabel)
								.addComponent(jobDescriptionLabel)
								.addComponent(jobDatesLabel)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(coursesComboBox)
								.addComponent(instructorTextField)
								.addComponent(jobDescriptionTextArea)
								.addComponent(jobDatesTextArea)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(jobOfferingsLabel)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(jobOfferingsComboBox)
								.addComponent(seeJobPostingButton)
								.addComponent(openApplicationButton)
								)
						)
				);


		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]{openApplicationButton, instructorTextField, coursesComboBox, jobOfferingsComboBox, seeJobPostingButton});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[]{courseNameLabel, jobDatesTextArea, seeJobPostingButton, coursesComboBox, jobOfferingsComboBox, openApplicationButton});
		//layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]{jobDescriptionTextArea, jobDatesTextArea});

		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[]{instructorTextField, coursesComboBox});


		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addComponent(courseNameLabel)
						.addComponent(coursesComboBox)
						.addComponent(jobOfferingsLabel)
						.addComponent(jobOfferingsComboBox)

						)
				.addGroup(layout.createParallelGroup()
						.addComponent(instructorNameLabel)
						.addComponent(instructorTextField)
						.addComponent(seeJobPostingButton)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(jobDescriptionLabel)
						.addComponent(jobDescriptionTextArea)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(jobDatesLabel)
						.addComponent(jobDatesTextArea)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(openApplicationButton)
						)
				);

		pack();

	}

	public void refreshData(){
		// error
		errorMessage.setText(error);
		if(error == null || error.length() == 0){
			jobDescriptionTextArea.setText("");
			jobDatesTextArea.setText("");
			instructorTextField.setText("");

			coursesComboBox.removeAllItems();
			jobOfferingsComboBox.removeAllItems();

			for(Course course : department.getCourses()){
				coursesComboBox.addItem(course.getId());
			}
			//selectedCourse = "";
			selectedCourseIndex = 0;
			//selectedJob = "";
			selectedJobIndex = 0;

		}
	}

	private void selectedCourseActionPerformed(){

		jobOfferingsComboBox.removeAllItems();
		if(selectedCourseIndex >= 0){
			Course selectedCourse = department.getCourse(selectedCourseIndex);
	
			if(selectedCourse != null){
				int jobOfferingIndex = 1;
				for(JobPosting job : selectedCourse.getInstructor().getJobOfferings()){
					if(job.getJobPostingState() == JobPosting.JobPostingState.Active){
						jobOfferingsComboBox.addItem(jobOfferingIndex++);
					}
				}
			}else{
				// TODO
				errorMessage.setText("Course is null");
			}
		}

	}

	private void getJobPostingDetails(){
		instructorTextField.setText("");
		jobDescriptionTextArea.setText("");
		jobDatesTextArea.setText("");
		
		if(selectedCourseIndex >= 0){
			Course selectedCourse = department.getCourse(selectedCourseIndex);
	
			if(selectedCourse != null){
				int jobOfferingIndex = 1;
				for(int i = 0; i < selectedCourse.getInstructor().getJobOfferings().size(); i++){
					if(selectedCourse.getInstructor().getJobOffering(i).getJobPostingState() == JobPosting.JobPostingState.Active && i == selectedJobIndex){
						this.job = selectedCourse.getInstructor().getJobOffering(i);
					}
				}
			}
		}


		Course selectedCourse = department.getCourse(selectedCourseIndex);
		instructorTextField.setText(job.getInstructor().getName());
		jobDescriptionTextArea.setText(job.getDescription());
		
		String importantDates = "Start date: " + job.getStartDate().toString() + "   End date: " + job.getEndDate().toString() + "   DeadLine: " + job.getDeadline().toString();
		jobDatesTextArea.setText(importantDates);

	}

}
