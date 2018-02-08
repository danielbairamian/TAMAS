package ca.mcgill.ecse321.tamas.view;

import java.awt.Dimension;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.mcgill.ecse321.tamas.model.Evaluation;
import ca.mcgill.ecse321.tamas.model.Student;

public class EvaluationPage extends JFrame {
	private JLabel instructorLabel;
	private JLabel studentLabel;
	private JTextArea desc;
	private JScrollPane scroll;
	
	private List<Student> studentList;
	
	private Evaluation evaluation;
	
	public EvaluationPage(Evaluation eval){
		this.evaluation = eval;
		
		initComponents();
	}
	
	public void initComponents(){
		instructorLabel = new JLabel("Instructor: " + evaluation.getInstructorName());
		//instructors.setSelectedIndex(-1);
		
		studentLabel = new JLabel("Student: " + evaluation.getStudent().getName());
		//students.setSelectedIndex(-1);
		
		desc = new JTextArea(5,10);
		desc.setText(evaluation.getText());
		desc.setEditable(false);
		scroll = new JScrollPane(desc);
		scroll.setPreferredSize(new Dimension(200, 250));
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(instructorLabel)
						.addComponent(studentLabel)
						.addComponent(scroll)
						)
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(instructorLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(studentLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(scroll)
						)
				);
		pack();
	}
}
