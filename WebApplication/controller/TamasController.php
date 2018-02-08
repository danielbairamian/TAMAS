<?php
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\persistence\PersistenceTamas.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Department.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Instructor.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\JobPosting.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Application.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Course.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Job.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Registration.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Student.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Evaluation.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\controller\InputValidator.php';

class TamasController{
	private $department;
	
	public function __construct(){
		//$this->department = $department;
		
		
	}
	//this method creates a job posting for an instructor
	public function publishJobPosting($deadline, $startDate, $endDate, $description, $theInstructor, $workHours, $jobType){
		//first, we check for any sort of bad input
		$error = "";
		$startDate2 = InputValidator::validate_input($startDate);
		$endDate2 = InputValidator::validate_input($endDate);
		$deadline2 = InputValidator::validate_input($deadline);
		$workHours2 = (int) $workHours;
		if(strtotime($deadline2) > strtotime($startDate2)){
			//throw exception because deadline cant be after startDate
			$error .= "@1Deadline cannot be after start date ";
		}
		
		if(strtotime($startDate2) > strtotime($endDate2)){
			$error .= "@2Start date cannot be after end date ";
		}
		if(strlen($description) == 0 || $description == null){
			$error .= "@3Description cannot be empty ";
		}
		if(strtotime($deadline2) == false){
			$error .= "@4Deadline cannot be empty ";
		}
		if(strtotime($startDate2) == false){
			$error .= "@5Start date cannot be empty ";
		}
		if(strtotime($endDate2) == false){
			
			$error .= "@6End date cannot be empty ";
		}
		if($theInstructor == null){
			$error .= "@7No instructor was selected ";
		}
		if(!is_int($workHours2) || $workHours2 > 180 || $workHours2 < 45 || $workHours2 == null){
			$error .= "@8Invalid work hours! Must be an integer between 45 and 180 ";
		}
		
		if(strlen($error) != 0 || $error != null){
				
			throw new Exception($error);
		}
		else{
			//load the data
			$persistence = new PersistenceTamas();
			$this->department = $persistence->loadDataFromStore();
			//need to find the instructor and then create the job posting and assign to the instructor
			foreach ($this->department->getInstructors() as $instructor){
				if($instructor->equals($theInstructor)){
					$verifiedInstructor = $instructor;
				}
			}
			//create a job object to store work hours, pay, job type
			$jobPosting = new JobPosting($deadline2, $startDate2, $endDate2, $description, $verifiedInstructor);
			$theJob = new Job($workHours2, 1, $startDate2, $endDate2, $jobPosting);
			$theJob->setJobType($jobType);
			$persistence->writeDataToStore($this->department);
			
		}
	}
	//returns a list of job postings the instructor has
	public function viewJobPostings($anInstructor){
		$persistence = new PersistenceTamas();
		$this->department = $persistence->loadDataFromStore();
		$jobPostings = array();
		//$counter = 0;
		$error = "";
		//find the instructor
		foreach ($this->department->getInstructors() as $instructor){
			if($instructor->equals($anInstructor)){
				$theInstructor = $instructor;
			}
		}
		//only want to get active jobs
		foreach($theInstructor->getJobOfferings() as $job){
			if($job->getJobPostingState() == "JobPostingStateActive"){
				array_push($jobPostings,$job);
			}
		}
		if(empty($jobPostings)){
			$error .= "No Job Postings Available";
			throw new Exception($error);
		}
		else{
			return $jobPostings;
		}
		
	}
	//creates an evaluation for a student
	public function writeEvaluation($theDescription, $theInstructor, $idOfStudent){
		$persistence = new PersistenceTamas();
		$this->department = $persistence->loadDataFromStore();
		$error = "";
		if($theDescription == "" || $theDescription == null){
			$error .= "@1Evaluation cannot be empty ";
		}
		if ($idOfStudent == null){
			$error .= "@2No student was selected";
		}
		if($error != ""){
			throw new Exception($error);
		}
		else{
			//need to find the instructor
			foreach ($this->department->getInstructors() as $instructor){
				if($instructor->equals($theInstructor)){
					$verifiedInstructor = $instructor;
				}
			}
			//need to find the student
			foreach ($this->department->getStudents() as $student){
				if($student->getId() == $idOfStudent){
					$theStudent = $student;
				}
			}
			$theCourse = $verifiedInstructor->getCourse();
			$theStudent->addEvaluationVia($theDescription, $theCourse, $verifiedInstructor->getName());
			$persistence->writeDataToStore($this->department);
			
		}
		
	}
	//returns list of all evaluations for a student
	public function viewEvaluationsOfStudent($idOfStudent){
		if($idOfStudent == null){
			throw new Exception("No Student Selected");
		}
		
		$persistence = new PersistenceTamas();
		$this->department = $persistence->loadDataFromStore();
		foreach ($this->department->getStudents() as $student){
			if($student->getId() == $idOfStudent){
				$theStudent = $student;
			}
		}
		
		$evaluationList = $theStudent->getEvaluations();
		if(empty($evaluationList)){
			$error = "Student has no evaluations";
			throw new Exception($error);
		}
		else{
			return $evaluationList;
		}
		
	}
	//returns a list of all courses in the department
	public function viewCourses(){
		$persistence = new PersistenceTamas();
		$this->department = $persistence->loadDataFromStore();
		$courseList = $this->department->getCourses();
		if(empty($courseList)){
			$error = "No courses currently available";
			throw new Exception($error);
		}
		else{
			return $courseList;
		}
		
	}
	
	public function removeJobPosting($anInstructor, $deadline, $startDate, $endDate, $description){
		$persistence = new PersistenceTamas();
		$this->department = $persistence->loadDataFromStore();
		foreach ($this->department->getInstructors() as $instructor){
			if($instructor->equals($anInstructor)){
				$theInstructor = $instructor;
			}
		}
		//$jobPosting->getDeadline() == $deadline && $jobPosting->getStartDate() == $startDate && $jobPosting->getEndDate() == $endDate && 
		foreach ($theInstructor->getJobOfferings() as $jobPosting){
			if ( $jobPosting->getDeadline() == $deadline && $jobPosting->getStartDate() == $startDate && $jobPosting->getEndDate() == $endDate && $jobPosting->getDescription() == $description){
				$theJobPosting = $jobPosting;

				$theJobPosting->setJobPostingState(2);
				$persistence->writeDataToStore($this->department);
				break;
			}
		}
		
		
	}
	//returns list of all the applications for a student
	public function viewApplicationsOfStudent($idOfStudent){
		if($idOfStudent == null){
			throw new Exception("Please select a student");
		}
		$persistence = new PersistenceTamas();
		$this->department = $persistence->loadDataFromStore();
		foreach ($this->department->getStudents() as $student){
			if($student->getId() == $idOfStudent){
				$theStudent = $student;
			}
		}
		$applicationList = $theStudent->getJobApplications();
		if(empty($applicationList)){
			$error = "This student has no applications";
			throw new Exception($error);
		}
		else{
			return $applicationList;
		}
		
	}
	//returns ist of jobs for a student
	public function viewJobsOfStudent($idOfStudent){
		if($idOfStudent == null){
			$error = "No student selected";
			throw new Exception($error);
		}
		$persistence = new PersistenceTamas();
		$this->department = $persistence->loadDataFromStore();
		foreach ($this->department->getStudents() as $student){
			if($student->getId() == $idOfStudent){
				$theStudent = $student;
			}
		}
		$jobList = array();
		foreach($theStudent->getRegistrations() as $registration){
			array_push($jobList, $registration->getJob());
		}
		if(empty($jobList)){
			$error = "This student has no assigned jobs";
			throw new Exception($error);
		}
		else{
			return $jobList;
		}
	}
	//returns the list of registrations pertaining to the specific instructor's job postings
	public function viewInitialAllocation($anInstructor){
		$persistence = new PersistenceTamas();
		$this->department = $persistence->loadDataFromStore();
		foreach ($this->department->getInstructors() as $instructor){
			if($instructor->equals($anInstructor)){
				$theInstructor = $instructor;
			}
		}
		$registrationList = array();
		foreach($theInstructor->getJobOfferings() as $jobPosting){
			foreach ($jobPosting->getJobs() as $job){
				if($job->getJobState() == "JobStateOffered"){
					array_push($registrationList, $job->getRegistration());
						
				}
			}
		}
		if(empty($registrationList)){
			$error = "No allocation list available";
			throw new Exception($error);
		}
		else{
			return $registrationList;
		}
	}
	// allows instructor to change hpurs and pay of a allocation and then sets the job to be instuctor approved
	public function modifyInitialAllocation($idOfStudent, $JobStartDate, $JobEndDate, $JobWorkHours, $JobHourlyRate, $workHours, $hourlyRate){
		$error = "";
		if(!is_int($hourlyRate)){
			$error .= "@1Hourly Rate must be an Integer!";
		}
		if(!is_int($workHours) || $workHours < 45 || $workHours > 180){
			$error .= "@2Work Hours must be an Integer between 45 and 180! ";
		}
		if($workHours == null){
			$error .= "@3Work Hours cannot be empty! ";
		}
		if($hourlyRate == null){
			$error .= "@4Hourly Rate cannot be empty!";
		}
		if($error != ""){
			throw new Exception($error);
		}
		$JobHourlyRate = (int) $JobHourlyRate;
		$JobWorkHours = (int) $JobWorkHours;
		$persistence = new PersistenceTamas();
		$this->department = $persistence->loadDataFromStore();
		//find the student
		foreach ($this->department->getStudents() as $student){
			if($student->getId() == $idOfStudent){
				$theStudent = $student;
			}
		}
		//find the job
		
		foreach ($theStudent->getRegistrations() as $aregistration){
			$job = $aregistration->getJob();
			if($job->getStartDate() == $JobStartDate && $job->getEndDate() == $JobEndDate && $job->getHourlyRate() == $JobHourlyRate && $job->getWorkHours() == $JobWorkHours){
				$theJob = $job;
			}
		}
		
		foreach ($theStudent->getRegistrations() as $registration){
			if($registration->getJob()->getStartDate() == $theJob->getStartDate() && $registration->getJob()->getEndDate() == $theJob->getEndDate() && $registration->getJob()->getHourlyRate() == $theJob->getHourlyRate() && $registration->getJob()->getWorkHours() == $theJob->getWorkHours() ){
				$theRegistration = $registration;
			}
		}
		$theRegistration->getJob()->setWorkHours($workHours);
		$theRegistration->getJob()->setHourlyRate($hourlyRate);
		$theRegistration->getJob()->setJobState(4);
		$persistence->writeDataToStore($this->department);
	}
	
}
?>