<?php
require_once 'controller/TamasController.php';
session_start();
$idOfStudent = $_GET['idOfStudent'];
$JobStartDate = $_GET['JobStartDate'];
$JobEndDate = $_GET['JobEndDate'];
$JobWorkHours = $_GET['JobWorkHours'];
$JobHourlyRate = $_GET['JobHourlyRate'];

try {
	$workHours = null;
	if(isset($_POST['workHours']) && $_POST['workHours'] != ""){
		$workHours = (int) $_POST['workHours'];
	}
	$hourlyRate = null;
	if(isset($_POST['hourlyRate']) && $_POST['hourlyRate'] != ""){
		$hourlyRate = (int) $_POST['hourlyRate'];
	}
	$_SESSION['errorModify1'] = "";
	$_SESSION['errorModify2'] = "";
	$_SESSION['errorModify3'] = "";
	$_SESSION['errorModify4'] = "";
	$tc = new TamasController();
	$tc->modifyInitialAllocation($idOfStudent, $JobStartDate, $JobEndDate, $JobWorkHours, $JobHourlyRate, $workHours, $hourlyRate);

} catch (Exception $e) {
	$errors = explode("@", $e->getMessage());
	foreach ($errors as $error) {
		if (substr($error, 0, 1) == "1"){
			$_SESSION["errorModify1"]=substr($error, 1);
		}
		if (substr($error, 0, 1) == "2"){
			$_SESSION["errorModify2"]=substr($error, 1);
		}
		if (substr($error, 0, 1) == "3"){
			$_SESSION["errorModify3"]=substr($error, 1);
		}
		if (substr($error, 0, 1) == "4"){
			$_SESSION["errorModify4"]=substr($error, 1);
		}
	}
}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/ECSE321SemesterProjectPHP/viewInitialAllocationPage.php"/>
	</head>
</html>