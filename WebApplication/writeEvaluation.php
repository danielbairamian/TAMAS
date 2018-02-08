<?php
require_once 'controller/TamasController.php';
session_start();


try {
	$currentInstructor = $_SESSION['currentinstructor'];
	$description = null;
	if(isset($_POST['evaluationText'])){
		$description = $_POST['evaluationText'];
	}
	$idofstudent = null;
	if(isset($_POST['studentspinner']) && $_POST['studentspinner'] != "Not Selected"){
		$idofstudent = $_POST['studentspinner'];
	}
	$_SESSION['studentError'] = "";
	$_SESSION['descriptionError'] = "";
	$tc = new TamasController();
	$tc->writeEvaluation($description, $currentInstructor, $idofstudent);
} catch (Exception $e) {
	$errors = explode("@", $e->getMessage());
	foreach ($errors as $error) {
		if (substr($error, 0, 1) == "1"){
			$_SESSION['descriptionError']=substr($error, 1);
		}
		if (substr($error, 0, 1) == "2"){
			$_SESSION['studentError']=substr($error, 1);
		}
	}
}



?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/ECSE321SemesterProjectPHP/studentEvaluationPage.php"/>
	</head>
</html>