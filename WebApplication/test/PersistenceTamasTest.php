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

class PersistenceTamasTest extends PHPUnit_Framework_TestCase{
	
	protected $persistence;
	
	protected function setUp(){
		$this->persistence = new PersistenceTamas();
	}
	protected function tearDown(){
		$department = $this->persistence->loadDataFromStore();
		$department->delete();
		$this->persistence->writeDataToStore($department);
	}
	public function testPersistenceTamas(){
		
		//create test data
		$department = new Department();
		$course = new Course(100000.0, "001", 1, 1, $department);
		$instructor = new Instructor("Billy Jean", $course, $department);
		$department->addCourse($course);
		$department->addInstructor($instructor);
		//write the data
		$this->persistence->writeDataToStore($department);
		//delete data
		$department->delete();
		$this->assertEquals(0,count($department->getCourses()));
		$this->assertEquals(0,count($department->getInstructors()));
		//load data
		$department = $this->persistence->loadDataFromStore();
		//check data
		$this->assertEquals(1,count($department->getCourses()));
		$this->assertEquals(1,count($department->getInstructors()));
		$this->assertEquals("001", $department->getCourse_index(0)->getId());
		$this->assertEquals("Billy Jean", $department->getInstructor_index(0)->getName());
	}
	
}