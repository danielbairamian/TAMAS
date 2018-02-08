package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.tamas.controller.InstructorDepartmentController;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Evaluation;
import ca.mcgill.ecse321.tamas.model.Instructor;
import ca.mcgill.ecse321.tamas.model.Student;

public class ViewEvaluation extends JFrame {
	private JLabel instructorLabel;
	private JComboBox<String> instructors;
	private JLabel studentLabel;
	private JComboBox<String> students;
	//private JComboBox<String> evals;
//	private JTextArea desc;
//	private JScrollPane scroll;
	private JButton submit;
	private  int instSelected;
	private  int studentSelected;
	private JLabel errorMsg;
	
	private List<Student> studentList;
	private Evaluation studentEval;
	
	private Department department;
	private InstructorDepartmentController cont;
	
	public ViewEvaluation(Department dep){
		this.department = dep;
		cont = new InstructorDepartmentController(department);
		initComponents();
	}
	
	public void initComponents(){
		instructorLabel = new JLabel("Instructors: ");
		instructors = new JComboBox<String>();
		instructors.setSelectedIndex(-1);
		//instructors.setSelectedIndex(-1);
		
		//evals = new JComboBox<String>();
		studentList = new ArrayList<>();
		studentLabel = new JLabel("Students: ");
		students = new JComboBox<String>();
		students.setSelectedIndex(-1);
		//students.setSelectedIndex(-1);
		errorMsg = new JLabel();
		errorMsg.setForeground(Color.RED);
		
		submit = new JButton("View Evaluation");
		
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
						//.addComponent(evals)
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
						.addComponent(submit)
						)
				
				);
		instructors.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
			}
		});
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
				errorMsg.setText("");
				loadEvals();
				if(studentEval != null){
					new EvaluationPage(studentEval).setVisible(true);
				}
			}
				
		});
		
		refreshData();
		pack();
	}
	
	public void loadStudents(){
		if(instructors.getSelectedIndex() != -1)
			studentList = cont.getStudentsWithJobs(department.getInstructor(instructors.getSelectedIndex()));
		
		
		if(studentList.size() > 0){
			for(Student s : studentList){
				students.addItem(s.getName());
			}
		}
		
		students.setSelectedIndex(-1);
	}
	
	public void loadEvals(){
		studentEval = null;
		if(studentList.size() > 0){
			if(studentList.get(studentSelected).getEvaluations().size() > 0 ){
				for(Evaluation e : studentList.get(studentSelected).getEvaluations()){
					if(e.getInstructorName() == department.getInstructor(instSelected).getName()){
						studentEval = e;
						
					}
				}
			}
		}
	}
	
	public void refreshData(){
		
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
		//instructors.setSelectedIndex(0);
		instructors.addActionListener(inst[0]);
		instructors.setSelectedIndex(-1);
	}
}
