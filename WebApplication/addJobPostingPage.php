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


//$currentInstructor = $_SESSION['currentinstructor'];

//echo "<form action='addJobPosting.php' method='post'>";
/*echo "<p>Instructor?<select name='instructorspinner'>";
foreach ($department->getInstructors() as $instructor){
	echo "<option>" . $instructor->getName() . "</option>";
}
echo "<select><span class='error'>";
if (isset($_SESSION['errorAddJobPosting7']) && !empty($_SESSION['errorAddJobPosting7'])){
	echo " * " . $_SESSION["errorAddJobPosting7"];
}
echo "</span></p>";*/
?>
	<form action="addJobPosting.php" method="post">
		<p>Deadline Date?<input type="date" name="deadline" value="<?php echo date('Y-m-d'); ?>" />
		<span class="error">
			<?php 
			if (isset($_SESSION['errorAddJobPosting1']) && !empty($_SESSION['errorAddJobPosting1'])) {
				echo " * " . $_SESSION["errorAddJobPosting1"];
			}
			if (isset($_SESSION['errorAddJobPosting4']) && !empty($_SESSION['errorAddJobPosting4'])) {
				echo " * " . $_SESSION["errorAddJobPosting4"];
			}
			?>
			</span>
			<br></br>
			Start Date?<input type="date" name="startDate" value="<?php echo date('Y-m-d'); ?>" />
		<span class="error">
			<?php 
			if (isset($_SESSION['errorAddJobPosting2']) && !empty($_SESSION['errorAddJobPosting2'])) {
				echo " * " . $_SESSION["errorAddJobPosting2"];
			}
			if (isset($_SESSION['errorAddJobPosting5']) && !empty($_SESSION['errorAddJobPosting5'])) {
				echo " * " . $_SESSION["errorAddJobPosting5"];
			}
			?>
			</span>
			<br></br>
			End Date?<input type="date" name="endDate" value="<?php echo date('Y-m-d'); ?>" />
			<span class="error">
			<?php 
			if (isset($_SESSION['errorAddJobPosting6']) && !empty($_SESSION['errorAddJobPosting6'])) {
				echo " * " . $_SESSION["errorAddJobPosting6"];
			}
			?>
			</span>
			<br></br>
			Description?<input type="text" name="description"/>
			<span class="error">
			<?php 
			if (isset($_SESSION['errorAddJobPosting3']) && !empty($_SESSION['errorAddJobPosting3'])) {
				echo " * " . $_SESSION["errorAddJobPosting3"];
			}
			
			?>
			</span>
			<br></br>
			Work Hours?<input type="text" name="workHours"/>
			<span class="error">
			<?php 
			if (isset($_SESSION['errorAddJobPosting8']) && !empty($_SESSION['errorAddJobPosting8'])) {
				echo " * " . $_SESSION["errorAddJobPosting8"];
			}
			
			?>
			</span>
			
			
			
			<br></br>
			TA?<input type="radio" name="jobType" value="1" checked/>
			
			Grader?<input type="radio" name="jobType" value="2"/>
			
			<p><input type="submit" value="Add Job Posting"></p>
			
			
	</form>
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
</html>
