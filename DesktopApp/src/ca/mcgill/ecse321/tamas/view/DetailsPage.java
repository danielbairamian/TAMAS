package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.controller.InstructorDepartmentController;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Job;

public class DetailsPage extends JFrame{
	private JLabel errorMsg;
	private JLabel nameLabel;
	private JLabel wageLabel;
	private JLabel hoursLabel;
	private JLabel newHoursLabel;
	private JLabel newWageLabel;
	private JTextField newWage;
	private JTextField newHours;
	private JButton submit;
	private JButton reject;
	
	private Department department;
	private DepartmentController depCont;
	private InstructorDepartmentController instCont;
	
	private Job job;
	private Course course;
	private String error = "";
	private boolean departmentOrInstructorBool;
	
	public DetailsPage(Department dep ,Course course, Job job, boolean departmentBool){
		this.department = dep;
		this.departmentOrInstructorBool = departmentBool;
		this.job = job;
		this.course = course;
		depCont = new DepartmentController(department);
		instCont = new InstructorDepartmentController(department);
		initComponents();
	}
	
	private void initComponents(){
		nameLabel = new JLabel("Name: " + job.getRegistration().getStudent().getName());
		wageLabel = new JLabel("Current Wage: " + job.getHourlyRate());
		hoursLabel = new JLabel("Current Hours:" + job.getWorkHours());
		newHoursLabel = new JLabel("New Hours: ");
		newHours = new JTextField();
		newWageLabel = new JLabel("New Wage: ");
		newWage = new JTextField();
		errorMsg = new JLabel("");
		errorMsg.setForeground(Color.RED);
		submit = new JButton("Submit");
		reject = new JButton("Reject");
		if(!departmentOrInstructorBool){
			reject.setVisible(false);
		}
		
		if(departmentOrInstructorBool){
			newHours.setVisible(false);
			newHoursLabel.setVisible(false);
			newWage.setVisible(false);
			newWageLabel.setVisible(false);
		}
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(errorMsg)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(nameLabel)
								.addComponent(wageLabel)
								.addComponent(hoursLabel)
								.addComponent(newHoursLabel)
								.addComponent(newWageLabel)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(newHours)
								.addComponent(newWage)
								.addGroup(layout.createSequentialGroup()
										.addComponent(submit)
										.addComponent(reject)
										)
								)
						)
				);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(errorMsg)
						.addGroup(layout.createParallelGroup()
								.addComponent(nameLabel)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(wageLabel)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(hoursLabel)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(newHoursLabel)
								.addComponent(newHours)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(newWageLabel)
								.addComponent(newWage)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(submit)
								.addComponent(reject)
								)
						
				);
		
		reject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				depCont.rejectAllocation(job.getRegistration());
				TabbedPanel.allocation.refreshData();
				TabbedPanel.departAllocation.refreshData();
				dispose();
				
			}
		});
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				error = "";
				int wage = -1;
				int hours = -1;
				try{
					wage = Integer.parseInt(newWage.getText());
					hours = Integer.parseInt(newHours.getText());
				} catch(Exception e1){
					
				}
				if(departmentOrInstructorBool){
					try {
						depCont.approveFinalAllocation(job.getRegistration());
					} catch (InvalidInputException e1) {
						// TODO Auto-generated catch block
						
					}
				} else {
					try {
						instCont.modifyAllocation(course, job, hours, wage);
					} catch (InvalidInputException e1) {
						// TODO Auto-generated catch block
						error+= e1.getMessage();
					}

				}
				TabbedPanel.allocation.refreshStudents();
				TabbedPanel.departAllocation.refreshStudents();
				if(error.trim().length() == 0){
					dispose();
				}
				refreshError();
			}
		});
		
		pack();
	}
	
	public void refreshError(){
			errorMsg.setText(error);
			
		pack();
	}
}
