/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.tamas.model;

// line 59 "../../../../../TAMAS.ump"
public class Application
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Application Attributes
  private String emailAddress;
  private String pastExperience;
  private int preference;

  //Application Associations
  private JobPosting jobApplication;
  private Student student;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Application(String aEmailAddress, String aPastExperience, int aPreference, JobPosting aJobApplication, Student aStudent)
  {
    emailAddress = aEmailAddress;
    pastExperience = aPastExperience;
    preference = aPreference;
    boolean didAddJobApplication = setJobApplication(aJobApplication);
    if (!didAddJobApplication)
    {
      throw new RuntimeException("Unable to create form due to jobApplication");
    }
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create jobApplication due to student");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPastExperience(String aPastExperience)
  {
    boolean wasSet = false;
    pastExperience = aPastExperience;
    wasSet = true;
    return wasSet;
  }

  public boolean setPreference(int aPreference)
  {
    boolean wasSet = false;
    preference = aPreference;
    wasSet = true;
    return wasSet;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }

  public String getPastExperience()
  {
    return pastExperience;
  }

  public int getPreference()
  {
    return preference;
  }

  public JobPosting getJobApplication()
  {
    return jobApplication;
  }

  public Student getStudent()
  {
    return student;
  }

  public boolean setJobApplication(JobPosting aJobApplication)
  {
    boolean wasSet = false;
    if (aJobApplication == null)
    {
      return wasSet;
    }

    JobPosting existingJobApplication = jobApplication;
    jobApplication = aJobApplication;
    if (existingJobApplication != null && !existingJobApplication.equals(aJobApplication))
    {
      existingJobApplication.removeForm(this);
    }
    jobApplication.addForm(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    //Must provide student to jobApplication
    if (aStudent == null)
    {
      return wasSet;
    }

    //student already at maximum (3)
    if (aStudent.numberOfJobApplications() >= Student.maximumNumberOfJobApplications())
    {
      return wasSet;
    }
    
    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      boolean didRemove = existingStudent.removeJobApplication(this);
      if (!didRemove)
      {
        student = existingStudent;
        return wasSet;
      }
    }
    student.addJobApplication(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    JobPosting placeholderJobApplication = jobApplication;
    this.jobApplication = null;
    placeholderJobApplication.removeForm(this);
    Student placeholderStudent = student;
    this.student = null;
    placeholderStudent.removeJobApplication(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "emailAddress" + ":" + getEmailAddress()+ "," +
            "pastExperience" + ":" + getPastExperience()+ "," +
            "preference" + ":" + getPreference()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "jobApplication = "+(getJobApplication()!=null?Integer.toHexString(System.identityHashCode(getJobApplication())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null")
     + outputString;
  }
}