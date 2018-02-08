/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.util.*;

// line 51 "../../../../../TAMAS.ump"
public class Department
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Department Associations
  private Student currentStudent;
  private Instructor currentInstructor;
  private List<Student> students;
  private List<Course> courses;
  private List<Instructor> instructors;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Department()
  {
    students = new ArrayList<Student>();
    courses = new ArrayList<Course>();
    instructors = new ArrayList<Instructor>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Student getCurrentStudent()
  {
    return currentStudent;
  }

  public boolean hasCurrentStudent()
  {
    boolean has = currentStudent != null;
    return has;
  }

  public Instructor getCurrentInstructor()
  {
    return currentInstructor;
  }

  public boolean hasCurrentInstructor()
  {
    boolean has = currentInstructor != null;
    return has;
  }

  public Student getStudent(int index)
  {
    Student aStudent = students.get(index);
    return aStudent;
  }

  public List<Student> getStudents()
  {
    List<Student> newStudents = Collections.unmodifiableList(students);
    return newStudents;
  }

  public int numberOfStudents()
  {
    int number = students.size();
    return number;
  }

  public boolean hasStudents()
  {
    boolean has = students.size() > 0;
    return has;
  }

  public int indexOfStudent(Student aStudent)
  {
    int index = students.indexOf(aStudent);
    return index;
  }

  public Course getCourse(int index)
  {
    Course aCourse = courses.get(index);
    return aCourse;
  }

  public List<Course> getCourses()
  {
    List<Course> newCourses = Collections.unmodifiableList(courses);
    return newCourses;
  }

  public int numberOfCourses()
  {
    int number = courses.size();
    return number;
  }

  public boolean hasCourses()
  {
    boolean has = courses.size() > 0;
    return has;
  }

  public int indexOfCourse(Course aCourse)
  {
    int index = courses.indexOf(aCourse);
    return index;
  }

  public Instructor getInstructor(int index)
  {
    Instructor aInstructor = instructors.get(index);
    return aInstructor;
  }

  public List<Instructor> getInstructors()
  {
    List<Instructor> newInstructors = Collections.unmodifiableList(instructors);
    return newInstructors;
  }

  public int numberOfInstructors()
  {
    int number = instructors.size();
    return number;
  }

  public boolean hasInstructors()
  {
    boolean has = instructors.size() > 0;
    return has;
  }

  public int indexOfInstructor(Instructor aInstructor)
  {
    int index = instructors.indexOf(aInstructor);
    return index;
  }

  public boolean setCurrentStudent(Student aNewCurrentStudent)
  {
    boolean wasSet = false;
    currentStudent = aNewCurrentStudent;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentInstructor(Instructor aNewCurrentInstructor)
  {
    boolean wasSet = false;
    currentInstructor = aNewCurrentInstructor;
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfStudents()
  {
    return 0;
  }

  public Student addStudent(String aName)
  {
    return new Student(aName, this);
  }

  public boolean addStudent(Student aStudent)
  {
    boolean wasAdded = false;
    if (students.contains(aStudent)) { return false; }
    Department existingDepartment = aStudent.getDepartment();
    boolean isNewDepartment = existingDepartment != null && !this.equals(existingDepartment);
    if (isNewDepartment)
    {
      aStudent.setDepartment(this);
    }
    else
    {
      students.add(aStudent);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStudent(Student aStudent)
  {
    boolean wasRemoved = false;
    //Unable to remove aStudent, as it must always have a department
    if (!this.equals(aStudent.getDepartment()))
    {
      students.remove(aStudent);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addStudentAt(Student aStudent, int index)
  {  
    boolean wasAdded = false;
    if(addStudent(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudents()) { index = numberOfStudents() - 1; }
      students.remove(aStudent);
      students.add(index, aStudent);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStudentAt(Student aStudent, int index)
  {
    boolean wasAdded = false;
    if(students.contains(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudents()) { index = numberOfStudents() - 1; }
      students.remove(aStudent);
      students.add(index, aStudent);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStudentAt(aStudent, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfCourses()
  {
    return 0;
  }

  public Course addCourse(float aBudgetGiven, String aId, int aNumLabs, int aNumTutorials)
  {
    return new Course(aBudgetGiven, aId, aNumLabs, aNumTutorials, this);
  }

  public boolean addCourse(Course aCourse)
  {
    boolean wasAdded = false;
    if (courses.contains(aCourse)) { return false; }
    Department existingDepartment = aCourse.getDepartment();
    boolean isNewDepartment = existingDepartment != null && !this.equals(existingDepartment);
    if (isNewDepartment)
    {
      aCourse.setDepartment(this);
    }
    else
    {
      courses.add(aCourse);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCourse(Course aCourse)
  {
    boolean wasRemoved = false;
    //Unable to remove aCourse, as it must always have a department
    if (!this.equals(aCourse.getDepartment()))
    {
      courses.remove(aCourse);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addCourseAt(Course aCourse, int index)
  {  
    boolean wasAdded = false;
    if(addCourse(aCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourses()) { index = numberOfCourses() - 1; }
      courses.remove(aCourse);
      courses.add(index, aCourse);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCourseAt(Course aCourse, int index)
  {
    boolean wasAdded = false;
    if(courses.contains(aCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourses()) { index = numberOfCourses() - 1; }
      courses.remove(aCourse);
      courses.add(index, aCourse);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCourseAt(aCourse, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfInstructors()
  {
    return 0;
  }

  public Instructor addInstructor(String aName, Course aCourse)
  {
    return new Instructor(aName, aCourse, this);
  }

  public boolean addInstructor(Instructor aInstructor)
  {
    boolean wasAdded = false;
    if (instructors.contains(aInstructor)) { return false; }
    Department existingDepartment = aInstructor.getDepartment();
    boolean isNewDepartment = existingDepartment != null && !this.equals(existingDepartment);
    if (isNewDepartment)
    {
      aInstructor.setDepartment(this);
    }
    else
    {
      instructors.add(aInstructor);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInstructor(Instructor aInstructor)
  {
    boolean wasRemoved = false;
    //Unable to remove aInstructor, as it must always have a department
    if (!this.equals(aInstructor.getDepartment()))
    {
      instructors.remove(aInstructor);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addInstructorAt(Instructor aInstructor, int index)
  {  
    boolean wasAdded = false;
    if(addInstructor(aInstructor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInstructors()) { index = numberOfInstructors() - 1; }
      instructors.remove(aInstructor);
      instructors.add(index, aInstructor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInstructorAt(Instructor aInstructor, int index)
  {
    boolean wasAdded = false;
    if(instructors.contains(aInstructor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInstructors()) { index = numberOfInstructors() - 1; }
      instructors.remove(aInstructor);
      instructors.add(index, aInstructor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addInstructorAt(aInstructor, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    currentStudent = null;
    currentInstructor = null;
    while (students.size() > 0)
    {
      Student aStudent = students.get(students.size() - 1);
      aStudent.delete();
      students.remove(aStudent);
    }
    
    while (courses.size() > 0)
    {
      Course aCourse = courses.get(courses.size() - 1);
      aCourse.delete();
      courses.remove(aCourse);
    }
    
    while (instructors.size() > 0)
    {
      Instructor aInstructor = instructors.get(instructors.size() - 1);
      aInstructor.delete();
      instructors.remove(aInstructor);
    }
    
  }

}