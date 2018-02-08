
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>TamasApplication</title>
		<style>
			.error {color: #FF0000;}
		</style>
	</head>
<body>
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
	
	session_start();
	
	//retrieve data from model
	$persistence = new PersistenceTamas();
	$department = $persistence->loadDataFromStore();
	
	if ($department->hasCourses() == false){
		$course1 = new Course(100, "ECSE321", 0, 1, $department);
		$course2 = new Course(100, "ECSE223", 0, 1, $department);
		$course3 = new Course(100, "ECSE222", 0, 1, $department);
	}
	$persistence->writeDataToStore($department);
	
	$department = $persistence->loadDataFromStore();
	if($department->hasInstructors() == false){
		$instructor1 = new Instructor("Jerry", $department->getCourse_index(0), $department);
		$jobPosting = new JobPosting("2017-11-05", "2017-11-06", "2017-11-07", "description", $instructor1);
		$job1 = new Job(50, 20, "2017-11-06", "2017-11-07", $jobPosting);
		$job2 = new Job(50, 20, "2017-11-06", "2017-11-07", $jobPosting);
		$instructor2 = new Instructor("Mark", $department->getCourse_index(1), $department);
		$instructor3 = new Instructor("Erin", $department->getCourse_index(2), $department);
	}
	$persistence->writeDataToStore($department);
	
	$department = $persistence->loadDataFromStore();
	if($department->hasStudents() == false){
		$student1 = new Student("Billy", $department);
		$student2 = new Student("Jole", $department);
		$student3 = new Student("George", $department);
		$application1 = new Application("billy@gmail.com", "past experience", 1, $department->getInstructor_index(0)->getJobOffering_index(0), $student1);
		$registration1 = new Registration("2017-10-04", $student1, $department->getInstructor_index(0)->getJobOffering_index(0)->getJob_index(0));
		$application2 = new Application("jole@gmail.com", "past experience", 1, $department->getInstructor_index(0)->getJobOffering_index(0), $student2);
		$registration2 = new Registration("2017-10-04", $student2, $department->getInstructor_index(0)->getJobOffering_index(0)->getJob_index(1));
		$registration1->getJob()->setJobState(3);
		$registration2->getJob()->setJobState(2);
		$evaluation1 = new Evaluation("an evaluation", $department->getCourse_index(0), $department->getInstructor_index(0)->getName(), $student2);
	}
	$persistence->writeDataToStore($department);
	
	
	//
	?>
	<h1>Welcome to the Teaching Assistant Management System</h1>
	<h2>Please tell us who you are</h2>
	<form method="post">
		<p>Instructor?<select name='instructorspinner'>
			
		<option selected>Not Selected</option>
		<?php 
	//$_SESSION['currentInstructor'] = null;
	foreach ($department->getInstructors() as $instructor){
		$name = $instructor->getName();
		echo "	<option value=$name>" . $name . "</option>";
	}
	//$_SESSION['currentInstructor'] = $_POST['instructorspinner'];
		?>
		</select>
		
		<p><input type="submit" name=loginsubmit value="Log In"></p>
		
	</form>
	<?php 
		if(isset($_POST['loginsubmit']) && $_POST['instructorspinner'] != "Not Selected"){
			//$theinstructorspinner = $_POST['instructorspinner'];
			foreach ($department->getInstructors() as $anInstructor){
				$thename = $anInstructor->getName();
				if( $thename == $_POST['instructorspinner']){
					$_SESSION['currentinstructor'] = $anInstructor;
					//$department->setCurrentInstructor($anInstructor);
				}
			}
			header("Location:homePage.php");
		}
		else{
			echo "<span class= 'error'>*  You must select your name  </span>";
		}
?>
	
	
</body>