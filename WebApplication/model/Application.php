<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Application
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Application Attributes
  private $emailAddress;
  private $pastExperience;
  private $preference;

  //Application Associations
  private $jobApplication;
  private $student;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aEmailAddress, $aPastExperience, $aPreference, $aJobApplication, $aStudent)
  {
    $this->emailAddress = $aEmailAddress;
    $this->pastExperience = $aPastExperience;
    $this->preference = $aPreference;
    $didAddJobApplication = $this->setJobApplication($aJobApplication);
    if (!$didAddJobApplication)
    {
      throw new Exception("Unable to create form due to jobApplication");
    }
    $didAddStudent = $this->setStudent($aStudent);
    if (!$didAddStudent)
    {
      throw new Exception("Unable to create jobApplication due to student");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setEmailAddress($aEmailAddress)
  {
    $wasSet = false;
    $this->emailAddress = $aEmailAddress;
    $wasSet = true;
    return $wasSet;
  }

  public function setPastExperience($aPastExperience)
  {
    $wasSet = false;
    $this->pastExperience = $aPastExperience;
    $wasSet = true;
    return $wasSet;
  }

  public function setPreference($aPreference)
  {
    $wasSet = false;
    $this->preference = $aPreference;
    $wasSet = true;
    return $wasSet;
  }

  public function getEmailAddress()
  {
    return $this->emailAddress;
  }

  public function getPastExperience()
  {
    return $this->pastExperience;
  }

  public function getPreference()
  {
    return $this->preference;
  }

  public function getJobApplication()
  {
    return $this->jobApplication;
  }

  public function getStudent()
  {
    return $this->student;
  }

  public function setJobApplication($aJobApplication)
  {
    $wasSet = false;
    if ($aJobApplication == null)
    {
      return $wasSet;
    }
    
    $existingJobApplication = $this->jobApplication;
    $this->jobApplication = $aJobApplication;
    if ($existingJobApplication != null && $existingJobApplication != $aJobApplication)
    {
      $existingJobApplication->removeForm($this);
    }
    $this->jobApplication->addForm($this);
    $wasSet = true;
    return $wasSet;
  }

  public function setStudent($aStudent)
  {
    $wasSet = false;
    //Must provide student to jobApplication
    if ($aStudent == null)
    {
      return $wasSet;
    }

    //student already at maximum (3)
    if ($aStudent->numberOfJobApplications() >= Student::maximumNumberOfJobApplications())
    {
      return $wasSet;
    }
    
    $existingStudent = $this->student;
    $this->student = $aStudent;
    if ($existingStudent != null && $existingStudent != $aStudent)
    {
      $didRemove = $existingStudent->removeJobApplication($this);
      if (!$didRemove)
      {
        $this->student = $existingStudent;
        return $wasSet;
      }
    }
    $this->student->addJobApplication($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $placeholderJobApplication = $this->jobApplication;
    $this->jobApplication = null;
    $placeholderJobApplication->removeForm($this);
    $placeholderStudent = $this->student;
    $this->student = null;
    $placeholderStudent->removeJobApplication($this);
  }

}
?>