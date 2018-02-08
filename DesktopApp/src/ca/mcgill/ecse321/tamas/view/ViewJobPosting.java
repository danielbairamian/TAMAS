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

import ca.mcgill.ecse321.tamas.controller.InstructorDepartmentController;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.JobPosting;

public class ViewJobPosting extends JFrame {
	private JLabel courseLabel;
	//private JLabel course;
	private JLabel instructorLabel;
	//private JLabel instructor;
	private JLabel startLabel;
	//private JLabel start;
	private JLabel endLabel;
	//private JLabel end;
	private JLabel deadlineLabel;
	//private JLabel deadline;
	private JScrollPane scroll;
	private JTextArea description;
	private JButton applications;
	private JButton deletePosting;
	
	
	
	private JobPosting jobPosting;
	private Department department;
	private InstructorDepartmentController cont;
	protected static ViewApplicationList appList;
	public ViewJobPosting(Department dep, JobPosting posting){
		this.department = dep;
		jobPosting = posting;
		cont = new InstructorDepartmentController(department);
		initComponents();
	}
	
	public void initComponents(){
		
		this.setPreferredSize(new Dimension(1000, 600));
		courseLabel = new JLabel("Course: " + jobPosting.getInstructor().getCourse().getId().trim());
		//course = new JLabel(jobPosting.getInstructor().getCourse().getId().trim());
		
		applications = new JButton("Load Applications");
		
		instructorLabel = new JLabel("Instructor: " + jobPosting.getInstructor().getName());
		//instructor = new JLabel("");
		
		startLabel = new JLabel("Start Date: " + jobPosting.getStartDate());
		//start = new JLabel("");
		
		endLabel = new JLabel("End Date: " + jobPosting.getEndDate());
		//end = new JLabel("");
		
		deadlineLabel = new JLabel("Application Deadline: " + jobPosting.getDeadline());
		//deadline = new JLabel("");
		deletePosting = new JButton("Delete posting");
		description = new JTextArea(jobPosting.getDescription());
		description.setEditable(false);
		scroll = new JScrollPane(description);
		scroll.setPreferredSize(new Dimension(200, 250));
		
		/*if(department.getStudents().size() < 3){
			Student stud = new Student("Joe ", department);
			Application app = new Application("yaboi@gmail.com", "none", 1, department.getInstructor(0).getJobOffering(0), stud);
			Job job = new Job(20, 20, department.getInstructor(0).getJobOffering(0).getStartDate(), department.getInstructor(0).getJobOffering(0).getEndDate(), department.getInstructor(0).getJobOffering(0));
			job.setJobState(Job.JobState.Offered);
			department.getCourse(0).setBudgetActual(department.getCourse(0).getBudgetGiven());
			Registration reg = new Registration(new Date(System.currentTimeMillis()), stud, job);
		}*/
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(courseLabel)
						.addComponent(instructorLabel)
						.addComponent(startLabel)
						.addComponent(endLabel)
						.addComponent(deadlineLabel)
						.addComponent(scroll)
						)
				.addGroup(layout.createSequentialGroup()
						.addComponent(applications)
						.addComponent(deletePosting)
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
						.addComponent(startLabel)
						//.addComponent(start)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(endLabel)
						//.addComponent(end)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(deadlineLabel)
						//.addComponent(deadline)
						)
				
				.addComponent(scroll)
				.addGroup(layout.createParallelGroup()
						.addComponent(applications)
						.addComponent(deletePosting)
						)
				);
		
		applications.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				appList = new ViewApplicationList(jobPosting, department);
				appList.setVisible(true);
				dispose();
			}
		});
		
		deletePosting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					cont.deleteJobPosting(jobPosting);
				} catch (InvalidInputException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				TabbedPanel.list.refreshData();
				dispose();
			}
		});
		
		pack();
		
	}
}
