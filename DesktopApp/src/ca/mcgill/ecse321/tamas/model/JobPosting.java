/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.tamas.model;
import java.sql.Date;
import java.util.*;

// line 3 "../../../../../TAMAS.ump"
public class JobPosting
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //JobPosting Attributes
  private Date deadline;
  private Date startDate;
  private Date endDate;
  private String description;

  //JobPosting State Machines
  public enum JobPostingState { Active, Deleted }
  private JobPostingState jobPostingState;

  //JobPosting Associations
  private List<Job> jobs;
  private Instructor instructor;
  private List<Application> forms;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public JobPosting(Date aDeadline, Date aStartDate, Date aEndDate, String aDescription, Instructor aInstructor)
  {
    deadline = aDeadline;
    startDate = aStartDate;
    endDate = aEndDate;
    description = aDescription;
    jobs = new ArrayList<Job>();
    boolean didAddInstructor = setInstructor(aInstructor);
    if (!didAddInstructor)
    {
      throw new RuntimeException("Unable to create jobOffering due to instructor");
    }
    forms = new ArrayList<Application>();
    setJobPostingState(JobPostingState.Active);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDeadline(Date aDeadline)
  {
    boolean wasSet = false;
    deadline = aDeadline;
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

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public Date getDeadline()
  {
    return deadline;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public String getDescription()
  {
    return description;
  }

  public String getJobPostingStateFullName()
  {
    String answer = jobPostingState.toString();
    return answer;
  }

  public JobPostingState getJobPostingState()
  {
    return jobPostingState;
  }

  public boolean setJobPostingState(JobPostingState aJobPostingState)
  {
    jobPostingState = aJobPostingState;
    return true;
  }

  public Job getJob(int index)
  {
    Job aJob = jobs.get(index);
    return aJob;
  }

  public List<Job> getJobs()
  {
    List<Job> newJobs = Collections.unmodifiableList(jobs);
    return newJobs;
  }

  public int numberOfJobs()
  {
    int number = jobs.size();
    return number;
  }

  public boolean hasJobs()
  {
    boolean has = jobs.size() > 0;
    return has;
  }

  public int indexOfJob(Job aJob)
  {
    int index = jobs.indexOf(aJob);
    return index;
  }

  public Instructor getInstructor()
  {
    return instructor;
  }

  public Application getForm(int index)
  {
    Application aForm = forms.get(index);
    return aForm;
  }

  public List<Application> getForms()
  {
    List<Application> newForms = Collections.unmodifiableList(forms);
    return newForms;
  }

  public int numberOfForms()
  {
    int number = forms.size();
    return number;
  }

  public boolean hasForms()
  {
    boolean has = forms.size() > 0;
    return has;
  }

  public int indexOfForm(Application aForm)
  {
    int index = forms.indexOf(aForm);
    return index;
  }

  public static int minimumNumberOfJobs()
  {
    return 0;
  }

  public Job addJob(int aWorkHours, int aHourlyRate, Date aStartDate, Date aEndDate)
  {
    return new Job(aWorkHours, aHourlyRate, aStartDate, aEndDate, this);
  }

  public boolean addJob(Job aJob)
  {
    boolean wasAdded = false;
    if (jobs.contains(aJob)) { return false; }
    JobPosting existingJobPosting = aJob.getJobPosting();
    boolean isNewJobPosting = existingJobPosting != null && !this.equals(existingJobPosting);
    if (isNewJobPosting)
    {
      aJob.setJobPosting(this);
    }
    else
    {
      jobs.add(aJob);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeJob(Job aJob)
  {
    boolean wasRemoved = false;
    //Unable to remove aJob, as it must always have a jobPosting
    if (!this.equals(aJob.getJobPosting()))
    {
      jobs.remove(aJob);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addJobAt(Job aJob, int index)
  {  
    boolean wasAdded = false;
    if(addJob(aJob))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobs()) { index = numberOfJobs() - 1; }
      jobs.remove(aJob);
      jobs.add(index, aJob);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveJobAt(Job aJob, int index)
  {
    boolean wasAdded = false;
    if(jobs.contains(aJob))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobs()) { index = numberOfJobs() - 1; }
      jobs.remove(aJob);
      jobs.add(index, aJob);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addJobAt(aJob, index);
    }
    return wasAdded;
  }

  public boolean setInstructor(Instructor aInstructor)
  {
    boolean wasSet = false;
    if (aInstructor == null)
    {
      return wasSet;
    }

    Instructor existingInstructor = instructor;
    instructor = aInstructor;
    if (existingInstructor != null && !existingInstructor.equals(aInstructor))
    {
      existingInstructor.removeJobOffering(this);
    }
    instructor.addJobOffering(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfForms()
  {
    return 0;
  }

  public Application addForm(String aEmailAddress, String aPastExperience, int aPreference, Student aStudent)
  {
    return new Application(aEmailAddress, aPastExperience, aPreference, this, aStudent);
  }

  public boolean addForm(Application aForm)
  {
    boolean wasAdded = false;
    if (forms.contains(aForm)) { return false; }
    JobPosting existingJobApplication = aForm.getJobApplication();
    boolean isNewJobApplication = existingJobApplication != null && !this.equals(existingJobApplication);
    if (isNewJobApplication)
    {
      aForm.setJobApplication(this);
    }
    else
    {
      forms.add(aForm);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeForm(Application aForm)
  {
    boolean wasRemoved = false;
    //Unable to remove aForm, as it must always have a jobApplication
    if (!this.equals(aForm.getJobApplication()))
    {
      forms.remove(aForm);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addFormAt(Application aForm, int index)
  {  
    boolean wasAdded = false;
    if(addForm(aForm))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfForms()) { index = numberOfForms() - 1; }
      forms.remove(aForm);
      forms.add(index, aForm);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveFormAt(Application aForm, int index)
  {
    boolean wasAdded = false;
    if(forms.contains(aForm))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfForms()) { index = numberOfForms() - 1; }
      forms.remove(aForm);
      forms.add(index, aForm);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addFormAt(aForm, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (jobs.size() > 0)
    {
      Job aJob = jobs.get(jobs.size() - 1);
      aJob.delete();
      jobs.remove(aJob);
    }
    
    Instructor placeholderInstructor = instructor;
    this.instructor = null;
    placeholderInstructor.removeJobOffering(this);
    for(int i=forms.size(); i > 0; i--)
    {
      Application aForm = forms.get(i - 1);
      aForm.delete();
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "deadline" + "=" + (getDeadline() != null ? !getDeadline().equals(this)  ? getDeadline().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "instructor = "+(getInstructor()!=null?Integer.toHexString(System.identityHashCode(getInstructor())):"null")
     + outputString;
  }
}