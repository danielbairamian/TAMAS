/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.sql.Date;

// line 75 "../../../../../TAMAS.ump"
public class Registration
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Registration Attributes
  private Date dateRegistered;

  //Registration Associations
  private Student student;
  private Job job;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Registration(Date aDateRegistered, Student aStudent, Job aJob)
  {
    dateRegistered = aDateRegistered;
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create registration due to student");
    }
    boolean didAddJob = setJob(aJob);
    if (!didAddJob)
    {
      throw new RuntimeException("Unable to create registration due to job");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDateRegistered(Date aDateRegistered)
  {
    boolean wasSet = false;
    dateRegistered = aDateRegistered;
    wasSet = true;
    return wasSet;
  }

  public Date getDateRegistered()
  {
    return dateRegistered;
  }

  public Student getStudent()
  {
    return student;
  }

  public Job getJob()
  {
    return job;
  }

  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    //Must provide student to registration
    if (aStudent == null)
    {
      return wasSet;
    }

    //student already at maximum (3)
    if (aStudent.numberOfRegistrations() >= Student.maximumNumberOfRegistrations())
    {
      return wasSet;
    }
    
    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      boolean didRemove = existingStudent.removeRegistration(this);
      if (!didRemove)
      {
        student = existingStudent;
        return wasSet;
      }
    }
    student.addRegistration(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setJob(Job aNewJob)
  {
    boolean wasSet = false;
    if (aNewJob == null)
    {
      //Unable to setJob to null, as registration must always be associated to a job
      return wasSet;
    }
    
    Registration existingRegistration = aNewJob.getRegistration();
    if (existingRegistration != null && !equals(existingRegistration))
    {
      //Unable to setJob, the current job already has a registration, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Job anOldJob = job;
    job = aNewJob;
    job.setRegistration(this);

    if (anOldJob != null)
    {
      anOldJob.setRegistration(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Student placeholderStudent = student;
    this.student = null;
    if(placeholderStudent != null){
    	placeholderStudent.removeRegistration(this);
    }
    Job existingJob = job;
    job = null;
    if (existingJob != null)
    {
      existingJob.setRegistration(null);
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dateRegistered" + "=" + (getDateRegistered() != null ? !getDateRegistered().equals(this)  ? getDateRegistered().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "job = "+(getJob()!=null?Integer.toHexString(System.identityHashCode(getJob())):"null")
     + outputString;
  }
}