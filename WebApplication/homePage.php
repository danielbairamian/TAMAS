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
	
	$thecurrentinstructor = $_SESSION['currentinstructor'];
	$name = $thecurrentinstructor->getName();
	echo "<h1>Welcome $name!</h1>";
	//need to figure out why the value isnt carrying over correctly
	
	
	?>
	<h2>Please select one of the actions below</h2>
	
	
	<a href="viewJobPostingPage.php"> <img src="viewJobPostingsButton.png" alt=""></a>
	<a href="addJobPostingPage.php"> <img src="postJob.png" alt=""></a>
	<a href="studentEvaluationPage.php"><img src="evaluationsButton.png" alt=""></a>
	<br></br>
	<a href="viewCoursesPage.php"><img src="coursesButton.png" alt=""></a>
	<a href="applicationsPage.php"><img src="applicationsButton.png" alt=""></a>
	<a href="viewJobsPage.php"><img src="viewStudentJobs.png" alt=""></a>
	<a href="viewInitialAllocationPage.php"><img src="viewAllocationButton.png" alt=""></a>
	
</body>
