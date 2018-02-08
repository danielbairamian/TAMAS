package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DepartmentView extends JFrame {
	
	private static final long serialVersionUID = -4426310869335015542L;
	
	
	// UI elements
	private JLabel errorMessage;
	
	// courses
	private JTextField courseNumber;
	private JLabel courseNumberLabel;
	private JComboBox<String> courseToggleList;
	private JLabel courseToggleLabel;
	private JButton addCourseButton;
	
	//course budget
	private JTextField courseBudget;
	private JLabel courseBudgetLabel;
	
	//course labs
	private JTextField courseNbOfLabs;
	private JLabel courseNbOfLabsLabel;
	
	//course tutorial
	private JTextField courseNbOfTutorials;
	private JLabel courseNbOfTutorialsLabel;
	
	
	public DepartmentView() {
		initComponents();
		refreshData();
	}
	
	private void initComponents(){
		//elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		//elements for course
		courseNumber = new JTextField();
		courseNumberLabel = new JLabel();
		courseToggleList = new JComboBox<String>();
		courseToggleLabel = new JLabel();
		addCourseButton = new JButton();
		
		//course budget
		courseBudget = new JTextField();
		courseBudgetLabel = new JLabel();
		
		//course tutorial
		courseNbOfTutorials = new JTextField();
		courseNbOfTutorialsLabel = new JLabel();
		
		
		//global settings and listeners
		
		courseNumberLabel.setText("Course Number:");
		courseToggleLabel.setText("Select Course:");
		addCourseButton.setText("Add course");
		
		courseBudgetLabel.setText("Course Budget:");
		courseNbOfTutorialsLabel.setText("Course Tutorials:");
		
		//horizontal line elements
		JSeparator horizontalLine = new JSeparator();
		
		//layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addComponent(errorMessage)
					.addGroup(layout.createParallelGroup()
							.addComponent(courseNumberLabel)
							.addComponent(courseToggleLabel)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(courseNumber)
							.addComponent(courseToggleList)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(courseBudgetLabel)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(courseBudget)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(courseNbOfTutorialsLabel)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(courseNbOfTutorials)
							.addComponent(addCourseButton)
							)	
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {courseNumber, courseToggleList,
				courseBudget, courseNbOfTutorials, addCourseButton});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {courseNumberLabel, courseNumber, courseBudget, courseNbOfTutorials, courseToggleList});
		
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(errorMessage)
						.addGroup(layout.createParallelGroup()
								.addComponent(courseNumberLabel)
								.addComponent(courseNumber)
								.addComponent(courseBudgetLabel)
								.addComponent(courseBudget)
								.addComponent(courseNbOfTutorialsLabel)
								.addComponent(courseNbOfTutorials)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(courseToggleLabel)
								.addComponent(courseToggleList)
								.addComponent(addCourseButton)
								)
						
						)
		);
	}
	
	private void refreshData(){
		
	}
	
}
