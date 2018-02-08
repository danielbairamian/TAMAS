package ca.mcgill.ecse321.tamas.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse321.tamas.model.Application;
import ca.mcgill.ecse321.tamas.model.Department;
import ca.mcgill.ecse321.tamas.model.Evaluation;
import ca.mcgill.ecse321.tamas.model.Job;
import ca.mcgill.ecse321.tamas.model.JobPosting;
import ca.mcgill.ecse321.tamas.model.Registration;
import ca.mcgill.ecse321.tamas.model.Student;
import ca.mcgill.ecse321.tamas.persistence.PersistenceXStream;

public class StudentDepartmentController {

	private Department department;

	public StudentDepartmentController(Department department){
		this.department = department;
	}


	/**
	 * This method creates a new application for the student and the job posting he's applying to
	 *
	 * Take as input a JobPosting ,
	 * Two strings (email and experience of the student ) and a preference
	 * The method checks if:
	 * 1. The Deadline is before the actual current date
	 * 2. the student is null
	 * 3. if the student already applied for this job
	 * 4. if the student already put the same preference for that job
	 * 5. if the email or the experience are empty or null
	 *
	 *
	 * @param job
	 * @param email
	 * @param experience
	 * @param preference
	 * @throws InvalidInputException
	 */
	public void applyForJob(JobPosting job, String email, String experience, int preference) throws InvalidInputException{

		Date deadline = job.getDeadline();

		java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

		if(currentDate.after(deadline)){
			throw new InvalidInputException("The deadline has been passed.");
		}


		//get current student
		Student currentStudent = department.getCurrentStudent();

		if(currentStudent == null){
			throw new InvalidInputException("The student is null.");
		}

		//checking if the current student already applied for the position
		//checking if the current student already used the preference
		// TODO test those two
		if(currentStudent.hasJobApplications()){
			for(Application application : currentStudent.getJobApplications()){
				if(application.getJobApplication().equals(job)){
					throw new InvalidInputException("You already applied for this job.");
				}
				if(application.getPreference() == preference){
					throw new InvalidInputException("You have already specified this preference for another job.");
				}
			}
		}

		if(email == null || email.trim().equals("")){
			throw new InvalidInputException("Please provide your email.");
		}

		if(experience == null || experience.trim().equals("")){
			throw new InvalidInputException("Please provide your experience.");
		}
		
		int i=0;
		if(currentStudent.hasRegistrations()){
			for(Job job1 : job.getJobs()){
					for(i=0; i<currentStudent.getRegistrations().size(); i++){
						if(currentStudent.getRegistration(i).getJob().equals(job1)){
							throw new InvalidInputException("You can't reapply to this job");
						}
					}
			}
		}

		Application application = new Application(email, experience, preference, job, currentStudent);
		PersistenceXStream.saveToXMLwithXStream(department);

	}


	/**
	 * This method cancels an application of a student
	 *
	 * Take as input an application
	 * The method checks if:
	 * 1. The application is for the current student
	 * 2. If an application has been selected
	 *
	 *
	 * @param application
	 * @throws InvalidInputException
	 */
	public void cancelApplication(Application application) throws InvalidInputException{

		int deleted = 0;
		if(application != null){

			//getting the current student
			Student currentStudent = department.getCurrentStudent();

			//
			for(Application jobApplication : currentStudent.getJobApplications()){
				if(jobApplication.equals(application)){
					application.delete();
					deleted = 1;
					break;
				}
			}
			if(deleted != 1){
				throw new InvalidInputException("This is application is not for you.");
			}
		}else{
			throw new InvalidInputException("Please select an application in order to cancel it.");
		}
		PersistenceXStream.saveToXMLwithXStream(department);

	}

	/**
	 * This method returns a list of the student's applications
	 *
	 * @return List<Application>
	 */
	public List<Application> viewOwnApplications() {

		//get current student
		Student currentStudent = department.getCurrentStudent();

		
		return currentStudent.getJobApplications();
		
	}

	/**
	 * This method returns a list of the student's evaluations
	 *
	 *
	 *
	 * @return List<Evaluation>
	 */
	public List<Evaluation> viewOwnEvaluations(){
		//getting the current student
		Student currentStudent = department.getCurrentStudent();

		//getting the evaluations that the student have
		List<Evaluation> evaluations = currentStudent.getEvaluations();

		//return null if the student doesn't have an evaluation, otherwise return the list
		if(evaluations == null || evaluations.size()==0){
			return null;
		}else{
		    //return own evaluations
			return evaluations;
		}
	}

	/**
	 * This method returns a list of the student's job that are "FILLED"
	 *
	 * @return List<Job>
	 */
	public List<Job> viewOwnEmployment() {

		//initiate a list of jobs
		List<Job> studentJob = new ArrayList<Job>();

		//get current student
		Student currentStudent = department.getCurrentStudent();

		//get all of the student's registrations
		List<Registration> registrations = currentStudent.getRegistrations();

		//for each registration get the job
		for(Registration reg : registrations){
			if(reg.getJob().getJobState().equals(Job.JobState.Filled)){
			studentJob.add(reg.getJob());
			}
		}
        //returning the student current employment
		return studentJob;

	}


	/**
	 * This method lets the student accept a job
	 *
	 * The method takes as input a job
	 * It checks if :
	 * 1. The current student is associated with the job's student
	 * 2. The Job is in "DepartmentApproved" state (i.e Passed the Final Allocation)
	 *
	 *
	 * @param job
	 * @throws InvalidInputException
	 */
	public void acceptJobOffer(Job job) throws InvalidInputException{


		boolean hasJob = false;

		//get current student
		Student currentStudent = department.getCurrentStudent();


		//getting all the student's applications
		List<Registration> studentRegistrations = currentStudent.getRegistrations();

		for(Registration registration : studentRegistrations){
			if (registration.getJob().equals(job)){
				hasJob = true;
				break;
			}
		}

		if(!hasJob){
			throw new InvalidInputException("The selected job is not for the current student.");
		}

		if(!(job.getJobState().equals(Job.JobState.DepartmentApproved))){
			throw new InvalidInputException("You Can't accept a job that is not offered to you.");
		}


		job.setJobState(Job.JobState.Filled);
		
		PersistenceXStream.saveToXMLwithXStream(department);

	}
/**
* This method lets the student decline a job
	 *
	 * The method takes as input a job
	 * It checks if :
	 * 1. The current student is associated with the job's student
	 * 2. The Job is in "DepartmentApproved" state (i.e Passed the Final Allocation)
	 *
	 *
	 * @param job
	 * @throws InvalidInputException
 */
public void declineJobOffer(Job job) throws InvalidInputException{


		boolean hasJob = false;

		//get current student
		Student currentStudent = department.getCurrentStudent();


		//getting all the student's applications
		List<Registration> studentRegistrations = currentStudent.getRegistrations();

		for(Registration registration : studentRegistrations){
			if (registration.getJob().equals(job)){
				hasJob = true;
				break;
			}
		}

		if(!hasJob){
			throw new InvalidInputException("The selected job is not for the current student.");
		}

		if(!(job.getJobState().equals(Job.JobState.DepartmentApproved))){
			throw new InvalidInputException("You Can't decline a job that is not offered to you.");
		}


		Registration registration = job.getRegistration();
		job.delete();
		registration.delete();


		PersistenceXStream.saveToXMLwithXStream(department);
	}


}
