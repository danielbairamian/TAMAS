package ca.mcgill.ecse321.tamas.application;


import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.persistence.PersistenceXStream;
import ca.mcgill.ecse321.tamas.view.TabbedPanel;

public class DesktopApplication {

	private static String fileName = "output/department.xml";

	public static void main(String[] args) {
		//load model
		final Department department =  PersistenceXStream.initializeModelManager(fileName);

		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TabbedPanel(department).setVisible(true);
            }
        });

	}

}
