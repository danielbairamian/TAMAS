package ca.mcgill.ecse321.tamas.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;

public class ViewCourses extends JFrame {
	 private JLabel headerLabel;
	   //private JLabel statusLabel;
	   private JScrollPane scroll;
	   private JButton viewCourse;
	   private JPanel controlPanel;
	   private Department theDepartment;
	   private DepartmentController tam;
	   private List<Course> courses;
	   private JList courseList;
	   private DefaultListModel coursesModel;

	   public ViewCourses(Department dep){
		   this.theDepartment = dep;
		   tam = new DepartmentController(theDepartment);
		   //posting = tam.getJobPostingList();
		   initComponents();
	   }
	   private void initComponents(){
		   controlPanel = new JPanel();
		   Container cont = getContentPane();
		   cont.setLayout(new GridBagLayout());
		   add(controlPanel);
		   
		   courses = tam.getCourses();
		   
		   viewCourse = new JButton("View Course");
		   headerLabel = new JLabel("Courses");
		   coursesModel = new DefaultListModel<>();
		   for(Course c : courses){
			   coursesModel.addElement(c.getId());
		   }
		  
		   courseList = new JList<>(coursesModel);
		   courseList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		   courseList.setSelectedIndex(0);
		   courseList.setVisibleRowCount(5);
		   
		   scroll = new JScrollPane(courseList);
		   scroll.setPreferredSize(new Dimension(600, 400));
		   
		  // controlPanel.add(headerLabel);
		   controlPanel.add(scroll);
		   //controlPanel.add(postingList);
		   controlPanel.add(viewCourse);
		 
		   GroupLayout layout = new GroupLayout(controlPanel);
		   controlPanel.setLayout(layout);
		   layout.setAutoCreateContainerGaps(true);
		   layout.setAutoCreateGaps(true);
		   
		   layout.setHorizontalGroup(layout.createSequentialGroup()
					   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							   .addComponent(headerLabel)
							   //.addComponent(postingList)
							   .addComponent(scroll)
							   .addComponent(viewCourse)
						   )
				   );
		   
		   layout.setVerticalGroup(layout.createSequentialGroup()
						   .addComponent(headerLabel)
						   //.addComponent(postingList)
						   .addComponent(scroll)
						   .addComponent(viewCourse)
				   );
		   
		   viewCourse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(courseList.getSelectedIndex() != -1){
					new CourseView(courses.get(courseList.getSelectedIndex())).setVisible(true);
				}
			}
		});
		  

		   
	   }
	   
	   public void refreshData(){
		   courses = tam.getCourses();
		   coursesModel.removeAllElements();
		   courseList.removeAll();
		   
		   for(Course c : courses){
			   coursesModel.addElement(c.getId());
		   }
		   
		   courseList.setModel(coursesModel);
	   }
}
