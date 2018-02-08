<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Course
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $coursesById = array();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Course Attributes
  private $budgetGiven;

  /**
   * derive from numTAs, hours, wage, etc
   */
  private $budgetActual;
  private $id;
  private $numLabs;
  private $numTutorials;

  //Course Associations
  public $instructor; //until PHP 5.3 (setAccessible)
  private $department;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aBudgetGiven, $aId, $aNumLabs, $aNumTutorials, $aDepartment)
  {
    $this->budgetGiven = $aBudgetGiven;
    $this->budgetActual = 0;
    $this->numLabs = $aNumLabs;
    $this->numTutorials = $aNumTutorials;
    if (!$this->setId($aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id");
    }
    $didAddDepartment = $this->setDepartment($aDepartment);
    if (!$didAddDepartment)
    {
      throw new Exception("Unable to create course due to department");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setBudgetGiven($aBudgetGiven)
  {
    $wasSet = false;
    $this->budgetGiven = $aBudgetGiven;
    $wasSet = true;
    return $wasSet;
  }

  public function setBudgetActual($aBudgetActual)
  {
    $wasSet = false;
    $this->budgetActual = $aBudgetActual;
    $wasSet = true;
    return $wasSet;
  }

  public function setId($aId)
  {
    $wasSet = false;
    if (isset($this->id)) {
      $anOldId = $this->getId();
    }
    if (Course::hasWithId($aId)) {
      return $wasSet;
    }
    $this->id = $aId;
    $wasSet = true;
    if (isset($anOldId)) {
      unset(Course::$coursesById[(string)$anOldId]);
    }
    Course::$coursesById[(string)$aId] = $this;
    return $wasSet;
  }

  public function setNumLabs($aNumLabs)
  {
    $wasSet = false;
    $this->numLabs = $aNumLabs;
    $wasSet = true;
    return $wasSet;
  }

  public function setNumTutorials($aNumTutorials)
  {
    $wasSet = false;
    $this->numTutorials = $aNumTutorials;
    $wasSet = true;
    return $wasSet;
  }

  public function getBudgetGiven()
  {
    return $this->budgetGiven;
  }

  public function getBudgetActual()
  {
    return $this->budgetActual;
  }

  public function getId()
  {
    return $this->id;
  }

  public static function getWithId($aId)
  {
    return Course::$coursesById[(string)$aId];
  }

  public static function hasWithId($aId)
  {
    return array_key_exists((string)$aId, Course::$coursesById);
  }

  public function getNumLabs()
  {
    return $this->numLabs;
  }

  public function getNumTutorials()
  {
    return $this->numTutorials;
  }

  public function getInstructor()
  {
    return $this->instructor;
  }

  public function hasInstructor()
  {
    $has = $this->instructor != null;
    return $has;
  }

  public function getDepartment()
  {
    return $this->department;
  }

  public function setInstructor($aNewInstructor)
  {
    $wasSet = false;
    if ($this->instructor != null && $this->instructor != $aNewInstructor && $this == $this->instructor->getCourse())
    {
      //Unable to setInstructor, as existing instructor would become an orphan
      return $wasSet;
    }
    
    $this->instructor = $aNewInstructor;
    $anOldCourse = $aNewInstructor != null ? $aNewInstructor->getCourse() : null;
    
    if ($this != $anOldCourse)
    {
      if ($anOldCourse != null)
      {
        $anOldCourse->instructor = null;
      }
      if ($this->instructor != null)
      {
        $this->instructor->setCourse($this);
      }
    }
    $wasSet = true;
    return $wasSet;
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
      $existingDepartment->removeCourse($this);
    }
    $this->department->addCourse($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    unset(Course::$coursesById[(string)$this->getId()]);
    $existingInstructor = $this->instructor;
    $this->instructor = null;
    if ($existingInstructor != null)
    {
      $existingInstructor->delete();
    }
    $placeholderDepartment = $this->department;
    $this->department = null;
    $placeholderDepartment->removeCourse($this);
  }

}
?>