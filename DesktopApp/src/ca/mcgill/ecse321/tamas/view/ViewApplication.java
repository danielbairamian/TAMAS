package ca.mcgill.ecse321.tamas.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.controller.InstructorDepartmentController;
import ca.mcgill.ecse321.tamas.model.Application;
import ca.mcgill.ecse321.tamas.model.Department;

public class ViewApplication extends JFrame {
	private JLabel name;
	//private JLabel course;
	private JLabel email;
	//private JLabel instructor;
	private JLabel preference;
	private JLabel studentLevel;
	//private JLabel start;
	//private JLabel deadline;
	private JButton hire;
	private JButton reject;
	private JScrollPane scroll;
	private JTextArea description;
	private JButton applications;
	
	
	
	private Application application;
	private Department department;
	private DepartmentController cont;
	private InstructorDepartmentController contInst;
	//private ViewApplicationList appListView;
	
	public ViewApplication( Department dep, Application app){
		this.department = dep;
		//this.appListView = view;
		application = app;
		contInst = new InstructorDepartmentController(department);
		cont = new DepartmentController(department);
		initComponents();
	}
	
	public void initComponents(){
		name = new JLabel("Name: " + application.getStudent().getName());
		//course = new JLabel(jobPosting.getInstructor().getCourse().getId().trim());
		hire = new JButton("Hire");
		//applications = new JButton("Load Applications");
		reject = new JButton("Reject");
		email = new JLabel("Email: " + application.getEmailAddress());
		//instructor = new JLabel("");
		studentLevel = new JLabel("Level: " + application.getStudent().getStudentLevelFullName());
		preference = new JLabel("Preference: " + application.getPreference());
		//start = new JLabel("");
		
		
		description = new JTextArea(application.getPastExperience());
		description.setEditable(false);
		scroll = new JScrollPane(description);
		scroll.setPreferredSize(new Dimension(200, 250));
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(name)
						.addComponent(studentLevel)
						.addComponent(email)
						.addComponent(preference)
						.addComponent(scroll)
						.addGroup(layout.createSequentialGroup()
								.addComponent(hire)
								.addComponent(reject)
								)
						//.addComponent(applications)
						)
				);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(name)
						//.addComponent(course)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(studentLevel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(email)
						//.addComponent(instructor)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(preference)
						//.addComponent(start)
						)
				
				.addComponent(scroll)
				.addGroup(layout.createParallelGroup()
						.addComponent(hire)
						.addComponent(reject)
						)
				
				//.addComponent(applications)
				);
		
		hire.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cont.hireStudentInitialDepartment(application);
				ViewJobPosting.appList.refreshData();
				dispose();
				
			}
		});
		
		reject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				contInst.rejectStudent(application);
				ViewJobPosting.appList.refreshData();
				dispose();
			}
		});
		
//		applications.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		pack();
		
	}
}
