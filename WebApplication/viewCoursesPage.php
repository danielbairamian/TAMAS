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
	$tc = new TamasController();
	$courseList = $tc->viewCourses();
	if(!empty($courseList)){
		$counter = 1;
		foreach ($courseList as $course){
			$budgetGiven = $course->getBudgetGiven();
			$numLabs = $course->getNumLabs();
			$numTutorials = $course->getNumTutorials();
			$theInstructor = $course->getInstructor();
			$courseId = $course->getId();
			$name = $theInstructor->getName();
		
			echo "<p> Course $counter:</p>";
			echo "<p> ID: $courseId </p>";
			echo "<p> Instructor: $name</p>";
			echo "<p> Budget: $budgetGiven</p>";
			echo "<p> Labs: $numLabs</p>";
			echo "<p> Tutorials: $numTutorials</p>";
			echo "<p>-----------------------------------------------------------------------------------------------------------------------</p>";
			$counter = $counter + 1;
		}
	}
	?>
	<form method="post">
			<p><input type="submit" name="back" value="Back"></p>
			</form>
			<?php 
				if(isset($_POST['back'])){
					header("Location:homePage.php");
					exit();
				}
			?>