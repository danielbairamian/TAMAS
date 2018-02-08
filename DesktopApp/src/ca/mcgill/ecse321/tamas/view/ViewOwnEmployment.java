package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.Registration;
import ca.mcgill.ecse321.tamas.model.Student;

public class ViewOwnEmployment extends JFrame{

	//UI Elements
	private JLabel errorMessage;

	//students elements
	private JLabel studentLabel;
	private JComboBox<String> studentComboBox;

	
	private JPanel controlPanel;

	//employments
	private JButton seeJobButton;
	private List<Job> employments;
	private JList<String> employmentList;
	private DefaultListModel employmentModel;
	private JScrollPane scroll;
	
	//data elements
	private DepartmentController departmentController;
	private String error = null;
	private int selectedStudentIndex;
	private Student selectedStudent;
	private Department department;
	

	public ViewOwnEmployment(Department department){
		this.department = department;
		departmentController = new DepartmentController(department);
		initComponents();
		refreshData();
	}

	public void initComponents(){
		controlPanel = new JPanel();
		Container cont = getContentPane();
		cont.setLayout(new GridBagLayout());
		add(controlPanel);
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.red);
		studentLabel = new JLabel();
		studentComboBox = new JComboBox<String>();
		seeJobButton = new JButton();
		employmentList = new JList<String>();
		employments = new ArrayList<Job>();
		employmentModel = new DefaultListModel<>();
		scroll = new JScrollPane(employmentList);
		populateStudentComboBox();
						
		studentLabel.setText("Student:");
		seeJobButton.setText("See job");
		
		
		studentComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				selectedStudentIndex = cb.getSelectedIndex();
				populateJList();
			}
		});
		
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(errorMessage)
						.addComponent(studentLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(studentComboBox)
						.addComponent(scroll)
						)
				
				);
		
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[]{studentComboBox, studentLabel});
		//layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]{});
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addComponent(studentLabel)
						.addComponent(studentComboBox)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(scroll)
						)
				);
		
		pack();
		
	}
	
	
	private void populateJList(){
		if(selectedStudentIndex >= 0){
			selectedStudent = departmentController.getStudentAt(selectedStudentIndex);
			department.setCurrentStudent(selectedStudent);
			List<Registration> registrations = selectedStudent.getRegistrations();
			
			employmentList.removeAll();
			employmentModel.removeAllElements();
			employments.clear();
			for(Registration registration : registrations){
				if(registration.getJob().getJobState().equals(Job.JobState.Filled)){
					employments.add(registration.getJob());
				}
			}
			if(employments!=null){
				for(Job job : employments){
					employmentModel.addElement(job.getJobPosting().getInstructor().getCourse().getId() + " " + job.getJobTypeFullName());
				}
				
				employmentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
				employmentList.setSelectedIndex(0);
				employmentList.setVisibleRowCount(5);
				
				employmentList.setModel(employmentModel);
			}else{
				error = "This student has no current Job";
			}
			
		}
		
		
	}
	
	public void refreshData(){
		
		if(error == null || error.isEmpty()){
			errorMessage.setText("");
			populateStudentComboBox();
		}else{
			errorMessage.setText(error);
			error = "";
		}
		
	}
	
	private void populateStudentComboBox(){
		studentComboBox.removeAllItems();
		List<Student> students = departmentController.getAllStudents();
		if(students == null | students.isEmpty()){
			error = "There are no students in this department";
		}else{
			for(Student student: students){
				studentComboBox.addItem(student.getName());				
			}
		}
	}
		
}
