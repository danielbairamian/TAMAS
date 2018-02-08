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
$studentList = array();
$currentInstructor = $_SESSION['currentinstructor'];
$persistence = new PersistenceTamas();
$department = $persistence->loadDataFromStore();
foreach ($department->getInstructors() as $instructor){
	if($instructor->equals($currentInstructor)){
		$currentInstructor = $instructor;
	}
}
foreach ($currentInstructor->getJobOfferings() as $jobPosting){
	if($jobPosting->hasForms()){
		foreach ($jobPosting->getForms() as $form)
			$student = $form->getStudent();
		array_push($studentList, $student);
	}
	
}

?>
<form action="viewApplications.php" method="post">
	<p>Select a Student to View the Application of <select name="studentspinner">
	<option selected>Not Selected</option>
		<?php 
			foreach ($studentList as $theStudent){
				$name = $theStudent->getName();
				$id = $theStudent->getId();
				echo "<option value=$id>" . $name . "</option";
			}
		?>
	</select>
	<p><input type="submit" name=viewApplicationsSubmit value="View Applications"></p>
</form>

<form method="post">
			<p><input type="submit" name="back" value="Back"></p>
			</form>
			<?php 
				if(isset($_POST['back'])){
					header("Location:homePage.php");
					exit();
				}
			?>
</body>
</html>