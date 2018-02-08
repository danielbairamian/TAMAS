<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Department
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Department Associations
  public $currentStudent; //until PHP 5.3 (setAccessible)
  public $currentInstructor; //until PHP 5.3 (setAccessible)
  private $students;
  private $courses;
  private $instructors;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct()
  {
    $this->students = array();
    $this->courses = array();
    $this->instructors = array();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getCurrentStudent()
  {
    return $this->currentStudent;
  }

  public function hasCurrentStudent()
  {
    $has = $this->currentStudent != null;
    return $has;
  }

  public function getCurrentInstructor()
  {
    return $this->currentInstructor;
  }

  public function hasCurrentInstructor()
  {
    $has = $this->currentInstructor != null;
    return $has;
  }

  public function getStudent_index($index)
  {
    $aStudent = $this->students[$index];
    return $aStudent;
  }

  public function getStudents()
  {
    $newStudents = $this->students;
    return $newStudents;
  }

  public function numberOfStudents()
  {
    $number = count($this->students);
    return $number;
  }

  public function hasStudents()
  {
    $has = $this->numberOfStudents() > 0;
    return $has;
  }

  public function indexOfStudent($aStudent)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->students as $student)
    {
      if ($student->equals($aStudent))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getCourse_index($index)
  {
    $aCourse = $this->courses[$index];
    return $aCourse;
  }

  public function getCourses()
  {
    $newCourses = $this->courses;
    return $newCourses;
  }

  public function numberOfCourses()
  {
    $number = count($this->courses);
    return $number;
  }

  public function hasCourses()
  {
    $has = $this->numberOfCourses() > 0;
    return $has;
  }

  public function indexOfCourse($aCourse)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->courses as $course)
    {
      if ($course->equals($aCourse))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getInstructor_index($index)
  {
    $aInstructor = $this->instructors[$index];
    return $aInstructor;
  }

  public function getInstructors()
  {
    $newInstructors = $this->instructors;
    return $newInstructors;
  }

  public function numberOfInstructors()
  {
    $number = count($this->instructors);
    return $number;
  }

  public function hasInstructors()
  {
    $has = $this->numberOfInstructors() > 0;
    return $has;
  }

  public function indexOfInstructor($aInstructor)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->instructors as $instructor)
    {
      if ($instructor->equals($aInstructor))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function setCurrentStudent($aNewCurrentStudent)
  {
    $wasSet = false;
    $this->currentStudent = $aNewCurrentStudent;
    $wasSet = true;
    return $wasSet;
  }

  public function setCurrentInstructor($aNewCurrentInstructor)
  {
    $wasSet = false;
    $this->currentInstructor = $aNewCurrentInstructor;
    $wasSet = true;
    return $wasSet;
  }

  public static function minimumNumberOfStudents()
  {
    return 0;
  }

  public function addStudentVia($aName)
  {
    return new Student($aName, $this);
  }

  public function addStudent($aStudent)
  {
    $wasAdded = false;
    if ($this->indexOfStudent($aStudent) !== -1) { return false; }
    $existingDepartment = $aStudent->getDepartment();
    $isNewDepartment = $existingDepartment != null && $this !== $existingDepartment;
    if ($isNewDepartment)
    {
      $aStudent->setDepartment($this);
    }
    else
    {
      $this->students[] = $aStudent;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeStudent($aStudent)
  {
    $wasRemoved = false;
    //Unable to remove aStudent, as it must always have a department
    if ($this !== $aStudent->getDepartment())
    {
      unset($this->students[$this->indexOfStudent($aStudent)]);
      $this->students = array_values($this->students);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addStudentAt($aStudent, $index)
  {  
    $wasAdded = false;
    if($this->addStudent($aStudent))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfStudents()) { $index = $this->numberOfStudents() - 1; }
      array_splice($this->students, $this->indexOfStudent($aStudent), 1);
      array_splice($this->students, $index, 0, array($aStudent));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveStudentAt($aStudent, $index)
  {
    $wasAdded = false;
    if($this->indexOfStudent($aStudent) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfStudents()) { $index = $this->numberOfStudents() - 1; }
      array_splice($this->students, $this->indexOfStudent($aStudent), 1);
      array_splice($this->students, $index, 0, array($aStudent));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addStudentAt($aStudent, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfCourses()
  {
    return 0;
  }

  public function addCourseVia($aBudgetGiven, $aId, $aNumLabs, $aNumTutorials)
  {
    return new Course($aBudgetGiven, $aId, $aNumLabs, $aNumTutorials, $this);
  }

  public function addCourse($aCourse)
  {
    $wasAdded = false;
    if ($this->indexOfCourse($aCourse) !== -1) { return false; }
    $existingDepartment = $aCourse->getDepartment();
    $isNewDepartment = $existingDepartment != null && $this !== $existingDepartment;
    if ($isNewDepartment)
    {
      $aCourse->setDepartment($this);
    }
    else
    {
      $this->courses[] = $aCourse;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeCourse($aCourse)
  {
    $wasRemoved = false;
    //Unable to remove aCourse, as it must always have a department
    if ($this !== $aCourse->getDepartment())
    {
      unset($this->courses[$this->indexOfCourse($aCourse)]);
      $this->courses = array_values($this->courses);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addCourseAt($aCourse, $index)
  {  
    $wasAdded = false;
    if($this->addCourse($aCourse))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfCourses()) { $index = $this->numberOfCourses() - 1; }
      array_splice($this->courses, $this->indexOfCourse($aCourse), 1);
      array_splice($this->courses, $index, 0, array($aCourse));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveCourseAt($aCourse, $index)
  {
    $wasAdded = false;
    if($this->indexOfCourse($aCourse) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfCourses()) { $index = $this->numberOfCourses() - 1; }
      array_splice($this->courses, $this->indexOfCourse($aCourse), 1);
      array_splice($this->courses, $index, 0, array($aCourse));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addCourseAt($aCourse, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfInstructors()
  {
    return 0;
  }

  public function addInstructorVia($aName, $aCourse)
  {
    return new Instructor($aName, $aCourse, $this);
  }

  public function addInstructor($aInstructor)
  {
    $wasAdded = false;
    if ($this->indexOfInstructor($aInstructor) !== -1) { return false; }
    $existingDepartment = $aInstructor->getDepartment();
    $isNewDepartment = $existingDepartment != null && $this !== $existingDepartment;
    if ($isNewDepartment)
    {
      $aInstructor->setDepartment($this);
    }
    else
    {
      $this->instructors[] = $aInstructor;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeInstructor($aInstructor)
  {
    $wasRemoved = false;
    //Unable to remove aInstructor, as it must always have a department
    if ($this !== $aInstructor->getDepartment())
    {
      unset($this->instructors[$this->indexOfInstructor($aInstructor)]);
      $this->instructors = array_values($this->instructors);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addInstructorAt($aInstructor, $index)
  {  
    $wasAdded = false;
    if($this->addInstructor($aInstructor))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfInstructors()) { $index = $this->numberOfInstructors() - 1; }
      array_splice($this->instructors, $this->indexOfInstructor($aInstructor), 1);
      array_splice($this->instructors, $index, 0, array($aInstructor));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveInstructorAt($aInstructor, $index)
  {
    $wasAdded = false;
    if($this->indexOfInstructor($aInstructor) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfInstructors()) { $index = $this->numberOfInstructors() - 1; }
      array_splice($this->instructors, $this->indexOfInstructor($aInstructor), 1);
      array_splice($this->instructors, $index, 0, array($aInstructor));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addInstructorAt($aInstructor, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->currentStudent = null;
    $this->currentInstructor = null;
    while (count($this->students) > 0)
    {
      $aStudent = $this->students[count($this->students) - 1];
      $aStudent->delete();
      unset($this->students[$this->indexOfStudent($aStudent)]);
      $this->students = array_values($this->students);
    }
    
    while (count($this->courses) > 0)
    {
      $aCourse = $this->courses[count($this->courses) - 1];
      $aCourse->delete();
      unset($this->courses[$this->indexOfCourse($aCourse)]);
      $this->courses = array_values($this->courses);
    }
    
    while (count($this->instructors) > 0)
    {
      $aInstructor = $this->instructors[count($this->instructors) - 1];
      $aInstructor->delete();
      unset($this->instructors[$this->indexOfInstructor($aInstructor)]);
      $this->instructors = array_values($this->instructors);
    }
    
  }

}
?>