package ca.mcgill.ecse321.tamas.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import ca.mcgill.ecse321.tamas.model.Application;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Evaluation;
import ca.mcgill.ecse321.tamas.model.Instructor;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.JobPosting;
import ca.mcgill.ecse321.tamas.model.Registration;
import ca.mcgill.ecse321.tamas.model.Student;


public class PersistenceXStream {
	
	private static XStream xstream = new XStream();
	private static String filename = "department.txt";
	

    public static Department initializeModelManager(String fileName) {
        // Initialization for persistence
        Department department;
        setFilename(fileName);
        setAlias("application", Application.class);
        setAlias("course", Course.class);
        setAlias("department", Department.class);
        setAlias("instructor", Instructor.class);
        setAlias("job", Job.class);
        setAlias("jobPosting", JobPosting.class);
        setAlias("registration", Registration.class);
        setAlias("student", Student.class);
        setAlias("evaluation", Evaluation.class);
        

        // load model if exists, create otherwise
        File file = new File(fileName);
        if (file.exists()) {
            department = (Department) loadFromXMLwithXStream();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            department = new Department();
            saveToXMLwithXStream(department);
        }
        return department;

    }

    public static boolean saveToXMLwithXStream(Object obj) {
        xstream.setMode(XStream.ID_REFERENCES);
        String xml = xstream.toXML(obj); // save our xml file

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object loadFromXMLwithXStream() {
        xstream.setMode(XStream.ID_REFERENCES);
        try {
            FileReader fileReader = new FileReader(filename); // load our xml file
            return xstream.fromXML(fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setAlias(String xmlTagName, Class<?> className) {
        xstream.alias(xmlTagName, className);
    }

    public static void setFilename(String fn) {
        filename = fn;
    }
}
