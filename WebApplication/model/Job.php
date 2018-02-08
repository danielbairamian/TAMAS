<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Job
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Job Attributes
  private $workHours;
  private $hourlyRate;
  private $startDate;
  private $endDate;

  //Job State Machines
  private static $JobStateOpen = 1;
  private static $JobStateFilled = 2;
  private static $JobStateOffered = 3;
  private static $JobStateInstructorApproved = 4;
  private static $JobStateDepartmentApproved = 5;
  private $JobState;

  private static $JobTypeTA = 1;
  private static $JobTypeGrader = 2;
  private $JobType;

  //Job Associations
  private $jobPosting;
  public $registration; //until PHP 5.3 (setAccessible)

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aWorkHours, $aHourlyRate, $aStartDate, $aEndDate, $aJobPosting)
  {
    $this->workHours = $aWorkHours;
    $this->hourlyRate = $aHourlyRate;
    $this->startDate = $aStartDate;
    $this->endDate = $aEndDate;
    $didAddJobPosting = $this->setJobPosting($aJobPosting);
    if (!$didAddJobPosting)
    {
      throw new Exception("Unable to create job due to jobPosting");
    }
    $this->setJobState(self::$JobStateOpen);
    $this->setJobType(self::$JobTypeTA);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setWorkHours($aWorkHours)
  {
    $wasSet = false;
    $this->workHours = $aWorkHours;
    $wasSet = true;
    return $wasSet;
  }

  public function setHourlyRate($aHourlyRate)
  {
    $wasSet = false;
    $this->hourlyRate = $aHourlyRate;
    $wasSet = true;
    return $wasSet;
  }

  public function setStartDate($aStartDate)
  {
    $wasSet = false;
    $this->startDate = $aStartDate;
    $wasSet = true;
    return $wasSet;
  }

  public function setEndDate($aEndDate)
  {
    $wasSet = false;
    $this->endDate = $aEndDate;
    $wasSet = true;
    return $wasSet;
  }

  public function getWorkHours()
  {
    return $this->workHours;
  }

  public function getHourlyRate()
  {
    return $this->hourlyRate;
  }

  public function getStartDate()
  {
    return $this->startDate;
  }

  public function getEndDate()
  {
    return $this->endDate;
  }

  public function getJobStateFullName()
  {
    $answer = $this->getJobState();
    return $answer;
  }

  public function getJobTypeFullName()
  {
    $answer = $this->getJobType();
    return $answer;
  }

  public function getJobState()
  {
    if ($this->JobState == self::$JobStateOpen) { return "JobStateOpen"; }
    elseif ($this->JobState == self::$JobStateFilled) { return "JobStateFilled"; }
    elseif ($this->JobState == self::$JobStateOffered) { return "JobStateOffered"; }
    elseif ($this->JobState == self::$JobStateInstructorApproved) { return "JobStateInstructorApproved"; }
    elseif ($this->JobState == self::$JobStateDepartmentApproved) { return "JobStateDepartmentApproved"; }
    return null;
  }

  public function getJobType()
  {
    if ($this->JobType == self::$JobTypeTA) { return "JobTypeTA"; }
    elseif ($this->JobType == self::$JobTypeGrader) { return "JobTypeGrader"; }
    return null;
  }

  public function setJobState($aJobState)
  {
    if ($aJobState == "JobStateOpen" || $aJobState == self::$JobStateOpen)
    {
      $this->JobState = self::$JobStateOpen;
      return true;
    }
    elseif ($aJobState == "JobStateFilled" || $aJobState == self::$JobStateFilled)
    {
      $this->JobState = self::$JobStateFilled;
      return true;
    }
    elseif ($aJobState == "JobStateOffered" || $aJobState == self::$JobStateOffered)
    {
      $this->JobState = self::$JobStateOffered;
      return true;
    }
    elseif ($aJobState == "JobStateInstructorApproved" || $aJobState == self::$JobStateInstructorApproved)
    {
      $this->JobState = self::$JobStateInstructorApproved;
      return true;
    }
    elseif ($aJobState == "JobStateDepartmentApproved" || $aJobState == self::$JobStateDepartmentApproved)
    {
      $this->JobState = self::$JobStateDepartmentApproved;
      return true;
    }
    else
    {
      return false;
    }
  }

  public function setJobType($aJobType)
  {
    if ($aJobType == "JobTypeTA" || $aJobType == self::$JobTypeTA)
    {
      $this->JobType = self::$JobTypeTA;
      return true;
    }
    elseif ($aJobType == "JobTypeGrader" || $aJobType == self::$JobTypeGrader)
    {
      $this->JobType = self::$JobTypeGrader;
      return true;
    }
    else
    {
      return false;
    }
  }

  public function getJobPosting()
  {
    return $this->jobPosting;
  }

  public function getRegistration()
  {
    return $this->registration;
  }

  public function hasRegistration()
  {
    $has = $this->registration != null;
    return $has;
  }

  public function setJobPosting($aJobPosting)
  {
    $wasSet = false;
    if ($aJobPosting == null)
    {
      return $wasSet;
    }
    
    $existingJobPosting = $this->jobPosting;
    $this->jobPosting = $aJobPosting;
    if ($existingJobPosting != null && $existingJobPosting != $aJobPosting)
    {
      $existingJobPosting->removeJob($this);
    }
    $this->jobPosting->addJob($this);
    $wasSet = true;
    return $wasSet;
  }

  public function setRegistration($aNewRegistration)
  {
    $wasSet = false;
    if ($this->registration != null && $this->registration != $aNewRegistration && $this == $this->registration->getJob())
    {
      //Unable to setRegistration, as existing registration would become an orphan
      return $wasSet;
    }
    
    $this->registration = $aNewRegistration;
    $anOldJob = $aNewRegistration != null ? $aNewRegistration->getJob() : null;
    
    if ($this != $anOldJob)
    {
      if ($anOldJob != null)
      {
        $anOldJob->registration = null;
      }
      if ($this->registration != null)
      {
        $this->registration->setJob($this);
      }
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
    $placeholderJobPosting = $this->jobPosting;
    $this->jobPosting = null;
    $placeholderJobPosting->removeJob($this);
    $existingRegistration = $this->registration;
    $this->registration = null;
    if ($existingRegistration != null)
    {
      $existingRegistration->delete();
    }
  }

}
?>