<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Evaluation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Evaluation Attributes
  private $text;
  private $course;
  private $instructorName;

  //Evaluation Associations
  private $student;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aText, $aCourse, $aInstructorName, $aStudent)
  {
    $this->text = $aText;
    $this->course = $aCourse;
    $this->instructorName = $aInstructorName;
    $didAddStudent = $this->setStudent($aStudent);
    if (!$didAddStudent)
    {
      throw new Exception("Unable to create evaluation due to student");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setText($aText)
  {
    $wasSet = false;
    $this->text = $aText;
    $wasSet = true;
    return $wasSet;
  }

  public function setCourse($aCourse)
  {
    $wasSet = false;
    $this->course = $aCourse;
    $wasSet = true;
    return $wasSet;
  }

  public function setInstructorName($aInstructorName)
  {
    $wasSet = false;
    $this->instructorName = $aInstructorName;
    $wasSet = true;
    return $wasSet;
  }

  public function getText()
  {
    return $this->text;
  }

  public function getCourse()
  {
    return $this->course;
  }

  public function getInstructorName()
  {
    return $this->instructorName;
  }

  public function getStudent()
  {
    return $this->student;
  }

  public function setStudent($aStudent)
  {
    $wasSet = false;
    if ($aStudent == null)
    {
      return $wasSet;
    }
    
    $existingStudent = $this->student;
    $this->student = $aStudent;
    if ($existingStudent != null && $existingStudent != $aStudent)
    {
      $existingStudent->removeEvaluation($this);
    }
    $this->student->addEvaluation($this);
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
    $placeholderStudent->removeEvaluation($this);
  }

}
?>