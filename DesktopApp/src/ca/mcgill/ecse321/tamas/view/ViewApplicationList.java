package ca.mcgill.ecse321.tamas.view;

import java.awt.Container;
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

import ca.mcgill.ecse321.tamas.model.Application;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.JobPosting;

public class ViewApplicationList extends JFrame {
	 private JLabel headerLabel;
	   private JScrollPane scroll;
	   private JButton viewApp;
	   private JPanel controlPanel;
	   private JobPosting posting;
	   private Application app;
	   private List<Application> applications;
	   private JList applicationList;
	   private DefaultListModel applicationModel;
	   
	   private Department department;
	   
	 
	   public ViewApplicationList(JobPosting post, Department dep){
		   this.posting = post;
		   this.department = dep;
		   initComponents();
	   }
	   private void initComponents(){
		   controlPanel = new JPanel();
		   Container cont = getContentPane();
		   cont.setLayout(new GridBagLayout());
		   add(controlPanel);
		   
		   applications = posting.getForms();
		   
		   //viewApplications = new JButton("View Applications");
		   viewApp = new JButton("View Application");
		   headerLabel = new JLabel("Applications");
		   applicationModel = new DefaultListModel<>();
		   
		   for(Application a : applications){
			   applicationModel.addElement(a.getStudent().getName());
		   }
		  
		   applicationList = new JList<>(applicationModel);
		   applicationList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		   applicationList.setSelectedIndex(0);
		   applicationList.setVisibleRowCount(5);
		   
		   scroll = new JScrollPane(applicationList);

		   
		  // controlPanel.add(headerLabel);
		   controlPanel.add(scroll);
		   //controlPanel.add(postingList);
		   controlPanel.add(viewApp);
		 
		   GroupLayout layout = new GroupLayout(controlPanel);
		   controlPanel.setLayout(layout);
		   layout.setAutoCreateContainerGaps(true);
		   layout.setAutoCreateGaps(true);
		   
		   layout.setHorizontalGroup(layout.createSequentialGroup()
					   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							   .addComponent(headerLabel)
							   //.addComponent(postingList)
							   .addComponent(scroll)
							   .addComponent(viewApp)
						   )
				   );
		   
		   layout.setVerticalGroup(layout.createSequentialGroup()
						   .addComponent(headerLabel)
						   //.addComponent(postingList)
						   .addComponent(scroll)
						   .addComponent(viewApp)
				   );
		   pack();
		   
		   viewApp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(applicationList.getSelectedIndex() != -1){
					new ViewApplication(department, applications.get(applicationList.getSelectedIndex())).setVisible(true);
				}
			}
		});
		  

		   
	   }
	   
	   public void refreshData(){
		   applications = posting.getForms();
		   applicationModel.removeAllElements();
		   applicationList.removeAll();
		   //System.out.println("size of posting in jplist is " + posting.size());
		  
		   for(Application p : applications){
			   applicationModel.addElement(p.getStudent().getName());
		   }
		   
		   applicationList.setModel(applicationModel);
	   }
}
