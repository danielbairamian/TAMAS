package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.model.Department;

public class UploadListPage extends JFrame {
	Department department;
	
	private JLabel error;
	private JLabel instLabel;
	private JTextField inst;
	private JLabel courseIDLabel;
	private JTextField courseID;
	private  JLabel numTutLabel;
	private JTextField numTut;
	private JLabel numLabLabel;
	private JTextField numLab;
	private JLabel budgetLabel;
	private JTextField budget;
	
	private JButton submitCourse;
	
	private int selectedInstructor;
	
	private DepartmentController tam;
	
	private String errorMsg = "";
	
	public UploadListPage(Department dep){
		this.department = dep;
		tam = new DepartmentController(department);
		initComponents();
		
	}
	
	public void initComponents(){
		this.setSize(new Dimension(800, 250));
	
		error = new JLabel("");
		error.setForeground(Color.RED);
		
		instLabel = new JLabel("Intsructor Name:");
		inst = new JTextField();
		
		budgetLabel = new JLabel("Given Budget $:");
		budget = new JTextField();
		
		courseIDLabel = new JLabel("Course ID:");
		courseID = new JTextField();
		
		
		numLabLabel = new JLabel("Number of Labs:");
		numLab = new JTextField();
		
		numTutLabel = new JLabel("Number of Tutorials:");
		numTut = new JTextField();
		
		submitCourse = new JButton("Submit Course");
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(error)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(instLabel)
							.addComponent(courseIDLabel)
							.addComponent(budgetLabel)
							.addComponent(numTutLabel)
							.addComponent(numLabLabel)
							)
					.addGroup(layout.createParallelGroup()
							.addComponent(inst)
							.addComponent(courseID)
							.addComponent(budget)
							.addComponent(numTut)
							.addComponent(numLab)
							.addComponent(submitCourse)
						))
				
				);
		
		 layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { courseID, numLab, numTut, budget, inst});
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(error)
				.addGroup(layout.createParallelGroup()
						.addComponent(instLabel)
						.addComponent(inst)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(courseIDLabel)
						.addComponent(courseID)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(budgetLabel)
						.addComponent(budget)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(numTutLabel)
						.addComponent(numTut)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(numLabLabel)
						.addComponent(numLab)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(submitCourse)
						)
				);
		
		
	
	submitCourse.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			submitCourse();
			refreshData();
		}
	});
		
		refreshData();
	}
	private void submitCourse(){
		errorMsg = "";
		float budgetTotal = -1;
		int lab = -1;
		int tut = -1;
		if(inst.getText().trim().equals("")){
			errorMsg = "Please input a valid instructor name";
			return;
		}
		try{
			budgetTotal = Float.parseFloat(budget.getText());
			lab = Integer.parseInt(numLab.getText());
			tut = Integer.parseInt(numTut.getText());
		} catch(Exception e){
			errorMsg = "invalid input for budget/number oflabs/number of tutorials";
			return;
		}
		
		try{
			tam.addACourse( inst.getText(),budgetTotal, courseID.getText(), lab, tut);
		} catch(Exception e){
			errorMsg = e.getMessage();
		}
		refreshData();
	}
	
	public void refreshData() {
		//errorMessage.setText(error);
		error.setText(errorMsg);
		if(errorMsg.trim().length() == 0){
			inst.setText("");
			numLab.setText("");
			numTut.setText("");
			budget.setText("");
			courseID.setText("");
		}
		//errorMsg = "";
		
		pack();
	}
}
