<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Instructor
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Instructor Attributes
  private $name;

  //Autounique Attributes
  private $id;

  //Instructor Associations
  private $jobOfferings;
  private $course;
  private $department;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aCourse, $aDepartment)
  {
    $this->name = $aName;
    $this->id = self::$nextId++;
    $this->jobOfferings = array();
    $didAddCourse = $this->setCourse($aCourse);
    if (!$didAddCourse)
    {
      throw new Exception("Unable to create instructor due to course");
    }
    $didAddDepartment = $this->setDepartment($aDepartment);
    if (!$didAddDepartment)
    {
      throw new Exception("Unable to create instructor due to department");
    }
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

  public function getJobOffering_index($index)
  {
    $aJobOffering = $this->jobOfferings[$index];
    return $aJobOffering;
  }

  public function getJobOfferings()
  {
    $newJobOfferings = $this->jobOfferings;
    return $newJobOfferings;
  }

  public function numberOfJobOfferings()
  {
    $number = count($this->jobOfferings);
    return $number;
  }

  public function hasJobOfferings()
  {
    $has = $this->numberOfJobOfferings() > 0;
    return $has;
  }

  public function indexOfJobOffering($aJobOffering)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->jobOfferings as $jobOffering)
    {
      if ($jobOffering->equals($aJobOffering))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getCourse()
  {
    return $this->course;
  }

  public function getDepartment()
  {
    return $this->department;
  }

  public static function minimumNumberOfJobOfferings()
  {
    return 0;
  }

  public function addJobOfferingVia($aDeadline, $aStartDate, $aEndDate, $aDescription)
  {
    return new JobPosting($aDeadline, $aStartDate, $aEndDate, $aDescription, $this);
  }

  public function addJobOffering($aJobOffering)
  {
    $wasAdded = false;
    if ($this->indexOfJobOffering($aJobOffering) !== -1) { return false; }
    $existingInstructor = $aJobOffering->getInstructor();
    $isNewInstructor = $existingInstructor != null && $this !== $existingInstructor;
    if ($isNewInstructor)
    {
      $aJobOffering->setInstructor($this);
    }
    else
    {
      $this->jobOfferings[] = $aJobOffering;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeJobOffering($aJobOffering)
  {
    $wasRemoved = false;
    //Unable to remove aJobOffering, as it must always have a instructor
    if ($this !== $aJobOffering->getInstructor())
    {
      unset($this->jobOfferings[$this->indexOfJobOffering($aJobOffering)]);
      $this->jobOfferings = array_values($this->jobOfferings);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addJobOfferingAt($aJobOffering, $index)
  {  
    $wasAdded = false;
    if($this->addJobOffering($aJobOffering))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobOfferings()) { $index = $this->numberOfJobOfferings() - 1; }
      array_splice($this->jobOfferings, $this->indexOfJobOffering($aJobOffering), 1);
      array_splice($this->jobOfferings, $index, 0, array($aJobOffering));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveJobOfferingAt($aJobOffering, $index)
  {
    $wasAdded = false;
    if($this->indexOfJobOffering($aJobOffering) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobOfferings()) { $index = $this->numberOfJobOfferings() - 1; }
      array_splice($this->jobOfferings, $this->indexOfJobOffering($aJobOffering), 1);
      array_splice($this->jobOfferings, $index, 0, array($aJobOffering));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addJobOfferingAt($aJobOffering, $index);
    }
    return $wasAdded;
  }

  public function setCourse($aNewCourse)
  {
    $wasSet = false;
    if ($aNewCourse == null)
    {
      //Unable to setCourse to null, as instructor must always be associated to a course
      return $wasSet;
    }
    
    $existingInstructor = $aNewCourse->getInstructor();
    if ($existingInstructor != null && $this != $existingInstructor)
    {
      //Unable to setCourse, the current course already has a instructor, which would be orphaned if it were re-assigned
      return $wasSet;
    }
    
    $anOldCourse = $this->course;
    $this->course = $aNewCourse;
    $this->course->setInstructor($this);
    
    if ($anOldCourse != null)
    {
      $anOldCourse->setInstructor(null);
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
      $existingDepartment->removeInstructor($this);
    }
    $this->department->addInstructor($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
  	return $this->getId() == $compareTo->getId();
  }

  public function delete()
  {
    foreach ($this->jobOfferings as $aJobOffering)
    {
      $aJobOffering->delete();
    }
    $existingCourse = $this->course;
    $this->course = null;
    if ($existingCourse != null)
    {
      $existingCourse->setInstructor(null);
    }
    $placeholderDepartment = $this->department;
    $this->department = null;
    $placeholderDepartment->removeInstructor($this);
  }

}
?>