/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.util.*;
import java.sql.Date;

// line 24 "../../../../../TAMAS.ump"
public class Student
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Student Attributes
  private String name;

  //Autounique Attributes
  private int id;

  //Student State Machines
  public enum StudentLevel { Graduate, Undergraduate }
  private StudentLevel studentLevel;

  //Student Associations
  private List<Evaluation> evaluations;
  private Department department;
  private List<Application> jobApplications;
  private List<Registration> registrations;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Student(String aName, Department aDepartment)
  {
    name = aName;
    id = nextId++;
    evaluations = new ArrayList<Evaluation>();
    boolean didAddDepartment = setDepartment(aDepartment);
    if (!didAddDepartment)
    {
      throw new RuntimeException("Unable to create student due to department");
    }
    jobApplications = new ArrayList<Application>();
    registrations = new ArrayList<Registration>();
    setStudentLevel(StudentLevel.Graduate);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getId()
  {
    return id;
  }

  public String getStudentLevelFullName()
  {
    String answer = studentLevel.toString();
    return answer;
  }

  public StudentLevel getStudentLevel()
  {
    return studentLevel;
  }

  public boolean setStudentLevel(StudentLevel aStudentLevel)
  {
    studentLevel = aStudentLevel;
    return true;
  }

  public Evaluation getEvaluation(int index)
  {
    Evaluation aEvaluation = evaluations.get(index);
    return aEvaluation;
  }

  public List<Evaluation> getEvaluations()
  {
    List<Evaluation> newEvaluations = Collections.unmodifiableList(evaluations);
    return newEvaluations;
  }

  public int numberOfEvaluations()
  {
    int number = evaluations.size();
    return number;
  }

  public boolean hasEvaluations()
  {
    boolean has = evaluations.size() > 0;
    return has;
  }

  public int indexOfEvaluation(Evaluation aEvaluation)
  {
    int index = evaluations.indexOf(aEvaluation);
    return index;
  }

  public Department getDepartment()
  {
    return department;
  }

  public Application getJobApplication(int index)
  {
    Application aJobApplication = jobApplications.get(index);
    return aJobApplication;
  }

  public List<Application> getJobApplications()
  {
    List<Application> newJobApplications = Collections.unmodifiableList(jobApplications);
    return newJobApplications;
  }

  public int numberOfJobApplications()
  {
    int number = jobApplications.size();
    return number;
  }

  public boolean hasJobApplications()
  {
    boolean has = jobApplications.size() > 0;
    return has;
  }

  public int indexOfJobApplication(Application aJobApplication)
  {
    int index = jobApplications.indexOf(aJobApplication);
    return index;
  }

  public Registration getRegistration(int index)
  {
    Registration aRegistration = registrations.get(index);
    return aRegistration;
  }

  public List<Registration> getRegistrations()
  {
    List<Registration> newRegistrations = Collections.unmodifiableList(registrations);
    return newRegistrations;
  }

  public int numberOfRegistrations()
  {
    int number = registrations.size();
    return number;
  }

  public boolean hasRegistrations()
  {
    boolean has = registrations.size() > 0;
    return has;
  }

  public int indexOfRegistration(Registration aRegistration)
  {
    int index = registrations.indexOf(aRegistration);
    return index;
  }

  public static int minimumNumberOfEvaluations()
  {
    return 0;
  }

  public Evaluation addEvaluation(String aText, String aCourse, String aInstructorName)
  {
    return new Evaluation(aText, aCourse, aInstructorName, this);
  }

  public boolean addEvaluation(Evaluation aEvaluation)
  {
    boolean wasAdded = false;
    if (evaluations.contains(aEvaluation)) { return false; }
    Student existingStudent = aEvaluation.getStudent();
    boolean isNewStudent = existingStudent != null && !this.equals(existingStudent);
    if (isNewStudent)
    {
      aEvaluation.setStudent(this);
    }
    else
    {
      evaluations.add(aEvaluation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEvaluation(Evaluation aEvaluation)
  {
    boolean wasRemoved = false;
    //Unable to remove aEvaluation, as it must always have a student
    if (!this.equals(aEvaluation.getStudent()))
    {
      evaluations.remove(aEvaluation);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addEvaluationAt(Evaluation aEvaluation, int index)
  {  
    boolean wasAdded = false;
    if(addEvaluation(aEvaluation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEvaluations()) { index = numberOfEvaluations() - 1; }
      evaluations.remove(aEvaluation);
      evaluations.add(index, aEvaluation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEvaluationAt(Evaluation aEvaluation, int index)
  {
    boolean wasAdded = false;
    if(evaluations.contains(aEvaluation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEvaluations()) { index = numberOfEvaluations() - 1; }
      evaluations.remove(aEvaluation);
      evaluations.add(index, aEvaluation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEvaluationAt(aEvaluation, index);
    }
    return wasAdded;
  }

  public boolean setDepartment(Department aDepartment)
  {
    boolean wasSet = false;
    if (aDepartment == null)
    {
      return wasSet;
    }

    Department existingDepartment = department;
    department = aDepartment;
    if (existingDepartment != null && !existingDepartment.equals(aDepartment))
    {
      existingDepartment.removeStudent(this);
    }
    department.addStudent(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfJobApplications()
  {
    return 0;
  }

  public static int maximumNumberOfJobApplications()
  {
    return 3;
  }

  public Application addJobApplication(String aEmailAddress, String aPastExperience, int aPreference, JobPosting aJobApplication)
  {
    if (numberOfJobApplications() >= maximumNumberOfJobApplications())
    {
      return null;
    }
    else
    {
      return new Application(aEmailAddress, aPastExperience, aPreference, aJobApplication, this);
    }
  }

  public boolean addJobApplication(Application aJobApplication)
  {
    boolean wasAdded = false;
    if (jobApplications.contains(aJobApplication)) { return false; }
    if (numberOfJobApplications() >= maximumNumberOfJobApplications())
    {
      return wasAdded;
    }

    Student existingStudent = aJobApplication.getStudent();
    boolean isNewStudent = existingStudent != null && !this.equals(existingStudent);
    if (isNewStudent)
    {
      aJobApplication.setStudent(this);
    }
    else
    {
      jobApplications.add(aJobApplication);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeJobApplication(Application aJobApplication)
  {
    boolean wasRemoved = false;
    //Unable to remove aJobApplication, as it must always have a student
    if (!this.equals(aJobApplication.getStudent()))
    {
      jobApplications.remove(aJobApplication);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addJobApplicationAt(Application aJobApplication, int index)
  {  
    boolean wasAdded = false;
    if(addJobApplication(aJobApplication))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobApplications()) { index = numberOfJobApplications() - 1; }
      jobApplications.remove(aJobApplication);
      jobApplications.add(index, aJobApplication);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveJobApplicationAt(Application aJobApplication, int index)
  {
    boolean wasAdded = false;
    if(jobApplications.contains(aJobApplication))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobApplications()) { index = numberOfJobApplications() - 1; }
      jobApplications.remove(aJobApplication);
      jobApplications.add(index, aJobApplication);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addJobApplicationAt(aJobApplication, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfRegistrations()
  {
    return 0;
  }

  public static int maximumNumberOfRegistrations()
  {
    return 3;
  }

  public Registration addRegistration(Date aDateRegistered, Job aJob)
  {
    if (numberOfRegistrations() >= maximumNumberOfRegistrations())
    {
      return null;
    }
    else
    {
      return new Registration(aDateRegistered, this, aJob);
    }
  }

  public boolean addRegistration(Registration aRegistration)
  {
    boolean wasAdded = false;
    if (registrations.contains(aRegistration)) { return false; }
    if (numberOfRegistrations() >= maximumNumberOfRegistrations())
    {
      return wasAdded;
    }

    Student existingStudent = aRegistration.getStudent();
    boolean isNewStudent = existingStudent != null && !this.equals(existingStudent);
    if (isNewStudent)
    {
      aRegistration.setStudent(this);
    }
    else
    {
      registrations.add(aRegistration);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRegistration(Registration aRegistration)
  {
    boolean wasRemoved = false;
    //Unable to remove aRegistration, as it must always have a student
    if (!this.equals(aRegistration.getStudent()))
    {
      registrations.remove(aRegistration);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addRegistrationAt(Registration aRegistration, int index)
  {  
    boolean wasAdded = false;
    if(addRegistration(aRegistration))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRegistrations()) { index = numberOfRegistrations() - 1; }
      registrations.remove(aRegistration);
      registrations.add(index, aRegistration);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRegistrationAt(Registration aRegistration, int index)
  {
    boolean wasAdded = false;
    if(registrations.contains(aRegistration))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRegistrations()) { index = numberOfRegistrations() - 1; }
      registrations.remove(aRegistration);
      registrations.add(index, aRegistration);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRegistrationAt(aRegistration, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=evaluations.size(); i > 0; i--)
    {
      Evaluation aEvaluation = evaluations.get(i - 1);
      aEvaluation.delete();
    }
    Department placeholderDepartment = department;
    this.department = null;
    placeholderDepartment.removeStudent(this);
    for(int i=jobApplications.size(); i > 0; i--)
    {
      Application aJobApplication = jobApplications.get(i - 1);
      aJobApplication.delete();
    }
    for(int i=registrations.size(); i > 0; i--)
    {
      Registration aRegistration = registrations.get(i - 1);
      aRegistration.delete();
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "department = "+(getDepartment()!=null?Integer.toHexString(System.identityHashCode(getDepartment())):"null")
     + outputString;
  }
}