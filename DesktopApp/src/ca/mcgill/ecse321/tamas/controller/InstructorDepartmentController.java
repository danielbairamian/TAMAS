package ca.mcgill.ecse321.tamas.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.tamas.model.Application;
import ca.mcgill.ecse321.tamas.model.Course;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Evaluation;
import ca.mcgill.ecse321.tamas.model.Instructor;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.JobPosting;
import ca.mcgill.ecse321.tamas.model.JobPosting.JobPostingState;
import ca.mcgill.ecse321.tamas.model.Registration;
import ca.mcgill.ecse321.tamas.model.Student;
import ca.mcgill.ecse321.tamas.persistence.PersistenceXStream;

public class InstructorDepartmentController {
	private Department department;

	public InstructorDepartmentController(Department dep){
		this.department = dep;
	}

	/**
	 * This method lets the instructor write an evaluation for a student working for him
	 * It checks if:
	 * 1. The evaluation description is null or empty
	 * 2. the student is null
	 * 3. the instructor is null
	 *
	 *
	 * @param desc
	 * @param instructor
	 * @param student
	 * @throws InvalidInputException
	 */
	public void writeEvaluatuation(String desc, Instructor instructor, Student student) throws InvalidInputException{
		if(desc == null || desc.trim().equals("")){
			throw new InvalidInputException("Evaluation cannot be empty");
		}

		if(student == null){
			throw new InvalidInputException("No student was selected");
		}

		if(instructor == null){
			throw new InvalidInputException("No instructor was selected");
		}


		Evaluation newEval = new Evaluation(desc, instructor.getCourse().getId(), instructor.getName(), student);
		PersistenceXStream.saveToXMLwithXStream(department);
	}

	/**
	 * This method lets the instructor view an evaluation he just wrote to a student
	 * It checks if :
	 * 1. There is an instructor selected
	 * 2. There is a student selected
	 * 3. If the student who's evaluation we want to see has an evaluation
	 *
	 *
	 * @param instructor
	 * @param student
	 * @return
	 * @throws InvalidInputException
	 */
	public Evaluation viewEvaluation(int instructor, int student) throws InvalidInputException{
		if(instructor == -1){
			throw new InvalidInputException("No instructor selected");
		}
		Instructor inst = department.getInstructor(instructor);

		if(student == -1){
			throw new InvalidInputException("No student selected");
		}
		Student theStudent = department.getStudent(student);
		Evaluation eval = null;

		for(Evaluation e : theStudent.getEvaluations()){
			if(e.getInstructorName() == inst.getName()){
				eval = e;
			}
		}
		if(eval == null){
			throw new InvalidInputException("This student has no evaluation");
		}
		return eval;
	}

	/**
	 * This method loads the list of all Job Postings an instructor has
	 * It checks if:
	 * 1. There is an instructor selected
	 *
	 *
	 * @param instructor
	 * @return
	 * @throws InvalidInputException
	 */
	public List<JobPosting> getInstructorPosting(int instructor) throws InvalidInputException{
		if(instructor == -1){
			throw new InvalidInputException("No instructor selected");
		}
		return department.getInstructor(instructor).getJobOfferings();
	}

	/**
	 * This method removes a job posting
	 * It checks if:
	 * 1. There is a posting selected
	 * 2. There is an instructor selected
	 *
	 *
	 * @param posting
	 * @param instructor
	 * @throws InvalidInputException
	 */
	public void removePosting(int posting, int instructor) throws InvalidInputException{
		if(posting == -1){
			throw new InvalidInputException("No posting selected");
		}

		if(instructor == -1){
			throw new InvalidInputException("No instructor selected");
		}

		department.getInstructor(instructor).getJobOffering(posting).delete();
		PersistenceXStream.saveToXMLwithXStream(department);
	}

	/**
	 * This method loads an application of a student
	 * It checks if:
	 * 1. There is a student selected
	 * 2. There is an application selected
	 *
	 * @param inst
	 * @param student
	 * @param application
	 * @return Application
	 * @throws InvalidInputException
	 */
	public Application getApplication(int inst, String student, int application) throws InvalidInputException{
		if(student == null || student == ""){
			throw new InvalidInputException("No student selected");

		}

		if(application == -1){
			throw new InvalidInputException("No application selected");
		}

		Student theStudent = null;

		for(Student s : department.getStudents()){
			if(s.getName() == student){
				theStudent = s;
			}
		}
		return theStudent.getJobApplication(application);
	}
	public List<Registration> getInitialApprovedStudents(Course course){
		List<Registration> students = new ArrayList<Registration>();

			for(Student s : department.getStudents()){
				for(Registration r : s.getRegistrations()){
					if(r.getJob().getJobState() == Job.JobState.Offered && !(students.contains(r))){
						students.add(r);
					}
				}
			}
			return students;
	}


	/**
	 * This method loads all students that have an active job (FILLED state)
	 *
	 * @param inst
	 * @return List<Student>
	 */
	public List<Student> getStudentsWithJobs(Instructor inst){
		List<Student> students = new ArrayList<Student>();

			for(JobPosting j : inst.getJobOfferings()){
				for(Job a : j.getJobs()){
					if(a.getJobState() == Job.JobState.Filled){
						students.add(a.getRegistration().getStudent());
					}
				}

		}
		return students;
	}

	/**
	 * This method lets the instructor delete a JobPosting (similar to removeJobPosting)
	 * The difference here is that this method sets the JobPosting to "Deleted" state
	 * Without actually deleting it
	 *
	 * It checks if:
	 * 1. There is a posting selected
	 *
	 * @param posting
	 * @throws InvalidInputException
	 */
	public void deleteJobPosting(JobPosting posting) throws InvalidInputException{
		if(posting == null){
			throw new InvalidInputException("No posting selected");

		}
		for(int i = 0 ; i < posting.getForms().size(); i++){
			posting.getForm(i).delete();
		}
		posting.setJobPostingState(JobPostingState.Deleted);
		PersistenceXStream.saveToXMLwithXStream(department);
	}
	

	/**
	 * This method rejects a student's application
	 *
	 * @param app
	 */
	public void rejectStudent(Application app){
		Student student = app.getStudent();
		app.delete();
		PersistenceXStream.saveToXMLwithXStream(department);
	}

	/**
	 * This method lets an instructor modify an allocation
	 * it checks if:
	 * 1. There is a job selected
	 * 2. The job is in Offered state (i.e : Department did the Initial Allocation)
	 * 3. The number of hours is greater than 0
	 * 4. The wage selected is greater than 0
	 *
	 * @param course
	 * @param job
	 * @param hours
	 * @param wage
	 * @throws InvalidInputException
	 */
	public void modifyAllocation(Course course, Job job, int hours, int wage) throws InvalidInputException{

		if(job == null){
			throw new InvalidInputException("No job selected");
		}

		if(!(job.getJobState() == Job.JobState.Offered)){
			throw new InvalidInputException("Cannot modify allocation of this job");
		}

		if(hours < 0){
			throw new InvalidInputException("Please select hours greater than zero");

		}

		if(wage < 0 ){
			throw new InvalidInputException("Please select wage greater than zero");
		}

		job.setHourlyRate(wage);
		job.setWorkHours(hours);
		course.setBudgetActual(course.getBudgetActual() - (float)(wage*hours));
		job.setJobState(Job.JobState.InstructorApproved);
		PersistenceXStream.saveToXMLwithXStream(department);

	}

}
