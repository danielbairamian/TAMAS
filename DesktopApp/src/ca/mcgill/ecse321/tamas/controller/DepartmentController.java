package ca.mcgill.ecse321.tamas.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse321.tamas.model.Application;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Instructor;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.JobPosting;
import ca.mcgill.ecse321.tamas.model.Registration;
import ca.mcgill.ecse321.tamas.model.Student;
import ca.mcgill.ecse321.tamas.persistence.PersistenceXStream;

public class DepartmentController {

	private Department department;
	private Instructor instructor;

	public DepartmentController(Department department){
		this.department = department;
	}

	// TODO
	/**
	 * This method lets the department add a course
	 * 
	 * It checks if:
	 * 1. The ID of the course is unique
	 * 2. A budget has been provided
	 * 3. An ID has been provided
	 * 
	 * 
	 * @param instructorName
	 * @param budgetGiven
	 * @param ID
	 * @param numLabs
	 * @param numTutorials
	 * @throws InvalidInputException
	 */
	public void addACourse(String instructorName, float budgetGiven, String ID, int numLabs, int numTutorials) throws InvalidInputException{
		//Course(float aBudgetGiven, String aId, int aNumLabs, int aNumTutorials, Department aDepartment)

		//checking for course duplicates
		if(department.hasCourses()){
			for(Course course : department.getCourses()){
				if(course.getId().equals(ID)){
					throw new InvalidInputException("The ID provided for the course already exists. Please enter a new one.");
				}
			}
		}

		//checking for budget
		if(budgetGiven==0){
			throw new InvalidInputException("Please provide a budget for the course.");
		}
		if(ID == null || ID.trim().equals("")){
			throw new InvalidInputException("Please provide an ID for the course.");
		}

		//creating a new course
		Course course = new Course(budgetGiven, ID, numLabs, numTutorials, department);
		course.setBudgetActual(budgetGiven);
		addInstructor(instructorName, course);
		PersistenceXStream.saveToXMLwithXStream(department);
	}

	/**
	 * This method lets the department add an instructor
	 * It checks if:
	 * 
	 * 1. The provided instructor name is null or empty
	 * 
	 * @param instName
	 * @param course
	 * @throws InvalidInputException
	 */
	public void addInstructor(String instName, Course course) throws InvalidInputException{
		if(instName == null || instName.trim() == ""){
			throw new InvalidInputException("Please provide an instructor name");
		}

		Instructor newInstructor = new Instructor(instName, course, department);
		PersistenceXStream.saveToXMLwithXStream(department);
	}

	
	/**
	 * This method allows a department to add a Job posting for an instructor
	 * 
	 * it checks if:
	 * 1. An Instructor has been chosen
	 * 2. the Undergrad wage is invalid
	 * 3. The Grad wage is invalid
	 * 4. The deadline , startDate and end date of the job posting are not null
	 * 5. If the start date and end date are correctly put
	 * 6. If the description of the job posting is empty
	 * 7. If the amount of hours is valid
	 * 8. If the job type is valid
	 * 
	 * 
	 * @param deadline
	 * @param startDate
	 * @param endDate
	 * @param description
	 * @param inst
	 * @param hours
	 * @param underGradWage
	 * @param gradWage
	 * @param position
	 * @throws InvalidInputException
	 */
	public void addJobPosting(Date deadline, Date startDate, Date endDate, String description, int inst, int hours,int underGradWage, int gradWage, String position) throws InvalidInputException{
		//currentInstructor = theDepartment.getCurrentInstructor();
		String error = "";
		if(inst == -1){
			error += "No Instructor chosen!";
		} else {
			instructor = department.getInstructor(inst);
		}
		if(underGradWage == -1){
			error += "Please enter a valid undergraduate wage";
		}
		
		if(gradWage == -1){
			error += "Please enter a valid graduate wage";
		}
		
		if(deadline == null){
			error += "No deadline date chosen!";
		}
		if(startDate == null){
			error += "No start date chosen!";
		}
		if(endDate == null){
			error += "No end date chosen!";
		}
		if(startDate !=null && endDate != null){
			if(startDate.after(endDate)){
				error += "Start date cannot be after end date!";
			}
		}
		if((startDate !=null && endDate != null)){
			if(startDate.equals(endDate)  && (startDate !=null || endDate != null)){
				error += "Start date cannot be the same as end date!";
			}
		}
		if((startDate !=null && endDate != null && deadline != null)){
			if(deadline.after(startDate) || deadline.after(endDate) ){
				error += "Deadline cannot be after start date or end date!";
			}
		}
		if(description.isEmpty()){
			error += "Description cannot be empty!";
		}

		if( hours == -1){
			error += "Please enter a valid input for hours/wage";
		}

		if(!(position.trim().toLowerCase().equals("ta") || position.trim().toLowerCase().equals("grader"))){
			error += "Please enter valid job type (TA, Grader)";
		}
								
		if(error.length() > 0)
			throw new InvalidInputException(error);


		JobPosting posting = new JobPosting(deadline, startDate, endDate, description, instructor);
		posting.setJobPostingState(JobPosting.JobPostingState.Active);
		if(position.trim().toLowerCase().equals("ta")){
			Job job = new Job(hours, underGradWage, startDate, endDate, posting); 
			Job job1 = new Job(hours, gradWage, startDate, endDate, posting);
			job.setJobType(Job.JobType.TA);
			job.setJobState(Job.JobState.Open);
		} else if(position.trim().toLowerCase().equals("grader")){
			Job job = new Job(hours, underGradWage, startDate, endDate, posting); 
			Job job1 = new Job(hours, gradWage, startDate, endDate, posting);
			job.setJobType(Job.JobType.Grader);
			job.setJobState(Job.JobState.Open);
		}
		PersistenceXStream.saveToXMLwithXStream(department);
	}

/**
 * This method loads all the students who's allocations has been modified by the instructor
 * 
 * it checks if:
 * 1. The input course is null
 * 
 * 
 * 
 * @param course
 * @return List<JobPosting>
 * @throws InvalidInputException
 */
	public List<Student> getApprovedStudents(Course course) throws InvalidInputException{
		if(course == null){
			throw new InvalidInputException("Course cannot be null");
		}
		List<Student> students = new ArrayList<Student>();

		for(JobPosting j : course.getInstructor().getJobOfferings()){
			for(Job a : j.getJobs()){
				if (a.getJobState() == Job.JobState.InstructorApproved &&  !(students.contains(a.getRegistration().getStudent()))){
					students.add(a.getRegistration().getStudent());
				}
			}
		}
		return students;
	}


	public List<JobPosting> getJobPostings(){
		List<JobPosting> list = new ArrayList<JobPosting>();
		for(Instructor i : department.getInstructors()){
			for(JobPosting j : i.getJobOfferings()){
				if(j.getJobPostingState() == JobPosting.JobPostingState.Active)
					list.add(j);
			}
		}

		return list;
	}

	/**
	 * 
	 * This method lets the department add an intial allocation to a student
	 * 
	 * it checks if:
	 * 1. The student , the job , or the date are not null
	 * 2. If the dates are valid
	 * 3. If the student is registered is part of the department
	 * 
	 * 
	 * @param student
	 * @param job
	 * @param dateRegistered
	 * @throws InvalidInputException
	 */
	public void addInitialAllocation(Student student, Job job, Date dateRegistered) throws InvalidInputException{

		if(student == null){
			throw new InvalidInputException("Please choose a student in order to register him.");
		}
		if(job == null){
			throw new InvalidInputException("Please choose a job in order to register him.");
		}
		if(dateRegistered== null){
			throw new InvalidInputException("Please choose a date in order to register the student.");
		}

		java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

		if(currentDate.after(dateRegistered)){
			throw new InvalidInputException("Please specify a date after than the current date.");
		}

		if(!department.getStudents().contains(student)){
			throw new InvalidInputException("The student is not registered.");
		}

		job.setJobState(Job.JobState.Offered);

		Registration registration = new Registration(dateRegistered, student, job);

		PersistenceXStream.saveToXMLwithXStream(department);

	}

	/**
	 * This method lets the department do the Final Allocation
	 * 
	 * It checks if:
	 * 1. The registration is null
	 * 
	 * @param registration
	 * @throws InvalidInputException
	 */
	public void approveFinalAllocation(Registration registration) throws InvalidInputException{
		if(registration == null){
			throw new InvalidInputException("Please choose a registration.");
		}else{
			Job currentJob = registration.getJob();
			currentJob.setJobState(Job.JobState.DepartmentApproved);
		}
		
		PersistenceXStream.saveToXMLwithXStream(department);
	}

	/**
	 * This method lets the department mass-approve all the student
	 * and add them all to the final allocation
	 * 
	 * @throws InvalidInputException
	 */
	public void approveAllAllocations() throws InvalidInputException{
		if(department.hasStudents()){
			for(Student student : department.getStudents()){
				if(student.hasRegistrations()){
					for(Registration registration : student.getRegistrations()){
						registration.getJob().setJobState(Job.JobState.DepartmentApproved);
					}
				}
			}
		}
	}

	/**
	 * This method lets the department hire a student (Similar to addInitialAllocation method
	 * 
	 * 
	 * @param app
	 */
	public void hireStudentInitialDepartment(Application app){
		Student student = app.getStudent();
		JobPosting posting = app.getJobApplication();
		Job templateJob;
		if(app.getStudent().getStudentLevel() == Student.StudentLevel.Undergraduate){
			templateJob = app.getJobApplication().getJob(0);
		} else {
			templateJob = app.getJobApplication().getJob(1);
		}
		Job newJob = new Job(templateJob.getWorkHours(), templateJob.getHourlyRate(),templateJob.getStartDate(), templateJob.getEndDate(), posting);
		newJob.setJobType(templateJob.getJobType());
		newJob.setJobState(Job.JobState.Offered);

		Registration reg = new Registration(new Date(System.currentTimeMillis()), student, newJob);

		app.delete();
		
		PersistenceXStream.saveToXMLwithXStream(department);
	}
	
	/**
	 * This method lets the department create a student 
	 * and specifies if the student is a Graduate or Undergraduate
	 * 
	 * it checks if:
	 * 1. the given name is null or empty
	 * 
	 * @param name
	 * @param grad
	 * @throws InvalidInputException
	 */
	public void createStudent(String name, boolean grad) throws InvalidInputException{
		if(name == null || name.trim().length() == 0){
			throw new InvalidInputException("No name inputted");
		}
		
		Student newStudent = new Student(name, department);
		
		if(grad){
			newStudent.setStudentLevel(Student.StudentLevel.Graduate);
		} else {
			newStudent.setStudentLevel(Student.StudentLevel.Undergraduate);
		}
		PersistenceXStream.saveToXMLwithXStream(department);
	}

	public void rejectAllocation(Registration reg){
		reg.getJob().delete();
	}
	
	public List<Course> getCourses(){
		return department.getCourses();
	}
	//	public void rejectStudent(Application app){
	//		Student student = app.getStudent();
	//		app.delete();
	//	}

	public List<Student> getAllStudents(){
		return department.getStudents();

	}

	public List<Instructor> getAllInstructors(){
		return department.getInstructors();
	}

	public Student getStudentAt(int index){
		return department.getStudent(index);
	}

	public Instructor getInstructorAt(int index){
		return department.getInstructor(index);

	}

	public Student getCurrentStudent(){
		return department.getCurrentStudent();
	}

	public Instructor getCurrentInstructor(){
		return department.getCurrentInstructor();
	}

	public List<Course> getAllCourses(){
		return department.getCourses();
	}

	public Course getCourseAt(int index){
		return department.getCourse(index);
	}


}


