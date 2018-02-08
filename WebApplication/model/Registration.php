<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Registration
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Registration Attributes
  private $dateRegistered;

  //Registration Associations
  private $student;
  private $job;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aDateRegistered, $aStudent, $aJob)
  {
    $this->dateRegistered = $aDateRegistered;
    $didAddStudent = $this->setStudent($aStudent);
    if (!$didAddStudent)
    {
      throw new Exception("Unable to create registration due to student");
    }
    $didAddJob = $this->setJob($aJob);
    if (!$didAddJob)
    {
      throw new Exception("Unable to create registration due to job");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setDateRegistered($aDateRegistered)
  {
    $wasSet = false;
    $this->dateRegistered = $aDateRegistered;
    $wasSet = true;
    return $wasSet;
  }

  public function getDateRegistered()
  {
    return $this->dateRegistered;
  }

  public function getStudent()
  {
    return $this->student;
  }

  public function getJob()
  {
    return $this->job;
  }

  public function setStudent($aStudent)
  {
    $wasSet = false;
    //Must provide student to registration
    if ($aStudent == null)
    {
      return $wasSet;
    }

    //student already at maximum (3)
    if ($aStudent->numberOfRegistrations() >= Student::maximumNumberOfRegistrations())
    {
      return $wasSet;
    }
    
    $existingStudent = $this->student;
    $this->student = $aStudent;
    if ($existingStudent != null && $existingStudent != $aStudent)
    {
      $didRemove = $existingStudent->removeRegistration($this);
      if (!$didRemove)
      {
        $this->student = $existingStudent;
        return $wasSet;
      }
    }
    $this->student->addRegistration($this);
    $wasSet = true;
    return $wasSet;
  }

  public function setJob($aNewJob)
  {
    $wasSet = false;
    if ($aNewJob == null)
    {
      //Unable to setJob to null, as registration must always be associated to a job
      return $wasSet;
    }
    
    $existingRegistration = $aNewJob->getRegistration();
    if ($existingRegistration != null && $this != $existingRegistration)
    {
      //Unable to setJob, the current job already has a registration, which would be orphaned if it were re-assigned
      return $wasSet;
    }
    
    $anOldJob = $this->job;
    $this->job = $aNewJob;
    $this->job->setRegistration($this);
    
    if ($anOldJob != null)
    {
      $anOldJob->setRegistration(null);
    }
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $placeholderStudent = $this->student;
    $this->student = null;
    $placeholderStudent->removeRegistration($this);
    $existingJob = $this->job;
    $this->job = null;
    if ($existingJob != null)
    {
      $existingJob->setRegistration(null);
    }
  }

}
?>