package ca.mcgill.ecse321.tamas.controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.tamas.model.Application;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Instructor;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.JobPosting;
import ca.mcgill.ecse321.tamas.model.Registration;
import ca.mcgill.ecse321.tamas.model.Student;
import ca.mcgill.ecse321.tamas.persistence.PersistenceXStream;

public class DepartmentControllerTest {

	private Department department;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		department = new Department();
	}

	@After
	public void tearDown() throws Exception {
		department.delete();
	}

	//	@Test
	//	public void testGetList(){
	//		DepartmentController dp = new DepartmentController(department);
	//		List<JobPosting> posting = new ArrayList<JobPosting>();
	//		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
	//		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
	//		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");
	//
	//		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
	//		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	//		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
	//		JobPosting posting = new JobPosting(aDeadline, aStartDate, aEndDate, aDescription, aInstructor)
	//		
	//	}

	@Test
	public void testCreateAJobSuccess(){
		//create the department controller
		DepartmentController dp = new DepartmentController(department);

		//initializing the error message
		String error = null;

		//initializing the data for creating a course and instructor
		String instructor = "jeremy";
		float budget = 10;
		String ID = "ecse321";
		int numLabs = 2;
		int numTutorials = 3;

		try{
			dp.addACourse(instructor,budget, ID, numLabs, numTutorials);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
	}


	@Test
	public void testCreateAJobCourseDuplicate(){
		//create the department controller
		DepartmentController dp = new DepartmentController(department);

		//initializing the error message
		String error = null;

		//initializing the data for creating a course
		String instructor = "josephpo";
		float budget = 10;
		String ID = "ecse321";
		int numLabs = 2;
		int numTutorials = 3;

		Course course = new Course(budget, ID, numLabs, numTutorials, department);

		try{
			dp.addACourse(instructor, budget, ID, numLabs, numTutorials);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("The ID provided for the course already exists. Please enter a new one.", error);
	}

	@Test
	public void testCreateAJobIDNull(){
		//create the department controller
		DepartmentController dp = new DepartmentController(department);

		//initializing the error message
		String error = null;

		//initializing the data for creating a course
		String instructor = "danielB";
		float budget = 10;
		String ID = "ecse321";
		int numLabs = 2;
		int numTutorials = 3;

		try{
			dp.addACourse(instructor,budget, null, numLabs, numTutorials);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("Please provide an ID for the course.", error);
	}

	@Test
	public void testCreateAJobNoBudget(){
		//create the department controller
		DepartmentController dp = new DepartmentController(department);

		//initializing the error message
		String error = null;

		//initializing the data for creating a course
		String instructor = "ElieJ";
		float budget = 0;
		String ID = "ecse321";
		int numLabs = 2;
		int numTutorials = 3;

		try{
			dp.addACourse(instructor,budget, ID, numLabs, numTutorials);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("Please provide a budget for the course.", error);
	}

	@Test
	public void testNullInstructor(){
		//create department controller
		DepartmentController dp = new DepartmentController(department);

		//initialize error
		String error = null;

		//initialize the inputs for creating course
		String instructor = null;
		float budget = 10000;
		String ID = "ECSE678";
		int numLabs = 2;
		int numTutorials = 2;

		try{
			dp.addACourse(instructor, budget, ID, numLabs, numTutorials);
		}catch(InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("Please provide an instructor name", error);
	}



	@Test
	public void testPostJobSuccess() throws ParseException{
		DepartmentController cont = new DepartmentController(department);

		String error = null;

		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "hello mello";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 , 0 , 0 , 0 , "TA");
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 , 0 , 0 , 0  , "Grader");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}


	}
	
	

	@Test
	public void testNoInstructorAddJobPosting() throws ParseException{

		DepartmentController cont = new DepartmentController(department);

		String error = null;

		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "hello mello";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, -1 , 0 , 0 , 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("No Instructor chosen!", error);

	}


	@Test
	public void testNullDeadlineAddJobPosting() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);
		String error = null;
		java.util.Date deadLine = null;
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = null;
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "hello mello";
		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 ,0 , 0, 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("No deadline date chosen!", error);
	}
	@Test
	public void testNullStartAddJobPosting() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);
		String error = null;
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = null;
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = null;
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "hello mello";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 , 0 , 0, 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("No start date chosen!", error);
	}
	@Test
	public void testNullEndAddJobPosting() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);
		String error = null;
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = null;

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = null;

		String jobDesc = "hello mello";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 ,0 , 0, 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("No end date chosen!", error);
	}
	@Test
	public void testStartAfterEndAddJobPosting() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-06");
		String error = null;
		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "hello mello";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 , 0 , 0, 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Start date cannot be after end date!", error);
	}
	@Test
	public void testStartEqualsEndAddJobPosting() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);
		String error = null;
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "hello mello";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 , 0 , 0, 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Start date cannot be the same as end date!", error);
	}
	@Test
	public void testDeadlineAfterStartOrEndAddJobPosting() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);
		String error = null;
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-07");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "hello mello";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 , 0 , 0, 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Deadline cannot be after start date or end date!", error);
	}


	@Test
	public void testEmptyDescriptionAddJobPosting() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");
		String error = null;
		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 , 0 , 0 , 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Description cannot be empty!", error);

	}


	@Test
	public void testInvalidHours() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");
		String error = null;
		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "this is a description";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc,  0 , -1 ,0 , 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid input for hours/wage", error);

	}
	@Test
	public void testInvalidGradWage() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");
		String error = null;
		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "this is a description";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc,  0 , 0 ,-1 , 0 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid undergraduate wage", error);

	}
	
	@Test
	public void testInvalidUGradWage() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");
		String error = null;
		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "this is a description";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc,  0 , 0 ,0 , -1 , "TA");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid graduate wage", error);

	}
	

	@Test
	public void testInvalidPosition() throws ParseException{
		DepartmentController cont = new DepartmentController(department);


		Course course = new Course(10,"ecse321",2,3,department);
		Instructor inst = new Instructor("bob", course, department);
		department.setCurrentInstructor(inst);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");
		String error = null;
		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		String jobDesc = "hello";


		try {
			cont.addJobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, jobDesc, 0 , 0 , 0 ,0 , "danielVarro");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid job type (TA, Grader)", error);

	}




	@Test
	public void initialAllocationSuccess() throws ParseException{
		DepartmentController cont = new DepartmentController(department);
		Student student = new Student("alex", department);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);

		String error = null;

		try {
			cont.addInitialAllocation(student, job1, sqlEndDate);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals(Job.JobState.Offered, job1.getJobState());
	}



	@Test
	public void initialAllocationStudentNull() throws ParseException{
		DepartmentController cont = new DepartmentController(department);
		Student student = new Student("alex", department);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);

		String error = null;

		try {
			cont.addInitialAllocation(null, job1, sqlEndDate);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Please choose a student in order to register him.");

	}

	@Test
	public void initalAllocationJobNull() throws ParseException{
		DepartmentController cont = new DepartmentController(department);
		Student student = new Student("alex", department);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);

		String error = null;

		try {
			cont.addInitialAllocation(student, null, sqlEndDate);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Please choose a job in order to register him.");
	}

	@Test
	public void initialAllocationDateNull() throws ParseException{
		DepartmentController cont = new DepartmentController(department);
		Student student = new Student("alex", department);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);

		String error = null;

		try {
			cont.addInitialAllocation(student, job1, null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Please choose a date in order to register the student.");
	}

	@Test
	public void inialAllocationDateError() throws ParseException{
		DepartmentController cont = new DepartmentController(department);
		Student student = new Student("alex", department);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);

		String error = null;

		try {
			cont.addInitialAllocation(student, job1, sqlStartDate);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Please specify a date after than the current date.");
	}

	@Test
	public void initialAllocationStudentNotRegistered() throws ParseException{
		Department department1 = new Department();
		DepartmentController cont = new DepartmentController(department);
		Student student = new Student("alex", department1);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);

		String error = null;

		try {
			cont.addInitialAllocation(student, job1, sqlEndDate);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals(error, "The student is not registered.");
	}



	@Test
	public void approveAllAllocationsSuccess() throws ParseException{
		DepartmentController cont = new DepartmentController(department);
		Student student = new Student("alex", department);
		Student student1 = new Student("bob", department);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);
		JobPosting jobPosting1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);
		Job job2 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);
		String error = null;

		Registration registration = new Registration(sqlEndDate, student, job1);
		Registration registration1 = new Registration(sqlEndDate, student1, job2);

		try {
			cont.approveAllAllocations();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals(job1.getJobState(), Job.JobState.DepartmentApproved);
		assertEquals(job2.getJobState(), Job.JobState.DepartmentApproved);
	}


	@Test

	public void approveFinalAllocationSuccess() throws ParseException {


		DepartmentController cont = new DepartmentController(department);
		String error = null;
		Student student = new Student("alex", department);
		Student student1 = new Student("bob", department);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);
		JobPosting jobPosting1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);
		Job job2 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);


		Registration registration = new Registration(sqlEndDate, student, job1);
		Registration registration1 = new Registration(sqlEndDate, student1, job2);

		try {
			cont.approveFinalAllocation(registration1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals(Job.JobState.DepartmentApproved , job2.getJobState());



	}

	@Test

	public void approveFinalAllocationNoReg() throws ParseException {


		DepartmentController cont = new DepartmentController(department);
		String error = null;
		Student student = new Student("alex", department);
		Student student1 = new Student("bob", department);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);
		JobPosting jobPosting1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);
		Job job2 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);


		Registration registration = new Registration(sqlEndDate, student, job1);
		Registration registration1 = null;

		try {
			cont.approveFinalAllocation(registration1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Please choose a registration.", error);



	}


	@Test
	public void getJobPostingTestSuccess() throws ParseException{

		DepartmentController cont = new DepartmentController(department);
		String error = null;
		Student student = new Student("alex", department);
		Student student1 = new Student("bob", department);
		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		Course coursetwo = new Course(10,"ecse3215",2,3,department);
		Instructor instructortwo = new Instructor("bob45", coursetwo, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);
		JobPosting jobPosting1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hiTwo", instructor);

		Job job1 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);
		Job job2 = new Job(10, 10, sqlStartDate, sqlEndDate, jobPosting);


		//creating a new job posting
		JobPosting jobPostingTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hiThree", instructortwo);
		JobPosting jobPostingTwo1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hiFour", instructortwo);

		Job job1two = new Job(10, 10, sqlStartDate, sqlEndDate, jobPostingTwo);
		Job job2two = new Job(10, 10, sqlStartDate, sqlEndDate, jobPostingTwo);

		List<JobPosting> listJob = new ArrayList<JobPosting>();

		listJob = cont.getJobPostings();

		assertEquals("hiFour" , listJob.get(3).getDescription()  );



	}


	@Test
	public void hireStudentInitialDepTestSuccess() throws ParseException{

		DepartmentController cont = new DepartmentController(department);
		String error = null;
		Student student = new Student("alex", department);
		student.setStudentLevel(Student.StudentLevel.Undergraduate);
		
		Student studentTwo = new Student("bob", department);
		studentTwo.setStudentLevel(Student.StudentLevel.Graduate);

		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);
		
		

		Course coursetwo = new Course(10,"ecse3215",2,3,department);
		Instructor instructortwo = new Instructor("bob45", coursetwo, department);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "this is a template job", instructor);
		JobPosting jobPosting1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hiTwo", instructor);

		//create student application
		Application appOne = new Application ("sometihng" , "somethingelse" , 1, jobPosting , student);

		//create student application
		Application appTwo = new Application ("something" , "somethingelse" , 2, jobPosting , studentTwo);


		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());



		//creating two jobs
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);
		
		//creating two jobs
		
		Job job2 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);
		

		cont.hireStudentInitialDepartment(appOne);
		cont.hireStudentInitialDepartment(appTwo);

		assertEquals( Job.JobState.Offered , student.getRegistration(0).getJob().getJobState() );
		assertEquals ("this is a template job" , student.getRegistration(0).getJob().getJobPosting().getDescription());

	}


	@Test 
	public void testAllGetters(){

		DepartmentController cont = new DepartmentController(department);
		String error = null;
		Student student = new Student("alex", department);
		Student student1 = new Student("bob", department);
		Student student2 = new Student("jimmy" , department);

		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);

		Course coursetwo = new Course(10,"ecse3215",2,3,department);
		Instructor instructortwo = new Instructor("bob45", coursetwo, department);

		Course coursethree = new Course(10,"ecse32431",2,3,department);
		Instructor instructorthree = new Instructor("bobLOL", coursethree, department);




		List<Course> listCourse = cont.getCourses();  //
		List<Course> listCourseTwo = cont.getAllCourses(); //
		Course testC = cont.getCourseAt(1);//
		List<Instructor> listInstructor = cont.getAllInstructors(); //
		List<Student> listStudent = cont.getAllStudents();//
		Student testS = cont.getStudentAt(0);//
		Instructor testI = cont.getInstructorAt(1);//


		department.setCurrentInstructor(instructorthree);
		department.setCurrentStudent(student1);


		assertEquals(  3 , listCourse.size());
		assertEquals(  3 , listCourseTwo.size());
		assertEquals(  3 , listInstructor.size());
		assertEquals(  3 , listStudent.size());

		assertEquals(  "ecse3215" , testC.getId());
		assertEquals(  "alex" , testS.getName());
		assertEquals(  "ecse3215" , testI.getCourse().getId());

		assertEquals( "bob" , cont.getCurrentStudent().getName());
		assertEquals( "bobLOL" ,cont.getCurrentInstructor().getName());










	}

	@Test
	public void testCreateStudentSuccess(){

		String error = null ;
		boolean grad = true;
		boolean ugrad = false;
		String name = "daniel";
		String nameTwo = "joe";
		DepartmentController cont = new DepartmentController(department);

		try {
			cont.createStudent(name , grad);
			cont.createStudent(nameTwo , ugrad);
		} catch (InvalidInputException e) {
			error =  e.getMessage();
		}	

		assertEquals("daniel" , department.getStudent(0).getName());
		assertEquals("joe" , department.getStudent(1).getName());

	}

	@Test
	public void testCreateStudentNull(){

		String error = null ;
		String name = null ;

		DepartmentController cont = new DepartmentController(department);

		try {
			cont.createStudent(name , false);
		} catch (InvalidInputException e) {
			error =  e.getMessage();
		}

		assertEquals("No name inputted" , error);



	}

	@Test
	public void testCreateStudentEmpty(){

		String error = null ;
		String name = "" ;

		DepartmentController cont = new DepartmentController(department);

		try {
			cont.createStudent(name , false);
		} catch (InvalidInputException e) {
			error =  e.getMessage();
		}

		assertEquals("No name inputted" , error);



	}

	@Test
	public void testGetApprovedStudentSuccess() throws ParseException{

		DepartmentController cont = new DepartmentController(department);
		String error = null;
		Student student = new Student("alex", department);
		Student student1 = new Student("bob", department);
		Student student2 = new Student("jimmy" , department);

		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);


		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "this is a template job", instructor);
		JobPosting jobPosting1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hiTwo", instructor);


		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());



		//creating two jobs
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);


		//creating two jobs
		Job job2 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);


		//creating two jobs
		Job job3 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);

		//creating registrations
		Registration reg1 = new Registration (sqlEndDate , student , job1);

		//creating registrations
		Registration reg2 = new Registration (sqlEndDate , student1 , job2);

		//creating registrations
		Registration reg3 = new Registration (sqlEndDate , student2 , job3);

		job1.setJobState(Job.JobState.InstructorApproved);
		job2.setJobState(Job.JobState.Offered);
		job3.setJobState(Job.JobState.InstructorApproved);

		//creating a list of Students
		List<Student> listStudent = new ArrayList<Student>();


		try {
			listStudent = cont.getApprovedStudents(course);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals(2 , listStudent.size());
		assertEquals("alex" , listStudent.get(0).getName());
		assertEquals("jimmy" , listStudent.get(1).getName());






	}

	@Test
	public void testGetApprovedStudentNullCourse() throws ParseException{

		DepartmentController cont = new DepartmentController(department);
		String error = null;
		Student student = new Student("alex", department);
		Student student1 = new Student("bob", department);
		Student student2 = new Student("jimmy" , department);

		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);


		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "this is a template job", instructor);
		JobPosting jobPosting1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hiTwo", instructor);


		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());



		//creating two jobs
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);


		//creating two jobs


		Job job2 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);


		//creating two jobs
		Job job3 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);

		//creating registrations
		Registration reg1 = new Registration (sqlEndDate , student , job1);

		//creating registrations
		Registration reg2 = new Registration (sqlEndDate , student1 , job2);

		//creating registrations
		Registration reg3 = new Registration (sqlEndDate , student2 , job3);

		job1.setJobState(Job.JobState.InstructorApproved);
		job2.setJobState(Job.JobState.Offered);
		job3.setJobState(Job.JobState.InstructorApproved);

		//creating a list of Students
		List<Student> listStudent = new ArrayList<Student>();


		try {
			listStudent = cont.getApprovedStudents(null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Course cannot be null" , error);



	}

	@Test
	public void testRejectAllocationSuccess() throws ParseException{
		
		DepartmentController cont = new DepartmentController(department);
		String error = null;
		Student student = new Student("alex", department);
		Student student1 = new Student("bob", department);
		Student student2 = new Student("jimmy" , department);

		Course course = new Course(10,"ecse321",2,3,department);
		Instructor instructor = new Instructor("bob", course, department);


		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobPosting = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "this is a template job", instructor);
		JobPosting jobPosting1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hiTwo", instructor);


		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());



		//creating two jobs
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);


		//creating two jobs


		Job job2 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);


		//creating two jobs
		Job job3 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPosting);

		//creating registrations
		Registration reg1 = new Registration (sqlEndDate , student , job1);

		//creating registrations
		Registration reg2 = new Registration (sqlEndDate , student1 , job2);
		
		cont.rejectAllocation(reg1);
		
		assertEquals(null, reg1.getJob());
		
		
		
		
	}

}
