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
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.JobPosting;

public class JobPostingList extends JFrame {
	   private JLabel headerLabel;
	   //private JLabel statusLabel;
	   private JScrollPane scroll;
	   private JButton viewJob;
	   private JPanel controlPanel;
	   private Department theDepartment;
	   private DepartmentController tam;
	   private List<JobPosting> posting;
	   private JList postingList;
	   private DefaultListModel postingModel;

	   public JobPostingList(Department dep){
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
		   
		   posting = tam.getJobPostings();
		   
		   viewJob = new JButton("View Posting");
		   headerLabel = new JLabel("Job Postings");
		   postingModel = new DefaultListModel<>();
		   
		   for(JobPosting p : posting){
			   postingModel.addElement(p.getInstructor().getCourse().getId() + "-" + p.getJob(0).getJobTypeFullName());
		   }
		  
		   postingList = new JList<>(postingModel);
		   postingList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		   postingList.setSelectedIndex(0);
		   postingList.setVisibleRowCount(5);
		   
		   scroll = new JScrollPane(postingList);
		   scroll.setPreferredSize(new Dimension(600, 400));
		   
		  // controlPanel.add(headerLabel);
		   controlPanel.add(scroll);
		   //controlPanel.add(postingList);
		   controlPanel.add(viewJob);
		 
		   GroupLayout layout = new GroupLayout(controlPanel);
		   controlPanel.setLayout(layout);
		   layout.setAutoCreateContainerGaps(true);
		   layout.setAutoCreateGaps(true);
		   
		   layout.setHorizontalGroup(layout.createSequentialGroup()
					   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							   .addComponent(headerLabel)
							   //.addComponent(postingList)
							   .addComponent(scroll)
							   .addComponent(viewJob)
						   )
				   );
		   
		   layout.setVerticalGroup(layout.createSequentialGroup()
						   .addComponent(headerLabel)
						   //.addComponent(postingList)
						   .addComponent(scroll)
						   .addComponent(viewJob)
				   );
		   
		   viewJob.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(postingList.getSelectedIndex() != -1){
					new ViewJobPosting(theDepartment, posting.get(postingList.getSelectedIndex())).setVisible(true);;
				}
			}
		});
		  

		   
	   }
	   
	   public void refreshData(){
		   posting = tam.getJobPostings();
		   postingModel.removeAllElements();
		   postingList.removeAll();
		  
		   for(JobPosting p : posting){
			   postingModel.addElement(p.getInstructor().getCourse().getId() + "-" + p.getJob(0).getJobTypeFullName() );
		   }
		   
		   postingList.setModel(postingModel);
	   }
	  

	
	
}
