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

public class InstructorDepartmentControllerTest {

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
	public void writeEvaluatuationSuccess() {
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create an error string
		String error = null;
		
		String desc = "You did a good job";
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		Student student = new Student("zabrey" , department);
		
		try {
			idc.writeEvaluatuation(desc,  instructor , student);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("You did a good job", student.getEvaluation(0).getText() );	
		
	}
	
	@Test
	public void writeEvaluatuationDescriptionNull() {
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create an error string
		String error = null;
		
		String desc = null;
		Course course = new Course (13, "ecse4544" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron1" , course , department );
		Student student = new Student("zabrey" , department);
		
		try {
			idc.writeEvaluatuation(desc,  instructor , student);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Evaluation cannot be empty", error );	
		
	}
	
	@Test
	public void writeEvaluatuationDescriptionEmpty() {
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create an error string
		String error = "";
		
		String desc = null;
		Course course = new Course (13, "ecse4544" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron1" , course , department );
		Student student = new Student("zabrey" , department);
		
		try {
			idc.writeEvaluatuation(desc,  instructor , student);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Evaluation cannot be empty", error );	
		
	}
	
	@Test
	public void writeEvaluatuationStudentNull() {
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create an error string
		String error = null;
		
		String desc = "You did a good job";
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		Student student = new Student("zabrey" , department);
		
		try {
			idc.writeEvaluatuation(desc,  instructor , null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("No student was selected", error );	
		
	}
	
	@Test
	public void writeEvaluatuationInstructorNull() {
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create an error string
		String error = null;
		
		String desc = "You did a good job";
		Student student = new Student("zabrey" , department);
		
		try {
			idc.writeEvaluatuation(desc,  null , student);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("No instructor was selected", error );	
		
	}
	
	
	@Test
	public void viewEvaluationSuccess() {
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create an error string
		String error = null;
		
		String desc = "You did a good job zabrey";
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		Student student = new Student("zabrey" , department);
		
		Evaluation newEval = new Evaluation(desc, instructor.getCourse().getId(), instructor.getName(), student);

		Evaluation compareEval = null;
		
		try {
			compareEval = idc.viewEvaluation(0, 0);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("You did a good job zabrey", student.getEvaluation(0).getText() );	
		
	}
	
	
	@Test
	public void viewEvaluationNoStudent() {
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create an error string
		String error = null;
		
		String desc = "You did a good job zabrey";
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		Student student = new Student("zabrey" , department);
		
		Evaluation newEval = new Evaluation(desc, instructor.getCourse().getId(), instructor.getName(), student);

		Evaluation compareEval = null;
		
		try {
			compareEval = idc.viewEvaluation(0, -1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("No student selected", error );	
		
	}
	
	@Test
	public void viewEvaluationNoInstructor() {
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create an error string
		String error = null;
		
		String desc = "You did a good job zabrey";
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		Student student = new Student("zabrey" , department);
		
		Evaluation newEval = new Evaluation(desc, instructor.getCourse().getId(), instructor.getName(), student);

		Evaluation compareEval = null;
		
		try {
			compareEval = idc.viewEvaluation(-1, 0);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("No instructor selected", error );	
		
	}
	
	@Test
	public void viewEvaluationNoEvals() {
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create an error string
		String error = null;
		
		String desc = "You did a good job zabrey";
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		Student student = new Student("zabrey" , department);
		
		//Evaluation newEval = new Evaluation(desc, instructor.getCourse().getId(), instructor.getName(), student);

		Evaluation compareEval = null;
		
		try {
			compareEval = idc.viewEvaluation(0, 0);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("This student has no evaluation", error);	
		
	}
	
	@Test
	public void getInstructorPostingTest() throws ParseException{
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
		
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
		
		//empty joblist
		List<JobPosting> jobList = new ArrayList<JobPosting>();
		
		try {
			jobList = idc.getInstructorPosting(0);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals( 2 ,  jobList.size() );
					
		
		
	}
	
	@Test
	public void getInstructorPostingInstructorNotExisting() throws ParseException{
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
		
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
		
		//empty joblist
		List<JobPosting> jobList = new ArrayList<JobPosting>();
		
		try {
			jobList = idc.getInstructorPosting(-1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals( "No instructor selected" , error);
					
		
		
	}
	
	
	@Test
	public void removeInstructorPostingTest() throws ParseException{
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
		
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
		
		assertEquals( 2 , instructor.getJobOfferings().size());
		
		try {
			idc.removePosting(0,0);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals( 1 , instructor.getJobOfferings().size());
					
		
		
	}
	
	@Test
	public void removeInstructorPostingNoPosting() throws ParseException{
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
		
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
		
		assertEquals( 2 , instructor.getJobOfferings().size());
		
		try {
			idc.removePosting(-1,0);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals( "No posting selected" , error);
					
		
		
	}
	
	@Test
	public void removeInstructorPostingNoInstructor() throws ParseException{
		//create the dep/instructor controller
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
		
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
		
		assertEquals( 2 , instructor.getJobOfferings().size());
		
		try {
			idc.removePosting(0,-1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals( "No instructor selected" , error);
					
		
	}
	
	@Test 
	public void getApplicationTestSuccess() throws ParseException{
		
		
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a student
		Student student = new Student("zabrey" , department);
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
		
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
		
		//create student application
		Application appOne = new Application ("something" , "somethingelse" , 1, jobPost , student);
		//create student application
		Application appTwo = new Application ("something" , "somethingelse" , 2, jobPost , student);
		
		Application theApp = null;
		Application theAppTwo = null;
		
		try {
			theApp = idc.getApplication (0 , student.getName() , 0  );
			theAppTwo = idc.getApplication (0 , student.getName() , 1  );
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals(1 , theApp.getPreference());
		assertEquals(2 , theAppTwo.getPreference());
		
		
	}
	
	@Test 
	public void getApplicationTestNullStudent() throws ParseException{
		
		
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a student
		Student student = new Student("zabrey" , department);
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
		
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
		
		//create student application
		Application appOne = new Application ("something" , "somethingelse" , 1, jobPost , student);
		//create student application
		Application appTwo = new Application ("something" , "somethingelse" , 2, jobPost , student);
		
		Application theApp = null;
		
		
		try {
			theApp = idc.getApplication (0 , null , 0  );
			
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("No student selected" ,error);
		
		
		
	}
	
	@Test 
	public void getApplicationTestEmptyStudent() throws ParseException{
		
		
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a student
		Student student = new Student("zabrey" , department);
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
		
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
		
		//create student application
		Application appOne = new Application ("something" , "somethingelse" , 1, jobPost , student);
		//create student application
		Application appTwo = new Application ("something" , "somethingelse" , 2, jobPost , student);
		
		Application theApp = null;
		
		
		try {
			theApp = idc.getApplication (0 , "" , 0  );
			
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("No student selected" ,error);
		
		
		
	}
	
	
	
	@Test 
	public void getApplicationTestNoApplication() throws ParseException{
		
		
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a student
		Student student = new Student("zabrey" , department);
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
		
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
		
		//create student application
		Application appOne = new Application ("something" , "somethingelse" , 1, jobPost , student);
		//create student application
		Application appTwo = new Application ("something" , "somethingelse" , 2, jobPost , student);
		
		Application theApp = null;
		
		
		try {
			theApp = idc.getApplication (0 , student.getName() , -1 );
			
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("No application selected" ,error);
		
		
		
	}
	
	
	@Test
	public void getApprovedStudentsTestSuccess() throws ParseException{
		
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a student
		Student student = new Student("zabrey" , department);
		
		//create a student
		Student studenttwo = new Student("zabreytwo" , department);
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
	    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
						
						
				
				
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
				
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
				
		//creating two jobs
		
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPost);
		Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobPostTwo);
		
		job1.setJobState(Job.JobState.Offered);
					
		//create Registrations
		
		Registration reg = new Registration (sqlEndDate , studenttwo , job1);
		Registration regTwo = new Registration (sqlEndDate , student , job2);
		
		
		//create an empty list of students
		
		List<Registration> listStudent = new ArrayList<Registration>();
		
		
		listStudent = idc.getInitialApprovedStudents(course);
		
		assertEquals(1 , listStudent.size());
		assertEquals("zabreytwo" , listStudent.get(0).getStudent().getName());
		
		
		
		
		
	}
	
	@Test
	public void getStudentsWithJobsTestSuccess() throws ParseException{
		
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a student
		Student student = new Student("zabrey" , department);
		
		//create a student
		Student studenttwo = new Student("zabreytwo" , department);
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
	    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
						
						
				
				
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
				
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
				
		//creating two jobs
		
		Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPost);
		Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobPostTwo);
		
		job1.setJobState(Job.JobState.Filled);
					
		//create Registrations
		
		Registration reg = new Registration (sqlEndDate , studenttwo , job1);
		Registration regTwo = new Registration (sqlEndDate , student , job2);
		
		
		//create an empty list of students
		
		List<Student> listStudent = new ArrayList<Student>();
		
		
		listStudent = idc.getStudentsWithJobs(instructor);
		
		assertEquals(1 , listStudent.size());
		assertEquals("zabreytwo" , listStudent.get(0).getName());
		
		
		
		
	}
	
	@Test
	public void deleteJobPostingTestSuccess () throws ParseException {
		
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a student
		Student student = new Student("zabrey" , department);
		
		//create a student
		Student studenttwo = new Student("zabreytwo" , department);
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
	    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
						
						
				
				
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
				
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
				
		//create Applications for this posting
		//making the student apply for the two postings
				
		Application firstApp = new Application ("someting", "something", 1, jobPost, student);
		Application secondtApp = new Application ("someting", "something", 2, jobPostTwo, student);
		try {
			idc.deleteJobPosting(jobPost);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals( 2 , instructor.getJobOfferings().size() );
		assertEquals( "firstPosting" , instructor.getJobOffering(0).getDescription());
		
		
		
	}
	
@Test	
public void deleteJobPostingTestNoPosting () throws ParseException {
		
		InstructorDepartmentController idc = new InstructorDepartmentController(department);
		
		//create error
		String error = null;
		
		
		
		//create a course and an instructor
		Course course = new Course (13, "ecse444" , 5 , 5 , department );
		Instructor instructor = new Instructor ("zabron" , course , department );
		
		//create a student
		Student student = new Student("zabrey" , department);
		
		//create a student
		Student studenttwo = new Student("zabreytwo" , department);
		
		//create a job posting
		java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
	    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

		java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		//creating job dates
		java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
		java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

		java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
		java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
						
						
				
				
		//create new job postings
		JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
				
		//create new job postings
		JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
			
		
				
		
		try {
			idc.deleteJobPosting(null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("No posting selected",error );
		
		
		
	}
	
	
	
@Test
public void testRejectStudentSuccess() throws ParseException {
	
	
	InstructorDepartmentController idc = new InstructorDepartmentController(department);
	
	//create error
	String error = null;
	
	
	
	//create a course and an instructor
	Course course = new Course (13, "ecse444" , 5 , 5 , department );
	Instructor instructor = new Instructor ("zabron" , course , department );
	
	//create a student
	Student student = new Student("zabrey" , department);
	
	//create a student
	Student studenttwo = new Student("zabreytwo" , department);
	
	//create a job posting
	java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
	java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

	java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
	java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
	
	//creating job dates
	java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
	java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

	java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
	java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
					
					
			
			
	//create new job postings
	JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
			
	//create new job postings
	JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
			
	//create Applications for this posting
	//making the student apply for the two postings
			
	Application firstApp = new Application ("someting", "something", 1, jobPost, student);
	Application secondtApp = new Application ("sometingTwo", "somethingTwo", 2, jobPostTwo, student);
	
	idc.rejectStudent(firstApp);
	
	assertEquals(1 , student.getJobApplications().size());
	assertEquals("somethingTwo" , student.getJobApplication(0).getPastExperience());
	
	
}
	
@Test
public void modifyAllocationTestSuccess() throws ParseException{
	
	
	
	InstructorDepartmentController idc = new InstructorDepartmentController(department);
	
	//create error
	String error = null;
	
	
	
	//create a course and an instructor
	Course course = new Course (13, "ecse444" , 5 , 5 , department );
	Instructor instructor = new Instructor ("zabron" , course , department );
	
	//create a student
	Student student = new Student("zabrey" , department);
	
	//create a student
	Student studenttwo = new Student("zabreytwo" , department);
	
	//create a job posting
	java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
	java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

	java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
	java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
	
	//creating job dates
	java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
	java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

	java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
	java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
					
					
			
			
	//create new job postings
	JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
			
	//create new job postings
	JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
			
	//creating two jobs
	
	Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPost);
	Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobPostTwo);
	job1.setJobState(Job.JobState.Offered);
	
	try {
		idc.modifyAllocation(course, job1, 20, 30);
	} catch (InvalidInputException e) {
		error = e.getMessage();
		
	}
	
	assertEquals(30 , job1.getHourlyRate());
	assertEquals(20 , job1.getWorkHours());
	
	
	
	
}


@Test
public void modifyAllocationTestNoStudent() throws ParseException{
	
	
	
	InstructorDepartmentController idc = new InstructorDepartmentController(department);
	
	//create error
	String error = null;
	
	
	
	//create a course and an instructor
	Course course = new Course (13, "ecse444" , 5 , 5 , department );
	Instructor instructor = new Instructor ("zabron" , course , department );
	
	//create a student
	Student student = new Student("zabrey" , department);
	
	//create a student
	Student studenttwo = new Student("zabreytwo" , department);
	
	//create a job posting
	java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
	java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

	java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
	java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
	
	//creating job dates
	java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
	java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

	java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
	java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
					
					
			
			
	//create new job postings
	JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
			
	//create new job postings
	JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
			
	//creating two jobs
	
	Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPost);
	Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobPostTwo);
	job1.setJobState(Job.JobState.Offered);
	
	try {
		idc.modifyAllocation(course, null, 20, 30);
	} catch (InvalidInputException e) {
		error = e.getMessage();
		
	}
	
	assertEquals("No job selected" , error);
	
	
	
	
	
}

@Test
public void modifyAllocationTestNoHours() throws ParseException{
	
	
	
	InstructorDepartmentController idc = new InstructorDepartmentController(department);
	
	//create error
	String error = null;
	
	
	
	//create a course and an instructor
	Course course = new Course (13, "ecse444" , 5 , 5 , department );
	Instructor instructor = new Instructor ("zabron" , course , department );
	
	//create a student
	Student student = new Student("zabrey" , department);
	
	//create a student
	Student studenttwo = new Student("zabreytwo" , department);
	
	//create a job posting
	java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
	java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

	java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
	java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
	
	//creating job dates
	java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
	java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

	java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
	java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
					
					
			
			
	//create new job postings
	JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
			
	//create new job postings
	JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
			
	//creating two jobs
	
	Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPost);
	Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobPostTwo);
	job1.setJobState(Job.JobState.Offered);
	
	try {
		idc.modifyAllocation(course, job1, -20, 30);
	} catch (InvalidInputException e) {
		error = e.getMessage();
		
	}
	
	assertEquals("Please select hours greater than zero" , error);
	
	
	
	
	
	
}

@Test
public void modifyAllocationTestNoWage() throws ParseException{
	
	
	
	InstructorDepartmentController idc = new InstructorDepartmentController(department);
	
	//create error
	String error = null;
	
	
	
	//create a course and an instructor
	Course course = new Course (13, "ecse444" , 5 , 5 , department );
	Instructor instructor = new Instructor ("zabron" , course , department );
	
	//create a student
	Student student = new Student("zabrey" , department);
	
	//create a student
	Student studenttwo = new Student("zabreytwo" , department);
	
	//create a job posting
	java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
	java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

	java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
	java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
	
	//creating job dates
	java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
	java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

	java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
	java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
					
					
			
			
	//create new job postings
	JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
			
	//create new job postings
	JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
			
	//creating two jobs
	
	Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPost);
	Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobPostTwo);
	
	job1.setJobState(Job.JobState.Offered);
	
	try {
		idc.modifyAllocation(course, job1, 20, -30);
	} catch (InvalidInputException e) {
		error = e.getMessage();
		
	}
	
	assertEquals("Please select wage greater than zero" , error);
	
	
	
	
	
}

@Test
public void modifyAllocationTestNoPermission() throws ParseException{
	
	
	
	InstructorDepartmentController idc = new InstructorDepartmentController(department);
	
	//create error
	String error = null;
	
	
	
	//create a course and an instructor
	Course course = new Course (13, "ecse444" , 5 , 5 , department );
	Instructor instructor = new Instructor ("zabron" , course , department );
	
	//create a student
	Student student = new Student("zabrey" , department);
	
	//create a student
	Student studenttwo = new Student("zabreytwo" , department);
	
	//create a job posting
	java.util.Date deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
    java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
	java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");

	java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
	java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
	
	//creating job dates
	java.util.Date startDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-06");
	java.util.Date endDateJob = new SimpleDateFormat("yyyy-MM-dd").parse("2029-05-06");

	java.sql.Date sqlStartDateJob = new java.sql.Date(startDateJob.getTime());
	java.sql.Date sqlEndDateJob = new java.sql.Date(endDateJob.getTime());
					
					
			
			
	//create new job postings
	JobPosting jobPost = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "firstPosting" , instructor);
			
	//create new job postings
	JobPosting jobPostTwo = new JobPosting (sqlDeadLine , sqlStartDate , sqlEndDate , "secondPosting" , instructor);
			
	//creating two jobs
	
	Job job1 = new Job (10 ,10 , sqlStartDateJob ,sqlEndDateJob ,jobPost);
	Job job2 = new Job (10 , 10 , sqlStartDateJob , sqlEndDateJob ,jobPostTwo);
	
	job1.setJobState(Job.JobState.Open);
	
	try {
		idc.modifyAllocation(course, job1, 20, 30);
	} catch (InvalidInputException e) {
		error = e.getMessage();
		
	}
	
	assertEquals("Cannot modify allocation of this job" , error);
	
	
	
	
	
}



	

}
