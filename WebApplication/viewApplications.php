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
$_SESSION['studentApplicationError'] = "";
$error = "";
try {
	$idofstudent = null;
	if(isset($_POST['studentspinner']) && $_POST['studentspinner'] != "Not Selected"){
		$idofstudent = $_POST['studentspinner'];
	}
	$tc = new TamasController();
	$applicationList = $tc->viewApplicationsOfStudent($idofstudent);
} catch (Exception $e) {
	$error = $e->getMessage();
	$_SESSION['studentApplicationError'] = $error;
}
if ($error == ""){
	$name = $applicationList[0]->getStudent()->getName();
	echo "Student: $name";
	foreach ($applicationList as $application){
		$email = $application->getEmailAddress();
		$experience = $application->getPastExperience();
		$preference = $application->getPreference();
		echo "<p>Email: $email</p>";
		echo "<p>Past Experience: $experience</p>";
		echo "<p>Preference: $preference</p>";
		echo "<p>-----------------------------------------------------------------------------------------------------------------------</p>";
	}
}
elseif ($error!= ""){
	echo "<span class='error'>";
	
	if(isset($_SESSION['studentApplicationError']) && !empty($_SESSION['studentApplicationError'])){
		echo "* " . $_SESSION['studentApplicationError'];
	}
	echo "</span>";
}
?>
<form method="post">
			<p><input type="submit" name="back" value="Back"></p>
			</form>
			<?php 
				if(isset($_POST['back'])){
					header("Location:applicationsPage.php");
					exit();
				}
			?>
</body>

</html>
