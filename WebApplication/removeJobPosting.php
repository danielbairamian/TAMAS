<?php
require_once 'controller/TamasController.php';
session_start();
$currentInstructor = $_SESSION['currentinstructor'];
$thecounter = $_GET['counter'];

$deadline = $_GET['deadline'];

$startDate = $_GET['startDate'];


$endDate = $_GET['endDate'];


$description = $_GET['description'];




$tc = new TamasController();
$tc->removeJobPosting($currentInstructor, $deadline, $startDate, $endDate, $description);

?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/ECSE321SemesterProjectPHP/viewJobPostingPage.php"/>
	</head>
</html>