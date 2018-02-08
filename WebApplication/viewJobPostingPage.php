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
	
	
	$theInstructor = $_SESSION['currentinstructor'];
	//retrieve data from model
	$persistence = new PersistenceTamas();
	$department = $persistence->loadDataFromStore();
	foreach ($department->getInstructors() as $instructor){
		if($instructor->equals($theInstructor)){
			$verifiedInstructor = $instructor;
		}
	}
	$tc = new TamasController();
	$error = "";
	
	try {
		$jobPostings = $tc->viewJobPostings($verifiedInstructor);
	} catch (Exception $e) {
		$error .= $e->getMessage();
	}
	if ($error != ""){
		echo "<span class= 'error'> $error </span>";
	}
	else{
		$counter = 1;
		foreach ($jobPostings as $jobPosting){
			
			$deadline = $jobPosting->getDeadline();
			$startDate = $jobPosting->getStartDate();
			$endDate = $jobPosting->getEndDate();
			$description = $jobPosting->getDescription();
			$jobType = $jobPosting->getJob_index(0)->getJobType();
			if($jobType == "JobTypeTA"){
				$jobType = "TA";
			}
			else{
				$jobType = "Grader";
			}
			echo "<p>Job Post $counter</p>";
			
			echo "<p>Deadline: $deadline</p>";
			echo "<p>Start Date: $startDate</p>";
			echo "<p>End Date: $endDate</p>";
			echo "<p>Description: $description</p>";
			echo "<p>Job Type: $jobType";
			echo "<form action='removeJobPosting.php?counter=$counter&deadline=$deadline&startDate=$startDate&endDate=$endDate&description=$description' method='post'> ";
			//echo "<p><input type='hidden' name='counter'.$counter value=$counter/></p>";
			/*echo" <p><input type='hidden' name='deadline'.$counter value='<?php$deadline;?>'/></p>";
			echo"<p><input type='hidden' name='startDate'.$counter value='<?php$startDate;?>'/></p>";
			echo"<p><input type='hidden' name='endDate'.$counter value='<?php$endDate;?>'/></p>";
			echo"<p><input type='hidden' name='description'.$counter value='<?php$description;?>'/></p>";*/
			/*$_SESSION['deadline'.$counter] = $deadline;
			$_SESSION['startDate'.$counter] = $startDate;
			$_SESSION['endDate'.$counter] = $endDate;
			$_SESSION['description'.$counter] = $description;*/
			echo "<p><input type='submit' name='remove'.$counter value='Remove'></p>";
			echo "</form>";
			echo "<p>-----------------------------------------------------------------------------------------------------------------------</p>";
			$counter = $counter + 1;
		}
		/*for ($cnt = 1;$cnt <= $counter;$cnt = $cnt + 1){
			if(isset($_POST['remove' . $cnt])){
				$_SESSION['jobPostingToRemove'] = $cnt;
				
			}
		}*/
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
</body>