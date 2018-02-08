/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.sql.Date;

// line 13 "../../../../../TAMAS.ump"
public class Job
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Job Attributes
  private int workHours;
  private int hourlyRate;
  private Date startDate;
  private Date endDate;

  //Job State Machines
  public enum JobState { Open, Offered, Filled, InstructorApproved, DepartmentApproved }
  private JobState jobState;
  public enum JobType { TA, Grader }
  private JobType jobType;

  //Job Associations
  private JobPosting jobPosting;
  private Registration registration;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Job(int aWorkHours, int aHourlyRate, Date aStartDate, Date aEndDate, JobPosting aJobPosting)
  {
    workHours = aWorkHours;
    hourlyRate = aHourlyRate;
    startDate = aStartDate;
    endDate = aEndDate;
    boolean didAddJobPosting = setJobPosting(aJobPosting);
    if (!didAddJobPosting)
    {
      throw new RuntimeException("Unable to create job due to jobPosting");
    }
    setJobState(JobState.Open);
    setJobType(JobType.TA);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWorkHours(int aWorkHours)
  {
    boolean wasSet = false;
    workHours = aWorkHours;
    wasSet = true;
    return wasSet;
  }

  public boolean setHourlyRate(int aHourlyRate)
  {
    boolean wasSet = false;
    hourlyRate = aHourlyRate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public int getWorkHours()
  {
    return workHours;
  }

  public int getHourlyRate()
  {
    return hourlyRate;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public String getJobStateFullName()
  {
    String answer = jobState.toString();
    return answer;
  }

  public String getJobTypeFullName()
  {
    String answer = jobType.toString();
    return answer;
  }

  public JobState getJobState()
  {
    return jobState;
  }

  public JobType getJobType()
  {
    return jobType;
  }

  public boolean setJobState(JobState aJobState)
  {
    jobState = aJobState;
    return true;
  }

  public boolean setJobType(JobType aJobType)
  {
    jobType = aJobType;
    return true;
  }

  public JobPosting getJobPosting()
  {
    return jobPosting;
  }

  public Registration getRegistration()
  {
    return registration;
  }

  public boolean hasRegistration()
  {
    boolean has = registration != null;
    return has;
  }

  public boolean setJobPosting(JobPosting aJobPosting)
  {
    boolean wasSet = false;
    if (aJobPosting == null)
    {
      return wasSet;
    }

    JobPosting existingJobPosting = jobPosting;
    jobPosting = aJobPosting;
    if (existingJobPosting != null && !existingJobPosting.equals(aJobPosting))
    {
      existingJobPosting.removeJob(this);
    }
    jobPosting.addJob(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setRegistration(Registration aNewRegistration)
  {
    boolean wasSet = false;
    if (registration != null && !registration.equals(aNewRegistration) && equals(registration.getJob()))
    {
      //Unable to setRegistration, as existing registration would become an orphan
      return wasSet;
    }

    registration = aNewRegistration;
    Job anOldJob = aNewRegistration != null ? aNewRegistration.getJob() : null;

    if (!this.equals(anOldJob))
    {
      if (anOldJob != null)
      {
        anOldJob.registration = null;
      }
      if (registration != null)
      {
        registration.setJob(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    JobPosting placeholderJobPosting = jobPosting;
    this.jobPosting = null;
    placeholderJobPosting.removeJob(this);
    Registration existingRegistration = registration;
    registration = null;
    if (existingRegistration != null)
    {
      existingRegistration.delete();
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "workHours" + ":" + getWorkHours()+ "," +
            "hourlyRate" + ":" + getHourlyRate()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "jobPosting = "+(getJobPosting()!=null?Integer.toHexString(System.identityHashCode(getJobPosting())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "registration = "+(getRegistration()!=null?Integer.toHexString(System.identityHashCode(getRegistration())):"null")
     + outputString;
  }
}