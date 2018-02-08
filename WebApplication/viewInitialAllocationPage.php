<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TamasApplication</title>
<style>
.error {
	color: #FF0000;
}
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
$currentInstructor = $_SESSION['currentinstructor'];
$tc = new TamasController();
$error = "";
try {
	$registrationList = $tc->viewInitialAllocation($currentInstructor);
} catch (Exception $e) {
	$error = $e->getMessage();
	echo "<span class='error'>$error</span>";
}
echo "<span class='error'>";
if(isset($_SESSION['errorModify1']) && !empty($_SESSION['errorModify1'])){
	echo " * " . $_SESSION["errorModify1"];
}
echo "</span>";
echo "<span class='error'>";
if(isset($_SESSION['errorModify2']) && !empty($_SESSION['errorModify2'])){
	echo " * " . $_SESSION["errorModify2"];
}
echo "</span>";
echo "<span class='error'>";
if(isset($_SESSION['errorModify3']) && !empty($_SESSION['errorModify3'])){
	echo " * " . $_SESSION["errorModify3"];
}
echo "</span>";
echo "<span class='error'>";
if(isset($_SESSION['errorModify4']) && !empty($_SESSION['errorModify4'])){
	echo " * " . $_SESSION["errorModify4"];
}
echo "</span>";
if($error == ""){
	foreach ($registrationList as $registration){
		if($registration->getJob()->getJobState() == "JobStateOffered"){
			$student = $registration->getStudent();
			$idOfStudent = $student->getId();
			$name = $student->getName();
			$job = $registration->getJob();
			$jobposting = $job->getJobPosting();
			$jobType = $job->getJobType();
			if($jobType == "JobTypeTA"){
				$jobType= "TA";
			}
			else{
				$jobType = "Grader";
			}
			$hourlyRate = $job->getHourlyRate();
			$workHours = $job->getWorkHours();
			$description = $jobposting->getDescription();
			$startDate = $jobposting->getStartDate();
			$endDate = $jobposting->getEndDate();	
			echo "<p>Student: $name</p>";
			echo "<p>Job Type: $jobType</p>";
			echo "<p>Job Description: $description</p>";
			echo "<p>Start Date: $startDate</p>";
			echo "<p>End Date: $endDate</p>";
			echo "<p>Hours: $workHours</p>";
			echo "<p>Hourly Rate: $hourlyRate</p>";
			echo "<form action='modifyInitialAllocation.php?idOfStudent=$idOfStudent&JobStartDate=$startDate&JobEndDate=$endDate&JobWorkHours=$workHours&JobHourlyRate=$hourlyRate' method='post'>";
			echo "<p>Set New Work Hours<input type='text' name='workHours'></p>";
			echo "<p>Set New Hourly Rate<input type='text' name='hourlyRate'></p>";
			echo "<p><input type='submit' name='submitModification' value='Modify Allocation'></p>";
			echo "</form>";
			echo "<p>-----------------------------------------------------------------------------------------------------------------------</p>";
		}
	}
}	
?>
<form method="post">
		<p>
			<input type="submit" name="back" value="Back">
		</p>
	</form>
			<?php 
				if(isset($_POST['back'])){
					header("Location:homePage.php");
					exit();
				}
			?>
</body>
</html>
