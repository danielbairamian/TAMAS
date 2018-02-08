<?php
require_once 'controller/TamasController.php';
session_start();
$_SESSION["errorAddJobPosting1"]="";
$_SESSION["errorAddJobPosting2"]="";
$_SESSION["errorAddJobPosting3"]="";
$_SESSION["errorAddJobPosting4"]="";
$_SESSION["errorAddJobPosting5"]="";
$_SESSION["errorAddJobPosting6"]="";
$_SESSION["errorAddJobPosting7"]="";
$_SESSION["errorAddJobPosting8"]="";
$currentInstructor = $_SESSION['currentinstructor'];
$tc = new TamasController();
try {
	$deadline = null;
	if (isset($_POST['deadline'])){
		$deadline = date('Y-m-d', strtotime($_POST['deadline']));
	}
	$startDate = null;
	if (isset($_POST['startDate'])){
		$startDate = date('Y-m-d', strtotime($_POST['startDate']));
	}
	$endDate = null;
	if (isset($_POST['endDate'])){
		$endDate = date('Y-m-d', strtotime($_POST['endDate']));
	}
	$description = null;
	if (isset($_POST['description'])){
		$description = $_POST['description'];
	}
	$workHours = null;
	if (isset($_POST['workHours'])){
		$workHours = $_POST['workHours'];
	}
	
	$jobType = $_POST['jobType'];
	
	
	//date('mm-dd-YYYY', strtotime($_POST['deadline']))
	//date('mm-dd-YYYY', strtotime($_POST['startDate']))
	//date('mm-dd-YYYY', strtotime($_POST['endDate']))
	// $_POST['description']
	$tc->publishJobPosting($deadline, $startDate, $endDate, $description, $currentInstructor, $workHours, $jobType);
	

	
} catch(Exception $e) {
	$errors = explode("@", $e->getMessage());
	foreach ($errors as $error) {
		if (substr($error, 0, 1) == "1"){
			$_SESSION["errorAddJobPosting1"]=substr($error, 1);
			}
		if (substr($error, 0, 1) == "2"){
			$_SESSION["errorAddJobPosting2"]=substr($error, 1);
			}
		if (substr($error, 0, 1) == "3"){
			$_SESSION["errorAddJobPosting3"]=substr($error, 1);
			}
		if (substr($error, 0, 1) == "4"){
				$_SESSION["errorAddJobPosting4"]=substr($error, 1);
			}
		if (substr($error, 0, 1) == "5"){
				$_SESSION["errorAddJobPosting5"]=substr($error, 1);
			}
		if (substr($error, 0, 1) == "6"){
				$_SESSION["errorAddJobPosting6"]=substr($error, 1);
			}
		if (substr($error, 0, 1) == "7"){
				$_SESSION["errorAddJobPosting7"]=substr($error, 1);
			}
		if (substr($error, 0, 1) == "8"){
				$_SESSION["errorAddJobPosting8"]=substr($error, 1);
			}
		
	}	
}
?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/ECSE321SemesterProjectPHP/addJobPostingPage.php"/>
	</head>
</html>