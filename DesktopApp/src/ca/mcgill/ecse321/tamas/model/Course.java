/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.util.*;

// line 41 "../../../../../TAMAS.ump"
public class Course
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Course> coursesById = new HashMap<String, Course>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Course Attributes
  private float budgetGiven;
  private float budgetActual;
  private String id;
  private int numLabs;
  private int numTutorials;

  //Course Associations
  private Instructor instructor;
  private Department department;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Course(float aBudgetGiven, String aId, int aNumLabs, int aNumTutorials, Department aDepartment)
  {
    budgetGiven = aBudgetGiven;
    budgetActual = 0.0f;
    numLabs = aNumLabs;
    numTutorials = aNumTutorials;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id");
    }
    boolean didAddDepartment = setDepartment(aDepartment);
    if (!didAddDepartment)
    {
      throw new RuntimeException("Unable to create course due to department");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBudgetGiven(float aBudgetGiven)
  {
    boolean wasSet = false;
    budgetGiven = aBudgetGiven;
    wasSet = true;
    return wasSet;
  }

  public boolean setBudgetActual(float aBudgetActual)
  {
    boolean wasSet = false;
    budgetActual = aBudgetActual;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    String anOldId = getId();
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      coursesById.remove(anOldId);
    }
    coursesById.put(aId, this);
    return wasSet;
  }

  public boolean setNumLabs(int aNumLabs)
  {
    boolean wasSet = false;
    numLabs = aNumLabs;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumTutorials(int aNumTutorials)
  {
    boolean wasSet = false;
    numTutorials = aNumTutorials;
    wasSet = true;
    return wasSet;
  }

  public float getBudgetGiven()
  {
    return budgetGiven;
  }

  /**
   * derive from numTAs, hours, wage, etc
   */
  public float getBudgetActual()
  {
    return budgetActual;
  }

  public String getId()
  {
    return id;
  }

  public static Course getWithId(String aId)
  {
    return coursesById.get(aId);
  }

  public static boolean hasWithId(String aId)
  {
    return getWithId(aId) != null;
  }

  public int getNumLabs()
  {
    return numLabs;
  }

  public int getNumTutorials()
  {
    return numTutorials;
  }

  public Instructor getInstructor()
  {
    return instructor;
  }

  public boolean hasInstructor()
  {
    boolean has = instructor != null;
    return has;
  }

  public Department getDepartment()
  {
    return department;
  }

  public boolean setInstructor(Instructor aNewInstructor)
  {
    boolean wasSet = false;
    if (instructor != null && !instructor.equals(aNewInstructor) && equals(instructor.getCourse()))
    {
      //Unable to setInstructor, as existing instructor would become an orphan
      return wasSet;
    }

    instructor = aNewInstructor;
    Course anOldCourse = aNewInstructor != null ? aNewInstructor.getCourse() : null;

    if (!this.equals(anOldCourse))
    {
      if (anOldCourse != null)
      {
        anOldCourse.instructor = null;
      }
      if (instructor != null)
      {
        instructor.setCourse(this);
      }
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
      existingDepartment.removeCourse(this);
    }
    department.addCourse(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    coursesById.remove(getId());
    Instructor existingInstructor = instructor;
    instructor = null;
    if (existingInstructor != null)
    {
      existingInstructor.delete();
    }
    Department placeholderDepartment = department;
    this.department = null;
    placeholderDepartment.removeCourse(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "budgetGiven" + ":" + getBudgetGiven()+ "," +
            "budgetActual" + ":" + getBudgetActual()+ "," +
            "id" + ":" + getId()+ "," +
            "numLabs" + ":" + getNumLabs()+ "," +
            "numTutorials" + ":" + getNumTutorials()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "instructor = "+(getInstructor()!=null?Integer.toHexString(System.identityHashCode(getInstructor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "department = "+(getDepartment()!=null?Integer.toHexString(System.identityHashCode(getDepartment())):"null")
     + outputString;
  }
}