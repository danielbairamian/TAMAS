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
require_once 'controller/TamasController.php';
session_start ();
$error = "";
try {
	$_SESSION ['studentError'] = "";
	$currentInstructor = $_SESSION ['currentinstructor'];
	$idofstudent = null;
	if (isset ( $_POST ['studentspinner'] ) && $_POST ['studentspinner'] != "Not Selected") {
		$idofstudent = $_POST ['studentspinner'];
	}
	$tc = new TamasController ();
	$jobList = $tc->viewJobsOfStudent ( $idofstudent );
} catch ( Exception $e ) {
	$error .= $e->getMessage();
	$_SESSION ['noStudentError'] = $error;
}
if ($error != ""){
	echo "<span class= 'error'> $error </span>";
}
elseif ($error == "") {
	$name = $jobList [0]->getRegistration ()->getStudent ()->getName ();
	echo "<p> Student: $name</p>";
	foreach ( $jobList as $job ) {
		$jobPosting = $job->getJobPosting ();
		$startDate = $jobPosting->getStartDate ();
		$endDate = $jobPosting->getEndDate ();
		$description = $jobPosting->getDescription ();
		$jobType = $job->getJobType ();
		if ($jobType == "JobTypeTA") {
			$jobType = "TA";
		} else {
			$jobType = "Grader";
		}
		$hourlyRate = $job->getHourlyRate ();
		$workHours = $job->getWorkHours ();
		echo "<p>Job Type: $jobType</p>";
		echo "<p>Job Description: $description</p>";
		echo "<p>Start Date: $startDate</p>";
		echo "<p>End Date: $endDate</p>";
		echo "<p>Hours: $workHours</p>";
		echo "<p>Hourly Rate: $hourlyRate</p>";
		echo "<p>-----------------------------------------------------------------------------------------------------------------------</p>";
	}
}

?>
<form method="post">
		<p>
			<input type="submit" name="back" value="Back">
		</p>
	</form>
			<?php
			if (isset ( $_POST ['back'] )) {
				header ( "Location:viewJobsPage.php" );
				exit ();
			}
			?>
</body>
</html>
