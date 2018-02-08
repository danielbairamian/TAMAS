package ca.mcgill.ecse321.tamas.view;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.mcgill.ecse321.tamas.model.Department;

public class TabbedPanel extends JPanel {
	private static Department dep;
	private TamasPage tam;
	private UploadListPage up;
	protected static JobPostingList list;
	private ViewCourses course;
	private WriteEval eval;
	private ViewEvaluation viewEval;
	protected static ModifyAllocation allocation;
	private ViewJobOffer viewOffer;
	protected static ModifyAllocationDepartment departAllocation;
	private ViewOwnEmployment viewEmployment;
    private DisplayJobPosting displayJobPosting;
	private static JTabbedPane tabbedPane;

    public TabbedPanel(Department department) {
        super(new GridLayout(1, 1));

        dep = department;
        initComponents();

    }

	private void initComponents() {
		tabbedPane = new JTabbedPane();
		
		 tabbedPane.addTab("Create Student", new CreateStudent(dep).getContentPane());

		 up = new UploadListPage(dep);
		 tabbedPane.addTab("Add Courses", up.getContentPane());
		 
		  course = new ViewCourses(dep);
	       tabbedPane.addTab("View Course", course.getContentPane());
		 
        tam = new TamasPage(dep);
        tabbedPane.addTab("Add Job Posting", tam.getContentPane());


       list = new JobPostingList(dep);
        tabbedPane.addTab("View Posting",list.getContentPane());

       displayJobPosting = new DisplayJobPosting(dep);
       tabbedPane.addTab("Display Job", displayJobPosting.getContentPane());
       
       allocation = new ModifyAllocation(dep);
       tabbedPane.addTab("Modify Allocation",allocation.getContentPane() );
       
       departAllocation = new ModifyAllocationDepartment(dep);
       tabbedPane.addTab("Final Allocation", departAllocation.getContentPane());
     

       viewOffer = new ViewJobOffer(dep);
       tabbedPane.addTab("View job offers", viewOffer.getContentPane());
       

       viewEmployment = new ViewOwnEmployment(dep);
       tabbedPane.addTab("View employments", viewEmployment.getContentPane());

       eval = new WriteEval(dep);
       tabbedPane.addTab("Write Evaluation",eval.getContentPane() );


       viewEval = new ViewEvaluation(dep);
       tabbedPane.addTab("View Evaluation",viewEval.getContentPane() );
       
      

        tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				refreshData();
			}
		});

        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        run();
	}

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);

        return panel;
    }

    private void refreshData(){
    	tam.refreshData();
    	list.refreshData();
    	course.refreshData();
    	eval.refreshData();
    	viewEval.refreshData();
    	viewOffer.refreshData();
    	allocation.refreshData();
    	departAllocation.refreshData();
    	viewEmployment.refreshData();
    	displayJobPosting.refreshData();
   
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Department");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        //frame.add(new TabbedPanel(dep), BorderLayout.CENTER);
         frame.add(tabbedPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void run() {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGUI();

    }
}
