package ca.mcgill.ecse321.tamas.view;



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Instructor;

public class TamasPage extends JFrame {

	private JLabel errorMessage;
	private JLabel instructorLabel;
	private JLabel hoursLabel;
	private JTextField hours;
	private JLabel underGradWageLabel;
	private JLabel gradWageLabel;
	private JTextField underGradWage;
	private JTextField gradWage;
//	private JLabel wageLabel;
//	private JTextField wage;
	private JLabel positionLabel;
	private JTextField position;
	private JComboBox<String> instructorList;
	private JDatePickerImpl startDatePicker;
	private JDatePickerImpl endDatePicker;
	private JDatePickerImpl deadline;
	private JLabel startDateLabel;
	private JLabel endDateLabel;
	private JLabel deadlineLabel;
	private JLabel jobDescLabel;
	private JTextArea jobDesc;
	private JButton postJobPostingButton;
	private JScrollPane scroll;
	private Department theDepartment;
	private String error = null;

	private Integer selectedInstructor = -1;

	public TamasPage(Department Department) {
		this.theDepartment = Department;
		initComponents();
		refreshData();
	}


	public void initComponents() {
//		Course c = new Course(10, "di", 4, 3, theDepartment);
//		Instructor inst = new Instructor("bobert", c, theDepartment);
//		theDepartment.addInstructor(inst);
		//elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		// elements for deadline
		deadlineLabel = new JLabel();
		
		hoursLabel = new JLabel("Hours: ");
		hours = new JTextField();
		
		underGradWageLabel = new JLabel("UnderGraduate Wage: ");
		underGradWage = new JTextField();
		
		gradWageLabel = new JLabel("Graduate Wage: ");
		gradWage = new JTextField();
		
		positionLabel = new JLabel("Job Type: ");
		position = new JTextField();
		
		instructorLabel = new JLabel();
		instructorList = new JComboBox<String>();
		
		jobDesc = new JTextArea(5,10);
		jobDesc.setSize(200, 400);
		jobDesc.setWrapStyleWord(true);
		jobDesc.setLineWrap(true);
		jobDescLabel = new JLabel();
		
		scroll = new JScrollPane(jobDesc);
		scroll.setPreferredSize(new Dimension(200, 200));
		
		startDateLabel = new JLabel();
		endDateLabel = new JLabel();
		deadlineLabel = new JLabel();
		
		postJobPostingButton = new JButton();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		
		SqlDateModel model = new SqlDateModel();
		SqlDateModel model1 = new SqlDateModel();
		SqlDateModel model2 = new SqlDateModel();
		   Properties p = new Properties();
		   p.put("text.today", "Today");
		   p.put("text.month", "Month");
		   p.put("text.year", "Year");
		   JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		   JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
		   JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		   startDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		   endDatePicker = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		   deadline = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		

		setTitle("Managment System");
		instructorLabel.setText("Instructors");
		jobDescLabel.setText("Job Description");
		startDateLabel.setText("Start Date");
		endDateLabel.setText("End Date");
		deadlineLabel.setText("Application Deadline");
		postJobPostingButton.setText("Post Job");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(

	            layout.createParallelGroup()
	            .addComponent(errorMessage)
	            .addGroup(layout.createSequentialGroup()
	                    .addGroup(layout.createParallelGroup()
	                            .addComponent(instructorLabel)
	                            .addComponent(jobDescLabel))
	                    .addGroup(layout.createParallelGroup()
	                            .addComponent(instructorList)
	                            .addComponent(scroll))
	                    .addGroup(layout.createParallelGroup()
	                    		.addComponent(hoursLabel)
	                    		//.addComponent(wageLabel)
	                    		.addComponent(positionLabel)
	                    		.addComponent(underGradWageLabel)
	                    		.addComponent(gradWageLabel)
	                    		.addComponent(startDateLabel)
	                    		.addComponent(endDateLabel)
	                    		.addComponent(deadlineLabel))
	                    .addGroup(layout.createParallelGroup()
	                    		.addComponent(hours)
	                    		//.addComponent(wage)
	                    		.addComponent(position)
	                    		.addComponent(underGradWage)
	                    		.addComponent(gradWage)
	                    		.addComponent(startDatePicker)
	                    		.addComponent(endDatePicker)
	                    		.addComponent(deadline)
	                    		.addComponent(postJobPostingButton)))
	                    
	            
	            
	        );

	    layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {postJobPostingButton, hours, position, gradWage, underGradWage});
	    layout.linkSize(SwingConstants.HORIZONTAL, new Component [] {postJobPostingButton,instructorList});

		
		layout.setVerticalGroup(
	            layout.createSequentialGroup()
	    .addComponent(errorMessage)
	            .addGroup(layout.createParallelGroup()
	                    .addComponent(instructorLabel)
	                    .addComponent(instructorList)
	                    )
	            .addGroup(layout.createParallelGroup()
	            		.addComponent(hoursLabel)
	            		.addComponent(hours)
	            		)
	            .addGroup(layout.createParallelGroup()
	            		.addComponent(positionLabel)
	            		.addComponent(position)
	            		)
	            .addGroup(layout.createParallelGroup()
	            		.addComponent(underGradWageLabel)
	            		.addComponent(underGradWage)
	            		)
	            .addGroup(layout.createParallelGroup()
	            		.addComponent(gradWageLabel)
	            		.addComponent(gradWage)
	            		)
	            .addGroup(layout.createParallelGroup()
	            		.addComponent(startDateLabel)
	                    .addComponent(startDatePicker)
	            		)
	            .addGroup(layout.createParallelGroup()
	            		.addComponent(endDateLabel)
		            	.addComponent(endDatePicker)
	            		)
	            .addGroup(layout.createParallelGroup()
	            		.addComponent(deadlineLabel)
	            		.addComponent(deadline))
	            .addComponent(postJobPostingButton)
	            .addGroup(layout.createParallelGroup()
	            	.addComponent(jobDescLabel)
	            	.addComponent(scroll))
	            
	            
	           
	        );
		
		instructorList.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(java.awt.event.ActionEvent evt) {
		        	
		            JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		            selectedInstructor = cb.getSelectedIndex();
		        }
		    });
		
		postJobPostingButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				postJob();
			}
		});
		
		refreshData();
		
	}
	
	public void postJob(){
		error = "";
		
		error = error.trim();
		int hour = -1;
		
		try{
			hour = Integer.parseInt(hours.getText());
			
		} catch(Exception e){
			//e.printStackTrace();
			errorMessage.setText("Please enter valid hours");
			return;
		}
			int underWage = -1;
			int gWage = -1;
			try{
				underWage = Integer.parseInt(underGradWage.getText());
				gWage = Integer.parseInt(gradWage.getText());
			}catch(Exception e){
				errorMessage.setText("Please enter valid wages");
			}
		
			DepartmentController tamas = new DepartmentController(theDepartment);
			try{
				tamas.addJobPosting((java.sql.Date) deadline.getModel().getValue(), (java.sql.Date) startDatePicker.getModel().getValue(), (java.sql.Date) endDatePicker.getModel().getValue(), jobDesc.getText(), selectedInstructor, hour, underWage, gWage, position.getText() );
			}catch(InvalidInputException e){
				error += e.getMessage();
			}
		
		refreshData();
	}
	
	public void refreshData() {
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			instructorList.removeAllItems();
			for(Instructor i : theDepartment.getInstructors()){
				instructorList.addItem(i.getName());
				//System.out.println(instructorList.getSize());
			}
			selectedInstructor = -1;
			instructorList.setSelectedIndex(selectedInstructor);
			underGradWage.setText("");
			gradWage.setText("");
			jobDesc.setText("");
			hours.setText("");
			position.setText("");
			startDatePicker.getModel().setValue(null);
			endDatePicker.getModel().setValue(null);
			deadline.getModel().setValue(null);
		}
		pack();
	}
	
}