<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Student
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Student Attributes
  private $name;

  //Autounique Attributes
  private $id;

  //Student State Machines
  private static $StudentLevelGraduate = 1;
  private static $StudentLevelUndergraduate = 2;
  private $StudentLevel;

  //Student Associations
  private $evaluations;
  private $department;
  private $jobApplications;
  private $registrations;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aDepartment)
  {
    $this->name = $aName;
    $this->id = self::$nextId++;
    $this->evaluations = array();
    $didAddDepartment = $this->setDepartment($aDepartment);
    if (!$didAddDepartment)
    {
      throw new Exception("Unable to create student due to department");
    }
    $this->jobApplications = array();
    $this->registrations = array();
    $this->setStudentLevel(self::$StudentLevelGraduate);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setName($aName)
  {
    $wasSet = false;
    $this->name = $aName;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getId()
  {
    return $this->id;
  }

  public function getStudentLevelFullName()
  {
    $answer = $this->getStudentLevel();
    return $answer;
  }

  public function getStudentLevel()
  {
    if ($this->StudentLevel == self::$StudentLevelGraduate) { return "StudentLevelGraduate"; }
    elseif ($this->StudentLevel == self::$StudentLevelUndergraduate) { return "StudentLevelUndergraduate"; }
    return null;
  }

  public function setStudentLevel($aStudentLevel)
  {
    if ($aStudentLevel == "StudentLevelGraduate" || $aStudentLevel == self::$StudentLevelGraduate)
    {
      $this->StudentLevel = self::$StudentLevelGraduate;
      return true;
    }
    elseif ($aStudentLevel == "StudentLevelUndergraduate" || $aStudentLevel == self::$StudentLevelUndergraduate)
    {
      $this->StudentLevel = self::$StudentLevelUndergraduate;
      return true;
    }
    else
    {
      return false;
    }
  }

  public function getEvaluation_index($index)
  {
    $aEvaluation = $this->evaluations[$index];
    return $aEvaluation;
  }

  public function getEvaluations()
  {
    $newEvaluations = $this->evaluations;
    return $newEvaluations;
  }

  public function numberOfEvaluations()
  {
    $number = count($this->evaluations);
    return $number;
  }

  public function hasEvaluations()
  {
    $has = $this->numberOfEvaluations() > 0;
    return $has;
  }

  public function indexOfEvaluation($aEvaluation)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->evaluations as $evaluation)
    {
      if ($evaluation->equals($aEvaluation))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getDepartment()
  {
    return $this->department;
  }

  public function getJobApplication_index($index)
  {
    $aJobApplication = $this->jobApplications[$index];
    return $aJobApplication;
  }

  public function getJobApplications()
  {
    $newJobApplications = $this->jobApplications;
    return $newJobApplications;
  }

  public function numberOfJobApplications()
  {
    $number = count($this->jobApplications);
    return $number;
  }

  public function hasJobApplications()
  {
    $has = $this->numberOfJobApplications() > 0;
    return $has;
  }

  public function indexOfJobApplication($aJobApplication)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->jobApplications as $jobApplication)
    {
      if ($jobApplication->equals($aJobApplication))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getRegistration_index($index)
  {
    $aRegistration = $this->registrations[$index];
    return $aRegistration;
  }

  public function getRegistrations()
  {
    $newRegistrations = $this->registrations;
    return $newRegistrations;
  }

  public function numberOfRegistrations()
  {
    $number = count($this->registrations);
    return $number;
  }

  public function hasRegistrations()
  {
    $has = $this->numberOfRegistrations() > 0;
    return $has;
  }

  public function indexOfRegistration($aRegistration)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->registrations as $registration)
    {
      if ($registration->equals($aRegistration))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public static function minimumNumberOfEvaluations()
  {
    return 0;
  }

  public function addEvaluationVia($aText, $aCourse, $aInstructorName)
  {
    return new Evaluation($aText, $aCourse, $aInstructorName, $this);
  }

  public function addEvaluation($aEvaluation)
  {
    $wasAdded = false;
    if ($this->indexOfEvaluation($aEvaluation) !== -1) { return false; }
    $existingStudent = $aEvaluation->getStudent();
    $isNewStudent = $existingStudent != null && $this !== $existingStudent;
    if ($isNewStudent)
    {
      $aEvaluation->setStudent($this);
    }
    else
    {
      $this->evaluations[] = $aEvaluation;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeEvaluation($aEvaluation)
  {
    $wasRemoved = false;
    //Unable to remove aEvaluation, as it must always have a student
    if ($this !== $aEvaluation->getStudent())
    {
      unset($this->evaluations[$this->indexOfEvaluation($aEvaluation)]);
      $this->evaluations = array_values($this->evaluations);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addEvaluationAt($aEvaluation, $index)
  {  
    $wasAdded = false;
    if($this->addEvaluation($aEvaluation))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfEvaluations()) { $index = $this->numberOfEvaluations() - 1; }
      array_splice($this->evaluations, $this->indexOfEvaluation($aEvaluation), 1);
      array_splice($this->evaluations, $index, 0, array($aEvaluation));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveEvaluationAt($aEvaluation, $index)
  {
    $wasAdded = false;
    if($this->indexOfEvaluation($aEvaluation) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfEvaluations()) { $index = $this->numberOfEvaluations() - 1; }
      array_splice($this->evaluations, $this->indexOfEvaluation($aEvaluation), 1);
      array_splice($this->evaluations, $index, 0, array($aEvaluation));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addEvaluationAt($aEvaluation, $index);
    }
    return $wasAdded;
  }

  public function setDepartment($aDepartment)
  {
    $wasSet = false;
    if ($aDepartment == null)
    {
      return $wasSet;
    }
    
    $existingDepartment = $this->department;
    $this->department = $aDepartment;
    if ($existingDepartment != null && $existingDepartment != $aDepartment)
    {
      $existingDepartment->removeStudent($this);
    }
    $this->department->addStudent($this);
    $wasSet = true;
    return $wasSet;
  }

  public static function minimumNumberOfJobApplications()
  {
    return 0;
  }

  public static function maximumNumberOfJobApplications()
  {
    return 3;
  }

  public function addJobApplicationVia($aEmailAddress, $aPastExperience, $aPreference, $aJobApplication)
  {
    if ($this->numberOfJobApplications() >= self::maximumNumberOfJobApplications())
    {
      return null;
    }
    else
    {
      return new Application($aEmailAddress, $aPastExperience, $aPreference, $aJobApplication, $this);
    }
  }

  public function addJobApplication($aJobApplication)
  {
    $wasAdded = false;
    if ($this->indexOfJobApplication($aJobApplication) !== -1) { return false; }
    if ($this->numberOfJobApplications() >= self::maximumNumberOfJobApplications())
    {
      return $wasAdded;
    }

    $existingStudent = $aJobApplication->getStudent();
    $isNewStudent = $existingStudent != null && $this !== $existingStudent;
    if ($isNewStudent)
    {
      $aJobApplication->setStudent($this);
    }
    else
    {
      $this->jobApplications[] = $aJobApplication;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeJobApplication($aJobApplication)
  {
    $wasRemoved = false;
    //Unable to remove aJobApplication, as it must always have a student
    if ($this !== $aJobApplication->getStudent())
    {
      unset($this->jobApplications[$this->indexOfJobApplication($aJobApplication)]);
      $this->jobApplications = array_values($this->jobApplications);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addJobApplicationAt($aJobApplication, $index)
  {  
    $wasAdded = false;
    if($this->addJobApplication($aJobApplication))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobApplications()) { $index = $this->numberOfJobApplications() - 1; }
      array_splice($this->jobApplications, $this->indexOfJobApplication($aJobApplication), 1);
      array_splice($this->jobApplications, $index, 0, array($aJobApplication));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveJobApplicationAt($aJobApplication, $index)
  {
    $wasAdded = false;
    if($this->indexOfJobApplication($aJobApplication) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobApplications()) { $index = $this->numberOfJobApplications() - 1; }
      array_splice($this->jobApplications, $this->indexOfJobApplication($aJobApplication), 1);
      array_splice($this->jobApplications, $index, 0, array($aJobApplication));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addJobApplicationAt($aJobApplication, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfRegistrations()
  {
    return 0;
  }

  public static function maximumNumberOfRegistrations()
  {
    return 3;
  }

  public function addRegistrationVia($aDateRegistered, $aJob)
  {
    if ($this->numberOfRegistrations() >= self::maximumNumberOfRegistrations())
    {
      return null;
    }
    else
    {
      return new Registration($aDateRegistered, $this, $aJob);
    }
  }

  public function addRegistration($aRegistration)
  {
    $wasAdded = false;
    if ($this->indexOfRegistration($aRegistration) !== -1) { return false; }
    if ($this->numberOfRegistrations() >= self::maximumNumberOfRegistrations())
    {
      return $wasAdded;
    }

    $existingStudent = $aRegistration->getStudent();
    $isNewStudent = $existingStudent != null && $this !== $existingStudent;
    if ($isNewStudent)
    {
      $aRegistration->setStudent($this);
    }
    else
    {
      $this->registrations[] = $aRegistration;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeRegistration($aRegistration)
  {
    $wasRemoved = false;
    //Unable to remove aRegistration, as it must always have a student
    if ($this !== $aRegistration->getStudent())
    {
      unset($this->registrations[$this->indexOfRegistration($aRegistration)]);
      $this->registrations = array_values($this->registrations);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addRegistrationAt($aRegistration, $index)
  {  
    $wasAdded = false;
    if($this->addRegistration($aRegistration))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfRegistrations()) { $index = $this->numberOfRegistrations() - 1; }
      array_splice($this->registrations, $this->indexOfRegistration($aRegistration), 1);
      array_splice($this->registrations, $index, 0, array($aRegistration));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveRegistrationAt($aRegistration, $index)
  {
    $wasAdded = false;
    if($this->indexOfRegistration($aRegistration) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfRegistrations()) { $index = $this->numberOfRegistrations() - 1; }
      array_splice($this->registrations, $this->indexOfRegistration($aRegistration), 1);
      array_splice($this->registrations, $index, 0, array($aRegistration));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addRegistrationAt($aRegistration, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    foreach ($this->evaluations as $aEvaluation)
    {
      $aEvaluation->delete();
    }
    $placeholderDepartment = $this->department;
    $this->department = null;
    $placeholderDepartment->removeStudent($this);
    foreach ($this->jobApplications as $aJobApplication)
    {
      $aJobApplication->delete();
    }
    foreach ($this->registrations as $aRegistration)
    {
      $aRegistration->delete();
    }
  }

}
?>