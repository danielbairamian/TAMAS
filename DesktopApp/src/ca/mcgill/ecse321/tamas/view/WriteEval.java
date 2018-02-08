package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.tamas.controller.InstructorDepartmentController;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Instructor;
import ca.mcgill.ecse321.tamas.model.Student;

public class WriteEval extends JFrame{
	private JLabel errorMsg;
	private JLabel instructorLabel;
	private JComboBox<String> instructors;
	private JLabel studentLabel;
	private JComboBox<String> students;
	private JTextArea desc;
	private JScrollPane scroll;
	private JButton submit;
	private  int instSelected;
	private  int studentSelected;
	private String error = "";
	private List<Student> studentList;
	
	private Department department;
	private InstructorDepartmentController cont;
	
	public WriteEval(Department dep){
		this.department = dep;
		cont = new InstructorDepartmentController(department);
		initComponents();
	}
	
	public void initComponents(){
		instructorLabel = new JLabel("Instructors: ");
		instructors = new JComboBox<String>();
		
		errorMsg = new JLabel("");
		errorMsg.setForeground(Color.RED);
		studentLabel = new JLabel("Students: ");
		students = new JComboBox<String>();
		studentList = new ArrayList<>();
		//students.setSelectedIndex(-1);
		
		desc = new JTextArea(5,10);
		scroll = new JScrollPane(desc);
		scroll.setPreferredSize(new Dimension(200, 250));
		
		submit = new JButton("Submit Evaluation");
		instructors.setSelectedIndex(-1);
		
//		if(department.getStudents().size() < 8){	
//			Student stud = new Student("josopo", department);
//			Job job = new Job(20, 15, department.getInstructor(0).getJobOffering(0).getStartDate(), department.getInstructor(0).getJobOffering(0).getEndDate(), department.getInstructor(0).getJobOffering(0));
//			System.out.println(department.getInstructor(0).getName());
//			Registration reg = new Registration(new java.sql.Date(System.currentTimeMillis()), stud, job);
//		}
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(errorMsg)
						.addComponent(instructorLabel)
						.addComponent(studentLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(instructors)
						.addComponent(students)
						.addComponent(scroll)
						.addComponent(submit)
						)
				);
		layout.linkSize(SwingConstants.VERTICAL, new Component[] {instructors, students});
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(errorMsg)
				.addGroup(layout.createParallelGroup()
						.addComponent(instructorLabel)
						.addComponent(instructors)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(studentLabel)
						.addComponent(students)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(scroll)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(submit)
						)
				
				);
		instructors.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("g");
				
				 JComboBox<String> cb = (JComboBox<String>) e.getSource();
		         instSelected = cb.getSelectedIndex();
				students.removeAllItems();
				loadStudents();
			}
		});
		
		students.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 JComboBox<String> cb = (JComboBox<String>) e.getSource();
		         studentSelected = cb.getSelectedIndex();
				//System.out.println("s " + studentSelected);
			}
		});
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("i " + instSelected + " s " + studentSelected);
				String error = "";
				if(instructors.getSelectedIndex() == -1){
					error = "No instructor selected";
					errorMsg.setText(error);
					return;
				}
				if(students.getSelectedIndex() == -1 ){
					error = "No student selected";
					errorMsg.setText(error);
					return;
				}
				try{
					cont.writeEvaluatuation(desc.getText(), department.getInstructor(instSelected), studentList.get(studentSelected));
				} catch (InvalidInputException e1){
					error = e1.getMessage();
					//System.out.println("error is " + error);
				}
				//desc.setText("");
				refreshData();
			}
		});
		
		refreshData();
		pack();
	}
	
	public void loadStudents(){
		studentList = cont.getStudentsWithJobs(department.getInstructor(instSelected));

		//System.out.println(studentList.size() + "  this is student list size");
		if(studentList.size() > 0){
			for(Student s : studentList){
				students.addItem(s.getName());
			}
		}
		//students.setSelectedIndex(0);
	}
	
	public void refreshData(){
		if(error.trim().length() != 0){
			errorMsg.setText(error);
		} else {
			errorMsg.setText("");
		}
		ActionListener[] inst = new ActionListener[instructors.getActionListeners().length];
		int counter = 0;
		for (ActionListener a : instructors.getActionListeners()){
			inst[counter] = a;
			counter++;
			instructors.removeActionListener(a);
		}
		
		instructors.removeAllItems();
		students.removeAllItems();
		
		
		for(Instructor i : department.getInstructors()){
			instructors.addItem(i.getName());
		}
		instructors.setSelectedIndex(-1);
		instructors.addActionListener(inst[0]);
		desc.setText("");
		
	}
}
