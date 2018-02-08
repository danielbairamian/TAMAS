/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.util.*;
import java.sql.Date;

// line 34 "../../../../../TAMAS.ump"
public class Instructor
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Instructor Attributes
  private String name;

  //Autounique Attributes
  private int id;

  //Instructor Associations
  private List<JobPosting> jobOfferings;
  private Course course;
  private Department department;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Instructor(String aName, Course aCourse, Department aDepartment)
  {
    name = aName;
    id = nextId++;
    jobOfferings = new ArrayList<JobPosting>();
    boolean didAddCourse = setCourse(aCourse);
    if (!didAddCourse)
    {
      throw new RuntimeException("Unable to create instructor due to course");
    }
    boolean didAddDepartment = setDepartment(aDepartment);
    if (!didAddDepartment)
    {
      throw new RuntimeException("Unable to create instructor due to department");
    }
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

  public JobPosting getJobOffering(int index)
  {
    JobPosting aJobOffering = jobOfferings.get(index);
    return aJobOffering;
  }

  public List<JobPosting> getJobOfferings()
  {
    List<JobPosting> newJobOfferings = Collections.unmodifiableList(jobOfferings);
    return newJobOfferings;
  }

  public int numberOfJobOfferings()
  {
    int number = jobOfferings.size();
    return number;
  }

  public boolean hasJobOfferings()
  {
    boolean has = jobOfferings.size() > 0;
    return has;
  }

  public int indexOfJobOffering(JobPosting aJobOffering)
  {
    int index = jobOfferings.indexOf(aJobOffering);
    return index;
  }

  public Course getCourse()
  {
    return course;
  }

  public Department getDepartment()
  {
    return department;
  }

  public static int minimumNumberOfJobOfferings()
  {
    return 0;
  }

  public JobPosting addJobOffering(Date aDeadline, Date aStartDate, Date aEndDate, String aDescription)
  {
    return new JobPosting(aDeadline, aStartDate, aEndDate, aDescription, this);
  }

  public boolean addJobOffering(JobPosting aJobOffering)
  {
    boolean wasAdded = false;
    if (jobOfferings.contains(aJobOffering)) { return false; }
    Instructor existingInstructor = aJobOffering.getInstructor();
    boolean isNewInstructor = existingInstructor != null && !this.equals(existingInstructor);
    if (isNewInstructor)
    {
      aJobOffering.setInstructor(this);
    }
    else
    {
      jobOfferings.add(aJobOffering);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeJobOffering(JobPosting aJobOffering)
  {
    boolean wasRemoved = false;
    //Unable to remove aJobOffering, as it must always have a instructor
    if (!this.equals(aJobOffering.getInstructor()))
    {
      jobOfferings.remove(aJobOffering);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addJobOfferingAt(JobPosting aJobOffering, int index)
  {  
    boolean wasAdded = false;
    if(addJobOffering(aJobOffering))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobOfferings()) { index = numberOfJobOfferings() - 1; }
      jobOfferings.remove(aJobOffering);
      jobOfferings.add(index, aJobOffering);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveJobOfferingAt(JobPosting aJobOffering, int index)
  {
    boolean wasAdded = false;
    if(jobOfferings.contains(aJobOffering))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobOfferings()) { index = numberOfJobOfferings() - 1; }
      jobOfferings.remove(aJobOffering);
      jobOfferings.add(index, aJobOffering);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addJobOfferingAt(aJobOffering, index);
    }
    return wasAdded;
  }

  public boolean setCourse(Course aNewCourse)
  {
    boolean wasSet = false;
    if (aNewCourse == null)
    {
      //Unable to setCourse to null, as instructor must always be associated to a course
      return wasSet;
    }
    
    Instructor existingInstructor = aNewCourse.getInstructor();
    if (existingInstructor != null && !equals(existingInstructor))
    {
      //Unable to setCourse, the current course already has a instructor, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Course anOldCourse = course;
    course = aNewCourse;
    course.setInstructor(this);

    if (anOldCourse != null)
    {
      anOldCourse.setInstructor(null);
    }
    wasSet = true;
    return wasSet;
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
      existingDepartment.removeInstructor(this);
    }
    department.addInstructor(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=jobOfferings.size(); i > 0; i--)
    {
      JobPosting aJobOffering = jobOfferings.get(i - 1);
      aJobOffering.delete();
    }
    Course existingCourse = course;
    course = null;
    if (existingCourse != null)
    {
      existingCourse.setInstructor(null);
    }
    Department placeholderDepartment = department;
    this.department = null;
    placeholderDepartment.removeInstructor(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "course = "+(getCourse()!=null?Integer.toHexString(System.identityHashCode(getCourse())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "department = "+(getDepartment()!=null?Integer.toHexString(System.identityHashCode(getDepartment())):"null")
     + outputString;
  }
}