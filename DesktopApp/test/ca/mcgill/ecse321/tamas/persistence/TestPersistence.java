package ca.mcgill.ecse321.tamas.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.tamas.model.Application;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Instructor;
import ca.mcgill.ecse321.tamas.model.JobPosting;
import ca.mcgill.ecse321.tamas.model.Student;

public class TestPersistence {

	private Department department;

	@Before
	public void setUp() throws Exception {
		department = new Department();

		//create a course
		Course course = new Course(10, "ecse321", 2, 3, department);
		Course course1 = new Course(10, "ecse322", 2, 3, department);
		
		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);
		Instructor instructor1 = new Instructor("alice", course1, department);
		
		//create a student
		Student student = new Student("alice", department);
		Student student2 = new Student("bob", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);
		
		//creating a new job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);
		JobPosting job1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor1);
		
		//creating an application
		Application application = new Application("bob@gmail.com", "zero", 1, job, student);
		
	
	}

	@After
	public void tearDown() throws Exception {
		department.delete();
	}

	@Test
	public void test() throws ParseException {
		// initialize model file
	    PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
	    
	    // save model that is loaded during test setup
	    if (!PersistenceXStream.saveToXMLwithXStream(department))
	        fail("Could not save file.");

	    // clear the model in memory
	    department.delete();
	    assertEquals(0, department.getInstructors().size());
	    assertEquals(0, department.getCourses().size());
	    assertEquals(0, department.getStudents().size());
	    
	    //load model
	    department = (Department) PersistenceXStream.loadFromXMLwithXStream();
	    if (department == null)
	        fail("Could not load file.");
	    
	    //check courses
	    assertEquals(2, department.getCourses().size());
	    assertEquals("ecse321", department.getCourse(0).getId());
	    assertEquals("ecse322", department.getCourse(1).getId());
	    /*assertEquals(10, department.getCourse(0).getBudgetGiven());
	    assertEquals(10, department.getCourse(1).getBudgetGiven());*/
	    assertEquals(2, department.getCourse(0).getNumLabs());
	    assertEquals(2, department.getCourse(0).getNumLabs());
	    
	    //check instructor
	    assertEquals(2, department.getInstructors().size());
	    assertEquals("bob", department.getInstructor(0).getName());
	    assertEquals("alice", department.getInstructor(1).getName());
	    	    
	    //check student
	    assertEquals(2, department.getStudents().size());
	    assertEquals("alice", department.getStudent(0).getName());
	    assertEquals("bob", department.getStudent(1).getName());
	    
	    //creating a new job posting
	    java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		assertEquals(1, department.getInstructor(0).numberOfJobOfferings());
		assertEquals("hi", department.getInstructor(0).getJobOffering(0).getDescription());
		assertEquals(sqlDeadLine.toString(), department.getInstructor(0).getJobOffering(0).getDeadline().toString());
		assertEquals(sqlStartDate.toString(), department.getInstructor(0).getJobOffering(0).getStartDate().toString());
		assertEquals(sqlEndDate.toString(), department.getInstructor(0).getJobOffering(0).getEndDate().toString());
	
		//application
		assertEquals("bob@gmail.com", department.getStudent(0).getJobApplication(0).getEmailAddress());
		assertEquals("zero", department.getStudent(0).getJobApplication(0).getPastExperience());
		assertEquals(1, department.getStudent(0).getJobApplication(0).getPreference());
		
	}

}
