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
require_once 'controller/TamasController.php';
session_start();
$error = "";
try {
	$idofstudent = null;
	if(isset($_POST['studentspinner2']) && $_POST['studentspinner2'] != "Not Selected"){
		$idofstudent = $_POST['studentspinner2'];
	}
	$_SESSION['studentError2'] = "";
	$tc = new TamasController();
	$evaluationList = $tc->viewEvaluationsOfStudent($idofstudent);
} catch ( Exception $e) {
	$error =  $e->getMessage();
	
	$_SESSION['studentError2']=$error;
		
}
if($error != ""){
	echo "<span class='error'>";
	if(isset($_SESSION['studentError2']) && !empty($_SESSION['studentError2'])){
		echo $_SESSION["studentError2"];
	}
	echo "</span>";
}
elseif($error == ""){
	$name = $evaluationList[0]->getStudent()->getName();
	echo "<p>Student: $name</p>";
	$counter = 1;
	foreach ($evaluationList as $evaluation){
		$text = $evaluation->getText();
		$instructorname = $evaluation->getInstructorName();
		echo "<p>Instructor: $instructorname<p>";
		echo "<p>Evaluation $counter: $text</p>";
		$counter = $counter + 1;
	}
}
?>
<form method="post">
			<p><input type="submit" name="back" value="Back"></p>
			</form>
			<?php 
				if(isset($_POST['back'])){
					header("Location:studentEvaluationPage.php");
					exit();
				}
			?>
</body>
</html>