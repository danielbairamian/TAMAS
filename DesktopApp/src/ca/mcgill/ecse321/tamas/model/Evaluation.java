/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.tamas.model;

// line 68 "../../../../../TAMAS.ump"
public class Evaluation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Evaluation Attributes
  private String text;
  private String course;
  private String instructorName;

  //Evaluation Associations
  private Student student;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Evaluation(String aText, String aCourse, String aInstructorName, Student aStudent)
  {
    text = aText;
    course = aCourse;
    instructorName = aInstructorName;
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create evaluation due to student");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setText(String aText)
  {
    boolean wasSet = false;
    text = aText;
    wasSet = true;
    return wasSet;
  }

  public boolean setCourse(String aCourse)
  {
    boolean wasSet = false;
    course = aCourse;
    wasSet = true;
    return wasSet;
  }

  public boolean setInstructorName(String aInstructorName)
  {
    boolean wasSet = false;
    instructorName = aInstructorName;
    wasSet = true;
    return wasSet;
  }

  public String getText()
  {
    return text;
  }

  public String getCourse()
  {
    return course;
  }

  public String getInstructorName()
  {
    return instructorName;
  }

  public Student getStudent()
  {
    return student;
  }

  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeEvaluation(this);
    }
    student.addEvaluation(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Student placeholderStudent = student;
    this.student = null;
    placeholderStudent.removeEvaluation(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "text" + ":" + getText()+ "," +
            "course" + ":" + getCourse()+ "," +
            "instructorName" + ":" + getInstructorName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null")
     + outputString;
  }
}