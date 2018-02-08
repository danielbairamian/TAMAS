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
import ca.mcgill.ecse321.tamas.model.Evaluation;
import ca.mcgill.ecse321.tamas.model.Instructor;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.JobPosting;
import ca.mcgill.ecse321.tamas.model.Registration;
import ca.mcgill.ecse321.tamas.model.Student;
import ca.mcgill.ecse321.tamas.persistence.PersistenceXStream;

public class StudentDepartmentControllerTest {

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

	@Test
	public void testApplyForAJobSuccess() throws ParseException {

		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse321", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preference = 2;

		try{
			dp.applyForJob(job,email,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
	}

	@Test
	public void testApplyForAJobEmailNull() throws ParseException {
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(12, "ecse325", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("bob", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preference = 2;

		try{
			dp.applyForJob(job,null,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("Please provide your email.", error);

	}

	@Test
	public void testApplyForAJobEmailEmpty() throws ParseException {
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);


		//create the error string
		String error = null;

		//create a course
		Course course = new Course(12, "ecse324", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("bob", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		//creating an email
		String email = "";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preference = 2;

		try{
			dp.applyForJob(job,email,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("Please provide your email.", error);

	}

	@Test
	public void testApplyForAJobStudentNull() throws ParseException{

		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse223", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current instructor

		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preference = 2;

		try{
			dp.applyForJob(job,email,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("The student is null.",error);
	}

	@Test
	public void testApplyForAJobExperienceNull() throws ParseException {
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse328", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = null;

		//creating a preference
		int preference = 2;

		try{
			dp.applyForJob(job,email,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("Please provide your experience.", error);
	}

	@Test
	public void testApplyForAJobExperienceEmpty() throws ParseException {
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse329", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "";

		//creating a preference
		int preference = 2;

		try{
			dp.applyForJob(job,email,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("Please provide your experience.", error);
	}

	@Test
	public void testApplyForAJobDuplicateApplication() throws ParseException {
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);


		//create the error string
		String error = null;

		//create a course
		Course course = new Course(12, "ecse3123", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("bob", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preference = 2;

		//set the application to the student
		Application application = new Application(email, experience, preference, job, student);

		try{
			dp.applyForJob(job,email,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("You already applied for this job.", error);
	}

	@Test
	public void testApplyForAJobDuplicatePreference() throws ParseException {
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse34234", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);
		JobPosting job2 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preference = 2;

		//set the application to the student
		Application application = new Application(email, experience, preference, job2, student);

		try{
			dp.applyForJob(job,email,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}

		assertEquals("You have already specified this preference for another job.", error);
	}


	@Test
	public void testApplyForAJobPassedDeadline() throws ParseException {
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse399921", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);

		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preference = 2;

		try{
			dp.applyForJob(job,email,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		assertEquals("The deadline has been passed.", error);
	}
	
	
	@Test
	public void testApplyForAJobAlreadyApplied() throws ParseException {
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse399921", 2, 3, department);

		//create an instructor
		Instructor instructor = new Instructor("bob", course, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor);
		
		//creating a job
		Job job1 = new Job(5 , 5 , sqlStartDate , sqlEndDate , job);
		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preference = 2;
		
		//creating a registration
		Registration reg = new Registration (sqlEndDate , student , job1);

		try{
			dp.applyForJob(job,email,experience,preference);	 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		assertEquals("You can't reapply to this job", error);
	}

	@Test
	public void testCancelApplicationSuccess() throws ParseException{
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse399921", 2, 3, department);
		Course course1 = new Course(10, "ecse3999", 2, 3, department);
		
		//create an instructor
		Instructor instructor1 = new Instructor("bob", course, department);
		Instructor instructor2 = new Instructor("alice", course1, department);
		
		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor1);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor1);
		JobPosting job2 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hey", instructor2);
		
		//create an application for this student
		Application application1 = new Application("heygmail.com","TA", 1, job1, student);
		Application application2 = new Application("heygmail.com","TA", 1, job2, student);
		
		try {
			dp.cancelApplication(application1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals(1, student.numberOfJobApplications());
		
	}
	
	
	@Test
	public void testCancelApplicationNullApllication() throws ParseException{
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse39991", 2, 3, department);
		Course course1 = new Course(10, "ecse399", 2, 3, department);
		
		//create an instructor
		Instructor instructor1 = new Instructor("bob", course, department);
		Instructor instructor2 = new Instructor("alice", course1, department);
		
		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor1);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor1);
		JobPosting job2 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hey", instructor2);
		
		//create an application for this student
		Application application1 = new Application("heygmail.com","TA", 1, job1, student);
		Application application2 = new Application("heygmail.com","TA", 2, job2, student);
		
		try {
			dp.cancelApplication(null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Please select an application in order to cancel it.", error);
		
	}
	
	
	
	@Test
	public void testCancelApplicationNotStudentApplication() throws ParseException{
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create a course
		Course course = new Course(10, "ecse39991", 2, 3, department);
		Course course1 = new Course(10, "ecse399", 2, 3, department);
		
		//create an instructor
		Instructor instructor1 = new Instructor("bob", course, department);
		Instructor instructor2 = new Instructor("alice", course1, department);
		
		//create a student
		Student student = new Student("alice", department);
		Student student1 = new Student("bob", department);
		
		//setting the current student and instructor
		department.setCurrentStudent(student);
		department.setCurrentInstructor(instructor1);

		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting job1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructor1);
		JobPosting job2 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hey", instructor2);
		
		//create an application for this student
		Application application1 = new Application("heygmail.com","TA", 1, job1, student);
		Application application2 = new Application("heygmail.com","TA", 2, job2, student1);
		
		try {
			dp.cancelApplication(application2);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("This is application is not for you.", error);
		
	}
	
	
	@Test
	public void testViewOwnApplicationSuccess() throws ParseException {
		
		 		List<Application> applications = new ArrayList<Application>() ;
				//create the department controller
				StudentDepartmentController dp = new StudentDepartmentController(department);

				//create the error string
				String error = null;

				//create two courses
				Course courseOne = new Course(10, "ecse3216868", 2, 3, department);
				
				Course courseTwo = new Course(10, "ecse3216867", 2, 3, department);

				//create two instructors
				Instructor instructorOne = new Instructor("bob", courseOne, department);
				
				Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

				//create a student
				Student student = new Student("alice", department);

				//setting the current student and instructor
				department.setCurrentStudent(student);
			
				
				//creating an email
				String email = "sexyboy@gmail";

				//creating an experience
				String experience = "zero";

				//creating a preference
				int preferenceOne = 1;
				int preferenceTwo = 2;
				
				//creating two job postings
				java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
				java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
				java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

				java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
				java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
				java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

				//creating a new job posting
				JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
				
				//creating a new job posting
				JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
				
				//making the student apply for the two postings
				
				Application firstApp = new Application (email, experience, preferenceOne, jobOne, student);
				Application secondtApp = new Application (email, experience, preferenceTwo, jobTwo, student);
				
				applications =  dp.viewOwnApplications()	; 
				
				
				assertEquals(2, applications.size() );
		
	}

	@Test
	public void testViewOwnEvaluationsSuccess() throws ParseException{
		List<Evaluation> evaluations = new ArrayList<Evaluation>() ;
		//create the department controller
		StudentDepartmentController dc = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create two courses
		Course courseOne = new Course(10, "ecse3216868", 2, 3, department);
		
		Course courseTwo = new Course(10, "ecse3216867", 2, 3, department);

		//create two instructors
		Instructor instructorOne = new Instructor("bob", courseOne, department);
		
		Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		
		//creating two job postings
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
		
		//creating a new job posting
		JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
		
		//create an evaluation
		Evaluation evaluation1 = new Evaluation("you are brilliant", courseOne.getId(), courseOne.getInstructor().getName(), student);
		Evaluation evaluation2 = new Evaluation("you are brilliant", courseOne.getId(), courseOne.getInstructor().getName(), student);
		
		try {
			evaluations = dc.viewOwnEvaluations();
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertEquals(2, evaluations.size());
	}
	
	@Test
	public void testViewOwnEvaluationsNoEvaluations() throws ParseException{
		List<Evaluation> evaluations = new ArrayList<Evaluation>() ;
		//create the department controller
		StudentDepartmentController dc = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create two courses
		Course courseOne = new Course(10, "ecse3216868", 2, 3, department);
		
		Course courseTwo = new Course(10, "ecse3216867", 2, 3, department);

		//create two instructors
		Instructor instructorOne = new Instructor("bob", courseOne, department);
		
		Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
		
		//creating two job postings
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
		
		//creating a new job posting
		JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
		
		try {
			evaluations = dc.viewOwnEvaluations();
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertEquals(null, evaluations);
		
	}
	
	
	@Test
	public void testViewOwnEmploymentSuccess() throws ParseException{
		
		List<Job> jobList = null;
		
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create two courses
		Course courseOne = new Course(10, "ecse32168681", 2, 3, department);
		
		Course courseTwo = new Course(10, "ecse32168672", 2, 3, department);

		//create two instructors
		Instructor instructorOne = new Instructor("bob", courseOne, department);
		
		Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
	
		
		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preferenceOne = 1;
		int preferenceTwo = 2;
		
		//creating two job postings
		
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
		
		//creating a new job posting
		JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
		
		//making the student apply for the two postings
		
		Application firstApp = new Application (email, experience, preferenceOne, jobOne, student);
		Application secondtApp = new Application (email, experience, preferenceTwo, jobTwo, student);
		
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
		
		
		//creating two jobs
		
		 Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobOne);
		
		 Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobTwo);
		
		 job1.setJobState(Job.JobState.Filled);
		 job2.setJobState(Job.JobState.Filled);
			
		
		//creating two registrations
		Registration reg1 = new Registration(sqlEndDate, student, job1);
		
		//creating two registrations
		Registration reg2 = new Registration(sqlEndDate, student, job2);
				
		
				
		
		
			jobList =  dp.viewOwnEmployment()	; 
		
		
		
		assertEquals(2, jobList.size() );
		
		
	}
	
	
	
	
	
	
	@Test
	public void testAcceptJobOfferSuccess() throws ParseException{
		
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create two courses
		Course courseOne = new Course(10, "ecse32168688898", 2, 3, department);
		
		Course courseTwo = new Course(10, "ecse321681231267", 2, 3, department);

		//create two instructors
		Instructor instructorOne = new Instructor("bob", courseOne, department);
		
		Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
	
		
		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preferenceOne = 1;
		int preferenceTwo = 2;
		
		//creating two job postings
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
		
		//creating a new job posting
		JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
		
		//making the student apply for the two postings
		
		Application firstApp = new Application (email, experience, preferenceOne, jobOne, student);
		Application secondtApp = new Application (email, experience, preferenceTwo, jobTwo, student);
		
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
				
				
		//creating two jobs
				
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobOne);
		Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobTwo);
		
		//create Registrations
		
				Registration reg = new Registration (sqlEndDate , student , job1);
				Registration regTwo = new Registration (sqlEndDate , student , job2);
					
		job1.setJobState(Job.JobState.DepartmentApproved);
		
		
		try{
		  dp.acceptJobOffer(job1); 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		
		assertEquals("Filled",job1.getJobStateFullName());
		assertEquals("Open",job2.getJobStateFullName());

		
		
		
	}
	
	
	@Test
	public void testAcceptJobOfferNotStudentsJob() throws ParseException{
		
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create two courses
		Course courseOne = new Course(10, "ecse321686188898", 2, 3, department);
		
		Course courseTwo = new Course(10, "ecse3216821231267", 2, 3, department);

		//create two instructors
		Instructor instructorOne = new Instructor("bob", courseOne, department);
		
		Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

		//create a student
		Student student = new Student("alice", department);
		
		//create a student
		Student studentTwo = new Student("aliceTwo", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
	
		
		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preferenceOne = 1;
		int preferenceTwo = 2;
		
		//creating two job postings
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
		
		//creating a new job posting
		JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
		
		//making the student apply for the two postings
		
		Application firstApp = new Application (email, experience, preferenceOne, jobOne, studentTwo);
		Application secondtApp = new Application (email, experience, preferenceTwo, jobTwo, student);
		
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
				
				
		//creating two jobs
				
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobOne);
		Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobTwo);
		
		//create Registrations
		
				Registration reg = new Registration (sqlEndDate , studentTwo , job1);
				Registration regTwo = new Registration (sqlEndDate , student , job2);
					
		job1.setJobState(Job.JobState.DepartmentApproved);
		
		
		try{
		  dp.acceptJobOffer(job1); 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		
		assertEquals("The selected job is not for the current student.",error);
		
		
		
		
	}
	
	
	
	@Test
	public void testAcceptJobOfferJobIsNotOffered() throws ParseException{
		
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create two courses
		Course courseOne = new Course(10, "ecse321686838898", 2, 3, department);
		
		Course courseTwo = new Course(10, "ecse3216812531267", 2, 3, department);

		//create two instructors
		Instructor instructorOne = new Instructor("bob", courseOne, department);
		
		Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
	
		
		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preferenceOne = 1;
		int preferenceTwo = 2;
		
		//creating two job postings
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
		
		//creating a new job posting
		JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
		
		//making the student apply for the two postings
		
		Application firstApp = new Application (email, experience, preferenceOne, jobOne, student);
		Application secondtApp = new Application (email, experience, preferenceTwo, jobTwo, student);
		
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
				
				
		
		//creating two jobs
		
				Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobOne);
				Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobTwo);
				
				Registration reg = new Registration (sqlEndDate , student , job1);
				Registration regTwo = new Registration (sqlEndDate , student , job2);
				
				job1.setJobState(Job.JobState.Open);
				
		
		try{
		  dp.acceptJobOffer(job1); 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		
		assertEquals("You Can't accept a job that is not offered to you.",error);
		
		
		
		
	}
	
	
	@Test
	public void testDeclineJobOfferSuccess() throws ParseException{
		
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create two courses
		Course courseOne = new Course(10, "ec1se32168688898", 2, 3, department);
		
		Course courseTwo = new Course(10, "ec2se321681231267", 2, 3, department);

		//create two instructors
		Instructor instructorOne = new Instructor("bob", courseOne, department);
		
		Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
	
		
		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preferenceOne = 1;
		int preferenceTwo = 2;
		
		//creating two job postings
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
		
		//creating a new job posting
		JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
		
		//making the student apply for the two postings
		
		Application firstApp = new Application (email, experience, preferenceOne, jobOne, student);
		Application secondtApp = new Application (email, experience, preferenceTwo, jobTwo, student);
		
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
				
				
		//creating two jobs
				
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobOne);
		Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobTwo);
		
		//create Registrations
		
		Registration reg = new Registration (sqlEndDate , student , job1);
		Registration regTwo = new Registration (sqlEndDate , student , job2);
		
					
		job1.setJobState(Job.JobState.DepartmentApproved);
		
		
		try{
		  dp.declineJobOffer(job1); 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
	assertEquals(1 , student.getRegistrations().size());

		
		
		
	}
	
	
	@Test
	public void testDeclineJobOfferNotStudentsJob() throws ParseException{
		
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create two courses
		Course courseOne = new Course(10, "ecse3216861880898", 2, 3, department);
		
		Course courseTwo = new Course(10, "ecse3216821235431267", 2, 3, department);

		//create two instructors
		Instructor instructorOne = new Instructor("bob", courseOne, department);
		
		Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

		//create a student
		Student student = new Student("alice", department);
		
		//create a student
		Student studentTwo = new Student("aliceTwo", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
	
		
		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preferenceOne = 1;
		int preferenceTwo = 2;
		
		//creating two job postings
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
		
		//creating a new job posting
		JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
		
		//making the student apply for the two postings
		
		Application firstApp = new Application (email, experience, preferenceOne, jobOne, studentTwo);
		Application secondtApp = new Application (email, experience, preferenceTwo, jobTwo, student);
		
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
				
				
		//creating two jobs
				
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobOne);
		Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobTwo);
					
		//create Registrations
		
		Registration reg = new Registration (sqlEndDate , studentTwo , job1);
		Registration regTwo = new Registration (sqlEndDate , student , job2);
		
		job1.setJobState(Job.JobState.Open);
		
		
		try{
		  dp.declineJobOffer(job1); 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		
		assertEquals("The selected job is not for the current student.",error);
		
		
		
		
	}
	
	
	
	@Test
	public void testDeclineJobOfferJobIsNotOffered() throws ParseException{
		
		//create the department controller
		StudentDepartmentController dp = new StudentDepartmentController(department);

		//create the error string
		String error = null;

		//create two courses
		Course courseOne = new Course(10, "ecse3421686838898", 2, 3, department);
		
		Course courseTwo = new Course(10, "ecse23216812531267", 2, 3, department);

		//create two instructors
		Instructor instructorOne = new Instructor("bob", courseOne, department);
		
		Instructor instructorTwo = new Instructor("bobby", courseTwo, department);

		//create a student
		Student student = new Student("alice", department);

		//setting the current student and instructor
		department.setCurrentStudent(student);
	
		
		//creating an email
		String email = "sexyboy@gmail";

		//creating an experience
		String experience = "zero";

		//creating a preference
		int preferenceOne = 1;
		int preferenceTwo = 2;
		
		//creating two job postings
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		//creating a new job posting
		JobPosting jobOne = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorOne);
		
		//creating a new job posting
		JobPosting jobTwo = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, "hi", instructorTwo);
		
		//making the student apply for the two postings
		
		Application firstApp = new Application (email, experience, preferenceOne, jobOne, student);
		Application secondtApp = new Application (email, experience, preferenceTwo, jobTwo, student);
		
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
				
				
		//creating two jobs
				
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobOne);
		Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobTwo);
		
		Registration reg = new Registration (sqlEndDate , student , job1);
		Registration regTwo = new Registration (sqlEndDate , student , job2);
		
		job1.setJobState(Job.JobState.Open);
		
	
		
		
		try{
		  dp.declineJobOffer(job1); 
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		
		assertEquals("You Can't decline a job that is not offered to you.",error);
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
