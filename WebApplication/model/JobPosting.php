<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class JobPosting
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //JobPosting Attributes
  private $deadline;
  private $startDate;
  private $endDate;
  private $description;

  //JobPosting State Machines
  private static $JobPostingStateActive = 1;
  private static $JobPostingStateDeleted = 2;
  private $JobPostingState;

  //JobPosting Associations
  private $jobs;
  private $instructor;
  private $forms;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aDeadline, $aStartDate, $aEndDate, $aDescription, $aInstructor)
  {
    $this->deadline = $aDeadline;
    $this->startDate = $aStartDate;
    $this->endDate = $aEndDate;
    $this->description = $aDescription;
    $this->jobs = array();
    $didAddInstructor = $this->setInstructor($aInstructor);
    if (!$didAddInstructor)
    {
      throw new Exception("Unable to create jobOffering due to instructor");
    }
    $this->forms = array();
    $this->setJobPostingState(self::$JobPostingStateActive);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setDeadline($aDeadline)
  {
    $wasSet = false;
    $this->deadline = $aDeadline;
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

  public function setDescription($aDescription)
  {
    $wasSet = false;
    $this->description = $aDescription;
    $wasSet = true;
    return $wasSet;
  }

  public function getDeadline()
  {
    return $this->deadline;
  }

  public function getStartDate()
  {
    return $this->startDate;
  }

  public function getEndDate()
  {
    return $this->endDate;
  }

  public function getDescription()
  {
    return $this->description;
  }

  public function getJobPostingStateFullName()
  {
    $answer = $this->getJobPostingState();
    return $answer;
  }

  public function getJobPostingState()
  {
    if ($this->JobPostingState == self::$JobPostingStateActive) { return "JobPostingStateActive"; }
    elseif ($this->JobPostingState == self::$JobPostingStateDeleted) { return "JobPostingStateDeleted"; }
    return null;
  }

  public function setJobPostingState($aJobPostingState)
  {
    if ($aJobPostingState == "JobPostingStateActive" || $aJobPostingState == self::$JobPostingStateActive)
    {
      $this->JobPostingState = self::$JobPostingStateActive;
      return true;
    }
    elseif ($aJobPostingState == "JobPostingStateDeleted" || $aJobPostingState == self::$JobPostingStateDeleted)
    {
      $this->JobPostingState = self::$JobPostingStateDeleted;
      return true;
    }
    else
    {
      return false;
    }
  }

  public function getJob_index($index)
  {
    $aJob = $this->jobs[$index];
    return $aJob;
  }

  public function getJobs()
  {
    $newJobs = $this->jobs;
    return $newJobs;
  }

  public function numberOfJobs()
  {
    $number = count($this->jobs);
    return $number;
  }

  public function hasJobs()
  {
    $has = $this->numberOfJobs() > 0;
    return $has;
  }

  public function indexOfJob($aJob)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->jobs as $job)
    {
      if ($job->equals($aJob))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getInstructor()
  {
    return $this->instructor;
  }

  public function getForm_index($index)
  {
    $aForm = $this->forms[$index];
    return $aForm;
  }

  public function getForms()
  {
    $newForms = $this->forms;
    return $newForms;
  }

  public function numberOfForms()
  {
    $number = count($this->forms);
    return $number;
  }

  public function hasForms()
  {
    $has = $this->numberOfForms() > 0;
    return $has;
  }

  public function indexOfForm($aForm)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->forms as $form)
    {
      if ($form->equals($aForm))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public static function minimumNumberOfJobs()
  {
    return 0;
  }

  public function addJobVia($aWorkHours, $aHourlyRate, $aStartDate, $aEndDate)
  {
    return new Job($aWorkHours, $aHourlyRate, $aStartDate, $aEndDate, $this);
  }

  public function addJob($aJob)
  {
    $wasAdded = false;
    if ($this->indexOfJob($aJob) !== -1) { return false; }
    $existingJobPosting = $aJob->getJobPosting();
    $isNewJobPosting = $existingJobPosting != null && $this !== $existingJobPosting;
    if ($isNewJobPosting)
    {
      $aJob->setJobPosting($this);
    }
    else
    {
      $this->jobs[] = $aJob;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeJob($aJob)
  {
    $wasRemoved = false;
    //Unable to remove aJob, as it must always have a jobPosting
    if ($this !== $aJob->getJobPosting())
    {
      unset($this->jobs[$this->indexOfJob($aJob)]);
      $this->jobs = array_values($this->jobs);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addJobAt($aJob, $index)
  {  
    $wasAdded = false;
    if($this->addJob($aJob))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobs()) { $index = $this->numberOfJobs() - 1; }
      array_splice($this->jobs, $this->indexOfJob($aJob), 1);
      array_splice($this->jobs, $index, 0, array($aJob));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveJobAt($aJob, $index)
  {
    $wasAdded = false;
    if($this->indexOfJob($aJob) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobs()) { $index = $this->numberOfJobs() - 1; }
      array_splice($this->jobs, $this->indexOfJob($aJob), 1);
      array_splice($this->jobs, $index, 0, array($aJob));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addJobAt($aJob, $index);
    }
    return $wasAdded;
  }

  public function setInstructor($aInstructor)
  {
    $wasSet = false;
    if ($aInstructor == null)
    {
      return $wasSet;
    }
    
    $existingInstructor = $this->instructor;
    $this->instructor = $aInstructor;
    if ($existingInstructor != null && $existingInstructor != $aInstructor)
    {
      $existingInstructor->removeJobOffering($this);
    }
    $this->instructor->addJobOffering($this);
    $wasSet = true;
    return $wasSet;
  }

  public static function minimumNumberOfForms()
  {
    return 0;
  }

  public function addFormVia($aEmailAddress, $aPastExperience, $aPreference, $aStudent)
  {
    return new Application($aEmailAddress, $aPastExperience, $aPreference, $this, $aStudent);
  }

  public function addForm($aForm)
  {
    $wasAdded = false;
    if ($this->indexOfForm($aForm) !== -1) { return false; }
    $existingJobApplication = $aForm->getJobApplication();
    $isNewJobApplication = $existingJobApplication != null && $this !== $existingJobApplication;
    if ($isNewJobApplication)
    {
      $aForm->setJobApplication($this);
    }
    else
    {
      $this->forms[] = $aForm;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeForm($aForm)
  {
    $wasRemoved = false;
    //Unable to remove aForm, as it must always have a jobApplication
    if ($this !== $aForm->getJobApplication())
    {
      unset($this->forms[$this->indexOfForm($aForm)]);
      $this->forms = array_values($this->forms);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addFormAt($aForm, $index)
  {  
    $wasAdded = false;
    if($this->addForm($aForm))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfForms()) { $index = $this->numberOfForms() - 1; }
      array_splice($this->forms, $this->indexOfForm($aForm), 1);
      array_splice($this->forms, $index, 0, array($aForm));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveFormAt($aForm, $index)
  {
    $wasAdded = false;
    if($this->indexOfForm($aForm) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfForms()) { $index = $this->numberOfForms() - 1; }
      array_splice($this->forms, $this->indexOfForm($aForm), 1);
      array_splice($this->forms, $index, 0, array($aForm));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addFormAt($aForm, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    while (count($this->jobs) > 0)
    {
      $aJob = $this->jobs[count($this->jobs) - 1];
      $aJob->delete();
      unset($this->jobs[$this->indexOfJob($aJob)]);
      $this->jobs = array_values($this->jobs);
    }
    
    $placeholderInstructor = $this->instructor;
    $this->instructor = null;
    $placeholderInstructor->removeJobOffering($this);
    foreach ($this->forms as $aForm)
    {
      $aForm->delete();
    }
  }

}
?>