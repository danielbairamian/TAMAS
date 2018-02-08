<?php
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Department.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\controller\TamasController.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\persistence\PersistenceTamas.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Instructor.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\JobPosting.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Application.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Course.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Job.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Registration.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Student.php';
require_once 'C:\Users\josep\workspacephp321\ECSE321SemesterProjectPHP\model\Evaluation.php';

class TamasControllerTest extends PHPUnit_Framework_TestCase{
	protected $department;
	protected $persistence;
	protected $tc;

	protected function setUp(){
		$this->tc = new TamasController();
		$this->persistence = new PersistenceTamas();

		$this->department = $this->persistence->loadDataFromStore();
		
	}
	protected function tearDown(){
		$this->department->delete();
		$this->persistence->writeDataToStore($this->department);
	}
	public function testAddJobPosting(){
		$theCourse = new Course(100.0, "111", 1, 1, $this->department);
		$theInstructor = new Instructor("Bob", $theCourse, $this->department);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));
		$deadline = "2017-02-15";
		$startDate = "2017-03-15";
		$endDate = "2017-11-15";
		$description = "a description";
		$workHours = 46;
		$hourlyRate = 10;
		$jobType = 1;
		$error = "";
		$this->persistence->writeDataToStore($this->department);
		try{
			$this->tc->publishJobPosting($deadline, $startDate, $endDate, $description, $theInstructor, $workHours, $jobType);
		} catch (Exception $e){
			//check for error
			$error .= $e->getMessage();
		}
		
		$this->assertEquals($error, "");
		$newDepartment = $this->persistence->loadDataFromStore();
		$newJobPosting = $newDepartment->getInstructor_index(0)->getJobOffering_index(0);
		$newInstructor = $newJobPosting->getInstructor();
		$this->assertEquals(1, count($newInstructor->getJobOfferings()));
		$newDepartment->delete();
		$this->persistence->writeDataToStore($newDepartment);
	}
	public function testStartAfterEndAddJobPosting(){

		$theCourse = new Course(100.0, "112", 1, 1, $this->department);
		$theInstructor = new Instructor("Jerry", $theCourse, $this->department);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));

		$deadline = "2017-02-15";
		$startDate = "2017-12-15";
		$endDate = "2017-11-15";
		$description = "a description";
		try{
			$this->tc->publishJobPosting($deadline, $startDate, $endDate, $description, $theInstructor, 46, 1);
		} catch (Exception $e){
			//check for error
			$error = $e->getMessage();
		}
		$this->assertEquals("@2Start date cannot be after end date ", $error);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));

	}
	public function testDeadlineAfterStartAddJobPosting(){

		$theCourse = new Course(100.0, "113", 1, 1, $this->department);
		$theInstructor = new Instructor("Mark", $theCourse, $this->department);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));

		$deadline = "2017-04-15";
		$startDate = "2017-03-15";
		$endDate = "2017-11-15";
		$description = "a description";
		try{
			$this->tc->publishJobPosting($deadline, $startDate, $endDate, $description, $theInstructor, 46, 1);
		} catch (Exception $e){
			//check for error
			$error = $e->getMessage();
		}
		$this->assertEquals("@1Deadline cannot be after start date ", $error);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));

	}

	public function testNullDescriptionAddJobPosting(){

		$theCourse = new Course(100.0, "115", 1, 1, $this->department);
		$theInstructor = new Instructor("Alfred", $theCourse, $this->department);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));

		$deadline = "2017-02-15";
		$startDate = "2017-03-15";
		$endDate = "2017-11-15";
		$description = null;
		try{
			$this->tc->publishJobPosting($deadline, $startDate, $endDate, $description, $theInstructor, 46, 1);
		} catch (Exception $e){
			//check for error
			$error = $e->getMessage();
		}
		$this->assertEquals("@3Description cannot be empty ", $error);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));


	}
	public function testNullDeadlineAddJobPosting(){

		$theCourse = new Course(100.0, "116", 1, 1, $this->department);
		$theInstructor = new Instructor("Tom", $theCourse, $this->department);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));

		$deadline = null;
		$startDate = "2017-03-15";
		$endDate = "2017-11-15";
		$description = "description";
		try{
			$this->tc->publishJobPosting($deadline, $startDate, $endDate, $description, $theInstructor, 46, 1);
		} catch (Exception $e){
			//check for error
			$error = $e->getMessage();
		}
		$this->assertEquals("@4Deadline cannot be empty ", $error);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));
	}
	public function testNullStartAddJobPosting(){
		$theCourse = new Course(100.0, "117", 1, 1, $this->department);
		$theInstructor = new Instructor("Tom", $theCourse, $this->department);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));

		$deadline = "2017-02-15";
		$startDate = null;
		$endDate = "2017-11-15";
		$description = "description";
		try{
			$this->tc->publishJobPosting($deadline, $startDate, $endDate, $description, $theInstructor, 46, 1);
		} catch (Exception $e){
			//check for error
			$error = $e->getMessage();
		}
		$this->assertEquals("@1Deadline cannot be after start date @5Start date cannot be empty ", $error);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));
	}
	public function testNullEndAddJobPosting(){
		$theCourse = new Course(100.0, "118", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));

		$deadline = "2017-02-15";
		$startDate = "2017-03-15";
		$endDate = null;
		$description = "description";
		try{
			$this->tc->publishJobPosting($deadline, $startDate, $endDate, $description, $theInstructor, 46, 1);
		} catch (Exception $e){
			//check for error
			$error = $e->getMessage();
		}
		$this->assertEquals("@2Start date cannot be after end date @6End date cannot be empty ", $error);
		$this->assertEquals(0, count($theInstructor->getJobOfferings()));
	}
	public function testSuccessViewJobPostings(){
		$theCourse = new Course(100.0, "119", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$jobPostings = array();
		$job = new JobPosting("2017-01-11", "2017-02-15", "2018-02-16", "description", $theInstructor, 46, 1);
		array_push($jobPostings, $job);
		$this->persistence->writeDataToStore($this->department);
		$error = "";
		$jobList = array();
		try {
			$jobList = $this->tc->viewJobPostings($theInstructor);
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "");
		$this->assertEquals($jobPostings, $jobList);
	}
	public function testNoJobPostings(){
		$error = "";
		
		$theCourse = new Course(100.0, "140", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$this->persistence->writeDataToStore($this->department);
		try {
			$jobList = $this->tc->viewJobPostings($theInstructor);
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "No Job Postings Available");
		
	}
	public function testWriteEvaluationSuccess(){
		$theCourse = new Course(100.0, "119", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$theDescription = "a description";
		$this->persistence->writeDataToStore($this->department);
		$error = "";
		try {
			$this->tc->writeEvaluation($theDescription, $theInstructor, $theStudent->getId());
		} catch (Exception $e) {
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "");
		
	}
	public function testNullOrEmptyDescriptionWriteEvaluation(){
		$theCourse = new Course(100.0, "120", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$theDescription = null;
		$error = "";
		try {
			$this->tc->writeEvaluation($theDescription, $theInstructor, $theStudent);
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "@1Evaluation cannot be empty ");
		$theDescription = "";
		$error = "";
		try {
			$this->tc->writeEvaluation($theDescription, $theInstructor, $theStudent);
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "@1Evaluation cannot be empty ");
	}
	public function testNullStudentWriteEvaluation(){
		$theCourse = new Course(100.0, "121", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = null;
		$theDescription = "a description";
		$error = "";
		try {
			$this->tc->writeEvaluation($theDescription, $theInstructor, $theStudent);
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "@2No student was selected");
	}
	public function testViewEvaluationsOfStudentSuccess(){
		$theCourse = new Course(100.0, "122", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$theStudent->addEvaluationVia("a description", $theCourse, $theInstructor->getName());
		$this->persistence->writeDataToStore($this->department);
		$error = "";
		try {
			$evaluationList = $this->tc->viewEvaluationsOfStudent($theStudent->getId());
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "");
		$this->assertEquals($evaluationList[0]->getText(), "a description");
	}
	public function testViewEvaluationsOfStudentNoEvaluations(){
		$theCourse = new Course(100.0, "123", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$error = "";
		$this->persistence->writeDataToStore($this->department);
		try {
			$evaluationList = $this->tc->viewEvaluationsOfStudent($theStudent->getId());
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "Student has no evaluations");
		
	}
	public function testViewCoursesSuccess(){
		$theCourse1 = new Course(100.0, "124", 1, 1, $this->department);
		$theCourse2 = new Course(100.0, "125", 1, 1, $this->department);
		$this->persistence->writeDataToStore($this->department);
		$courseList = array();
		array_push($courseList, $theCourse1, $theCourse2);
		$error = "";
		try {
			$courses = $this->tc->viewCourses();
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "");
		$this->assertEquals($courseList, $courses);
		
	}
	public function testViewCoursesNoCourses(){
		$error = "";
		try {
			$courses = $this->tc->viewCourses();
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "No courses currently available");
		
	}
	public function testRemoveJobPostings(){
		$theCourse = new Course(100.0, "126", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$jobPosting = new JobPosting("2017-02-11", "2017-02-12", "2017-02-13", "a description", $theInstructor);
		$this->persistence->writeDataToStore($this->department);
		$newDepartment = $this->persistence->loadDataFromStore();
		$newJobPosting = $newDepartment->getInstructor_index($newDepartment->indexOfInstructor($theInstructor))->getJobOffering_index(0);
		$this->assertEquals($newJobPosting, $jobPosting);
		$newInstructor = $newJobPosting->getInstructor();
		$this->tc->removeJobPosting($newInstructor, "2017-02-11","2017-02-12", "2017-02-13", "a description" );
		$newDepartment = $this->persistence->loadDataFromStore();
		$newJobPosting = $newDepartment->getInstructor_index($newDepartment->indexOfInstructor($theInstructor))->getJobOffering_index(0);
		$this->assertEquals("JobPostingStateDeleted",$newJobPosting->getJobPostingState());
	}
	public function testViewApplicationsOfStudent(){
		$theCourse = new Course(100.0, "127", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$theJobPosting = new JobPosting("2017-02-11", "2017-02-12", "2017-02-13", "a description", $theInstructor);
		$application = new Application("billy@gmail.com", "an experience", 1, $theJobPosting, $theStudent);
		$applicationList = array();
		array_push($applicationList, $application);
		$this->persistence->writeDataToStore($this->department);
		$newDepartment = $this->persistence->loadDataFromStore();
		$newStudent = $newDepartment->getStudent_index(0);
		$newApplicationList = array();
		$error = "";
		try {
			$newApplicationList = $this->tc->viewApplicationsOfStudent($newStudent->getId());
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "");
		$this->assertEquals($applicationList, $newApplicationList);
	}
	public function testViewApplicationsOfStudentNoApplications(){
		$theCourse = new Course(100.0, "128", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$this->persistence->writeDataToStore($this->department);
		$newDepartment = $this->persistence->loadDataFromStore();
		$newStudent = $newDepartment->getStudent_index(0);
		$error = "";
		try {
			$newApplicationList = $this->tc->viewApplicationsOfStudent($newStudent->getId());
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "This student has no applications");
	}
	public function testViewJobsOfStudent(){
		$theCourse = new Course(100.0, "129", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$theJobPosting = new JobPosting("2017-02-11", "2017-02-12", "2017-02-13", "a description", $theInstructor);
		$job = new Job(45, 15, "2017-02-12", "2017-02-13", $theJobPosting);
		$registration = new Registration("2017-04-03", $theStudent, $job);
		$jobList = array();
		array_push($jobList, $job);
		$this->persistence->writeDataToStore($this->department);
		$newDepartment = $this->persistence->loadDataFromStore();
		$newStudent = $newDepartment->getStudent_index(0);
		$error = "";
		try {
			$newJobList = $this->tc->viewJobsOfStudent($theStudent->getId());
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "");
		$this->assertEquals($jobList, $newJobList);
	}
	public function testViewJobsOfStudentNoJobs(){
		$theCourse = new Course(100.0, "130", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$error = "";
		$this->persistence->writeDataToStore($this->department);
		try {
			$newJobList = $this->tc->viewJobsOfStudent($theStudent->getId());
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "This student has no assigned jobs");
		
	}
	public function testViewInitialAllocation(){
		$theCourse = new Course(100.0, "131", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$theJobPosting = new JobPosting("2017-02-11", "2017-02-12", "2017-02-13", "a description", $theInstructor);
		$job = new Job(45, 15, "2017-02-12", "2017-02-13", $theJobPosting);
		$job->setJobState(3);
		$registration = new Registration("2017-04-03", $theStudent, $job);
		$registrationList = array();
		array_push($registrationList, $registration);
		$this->persistence->writeDataToStore($this->department);
		$newDepartment = $this->persistence->loadDataFromStore();
		$newInstructor = $newDepartment->getInstructor_index(0);
		$error = "";
		try {
			$newRegistrationList = $this->tc->viewInitialAllocation($newInstructor);
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "");
		$this->assertEquals($registrationList, $newRegistrationList);
		
		
	}
	public function testViewInitialAllocationNoAllocation(){
		$theCourse = new Course(100.0, "132", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$error = "";
		$this->persistence->writeDataToStore($this->department);
		try {
			$newRegistrationList = $this->tc->viewInitialAllocation($theInstructor);
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "No allocation list available");
	}
	public function testModifyInitialAllocation(){
		$theCourse = new Course(100.0, "133", 1, 1, $this->department);
		$theInstructor = new Instructor("William", $theCourse, $this->department);
		$theStudent = new Student("Billy", $this->department);
		$theJobPosting = new JobPosting("2017-02-11", "2017-02-12", "2017-02-13", "a description", $theInstructor);
		$job = new Job(45, 15, "2017-02-12", "2017-02-13", $theJobPosting);
		$registration = new Registration("2017-04-03", $theStudent, $job);
		$this->persistence->writeDataToStore($this->department);
		$error = "";
		try {
			$this->tc->modifyInitialAllocation($theStudent->getId(), $job->getStartDate(), $job->getEndDate(), $job->getWorkHours(), $job->getHourlyRate(), 47, 30);
		} catch (Exception $e) {
			$error .= $e->getMessage();
		}
		$this->assertEquals($error, "");
		
		$newDepartment = $this->persistence->loadDataFromStore();
		$newRegistration = $newDepartment->getStudent_index(0)->getRegistration_index(0);
		$newJob = $newRegistration->getJob();
		$this->assertEquals($newJob->getWorkHours(), 47);
		$this->assertEquals($newJob->getHourlyRate(), 30);
		
		
		
	}
}



?>