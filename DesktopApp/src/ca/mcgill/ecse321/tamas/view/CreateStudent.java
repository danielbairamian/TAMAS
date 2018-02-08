package ca.mcgill.ecse321.tamas.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.model.Department;

public class CreateStudent extends JFrame{
	private JLabel nameLabel;
	private JTextField name;
	private JRadioButton grad;
	private JRadioButton underGrad;
	private JButton submit;
	private Department department;
	private JLabel errorMsg;
	
	private DepartmentController cont;
	public CreateStudent(Department dep){
		this.department = dep;
		cont = new DepartmentController(department);
		initComponents();
	}

	public void initComponents(){
		nameLabel = new JLabel("Name");
		name = new JTextField();
		submit = new JButton("Submit");
		grad = new JRadioButton("Graduate");
		
		underGrad = new JRadioButton("UnderGraduate");
		underGrad.setSelected(true);
		
		errorMsg = new JLabel();
		errorMsg.setForeground(Color.RED);
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(errorMsg)
						.addComponent(underGrad)
						.addComponent(nameLabel)
						)
						
				.addGroup(layout.createParallelGroup()
						.addComponent(grad)	
						.addComponent(name)
						.addComponent(submit)
						)
				
				);
		layout.linkSize(SwingConstants.VERTICAL, new Component[] {name});
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(errorMsg)
				.addGroup(layout.createParallelGroup()
						.addComponent(underGrad)
						.addComponent(grad)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(nameLabel)
						.addComponent(name)
						
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(submit)
						)
				
				);
		
		grad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(underGrad.isSelected()){
					underGrad.setSelected(false);
				} else {
					grad.setSelected(true);
				}
			}
		});
		
		underGrad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(grad.isSelected()){
					grad.setSelected(false);
				} else {
					underGrad.setSelected(true);
				}
			}
		});
		
		submit.addActionListener(new ActionListener() {
			String error = "";
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					cont.createStudent(name.getText(), grad.isSelected());
				} catch (InvalidInputException e1) {
					// TODO Auto-generated catch block
					error = e1.getMessage();
					
				}
				errorMsg.setText(error);
				name.setText("");
				error = "";
			}
		});
	}
}
