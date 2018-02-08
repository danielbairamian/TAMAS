package ca.mcgill.ecse321.tamas;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import ca.mcgill.ecse321.tamas.controller.DepartmentController;
import ca.mcgill.ecse321.tamas.controller.InvalidInputException;
import ca.mcgill.ecse321.tamas.controller.StudentDepartmentController;
import ca.mcgill.ecse321.tamas.model.*;
import ca.mcgill.ecse321.tamas.persistence.PersistenceXStream;

public class MainActivity extends AppCompatActivity {

    private Department department=null;
    private String fileName;
    private String selectedCourse;
    private int selectedJob;
    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileName = getFilesDir().getAbsolutePath() + "/tamasApplication1.xml";
        department = PersistenceXStream.initializeModelManager(fileName);


        /*
        TODO all the below code, except for refreshData, is hard coded so the application can run
        This code will create duplicates when we run the application multiple times,
        in order to avoid this, please comment the code when you run it for the 2nd time
         */


        Student student1 = new Student("Robert", department);
        Student student2 = new Student("Jacob", department);
        Student student3 = new Student("Leon", department);

        department.setCurrentStudent(student1);

        Course course1 = new Course(1000, "ECSE321", 3, 2, department);
        Course course2 = new Course(1040, "MATH363", 3, 2, department);
        Course course3 = new Course(1500, "ECSE210", 3, 2, department);

        Instructor instructor1 = new Instructor("Daniel", course1, department);
        Instructor instructor2 = new Instructor("Bob", course2, department);
        Instructor instructor3 = new Instructor("Alice", course3, department);

        java.util.Date deadLine = null;
        java.util.Date startDate = null;
        java.util.Date endDate = null;
        try {
            deadLine = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-06");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-06");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-06");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        java.sql.Date sqlDeadLine = new java.sql.Date(deadLine.getTime());
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        String description1 = "you will be working in the labs from 8am to 3pm, this job requires dedication";
        String description2 = "This is a full time job which requires 20 years of experience";
        String description3 = "This job is for phds, with 30 years of industry experience. gd luck :)";

        JobPosting jobPosting1 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, description1, instructor1);
        JobPosting jobPosting2 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, description2, instructor2);
        JobPosting jobPosting3 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, description3, instructor3);
        JobPosting jobPosting4 = new JobPosting(sqlDeadLine, sqlStartDate, sqlEndDate, description1, instructor3);

        Evaluation evaluation1 = new Evaluation("you did great", course1.getId(), course1.getInstructor().getName(), student1);
        Evaluation evaluation2 = new Evaluation("you didnt do great", course2.getId(), course2.getInstructor().getName(), student1);
        Evaluation evaluation3 = new Evaluation("you are the best", course3.getId(), course3.getInstructor().getName(), student2);


        Job job1 = new Job(10, 20, sqlStartDate, sqlEndDate, jobPosting1);
        Job job2 = new Job(15, 30, sqlStartDate, sqlEndDate, jobPosting2);
        Job job3 = new Job(20, 35, sqlStartDate, sqlEndDate, jobPosting3);
        Job job4 = new Job(10, 20, sqlStartDate, sqlEndDate, jobPosting4);

        Registration reg1 = new Registration(sqlStartDate, student1, job1);
        Registration reg2 = new Registration(sqlStartDate, student1, job2);
        Registration reg3 = new Registration(sqlStartDate, student2, job4);

        job2.setJobState(Job.JobState.DepartmentApproved);
        job4.setJobState(Job.JobState.Filled);

        PersistenceXStream.saveToXMLwithXStream(department);

        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshData() {

    }

    /*
    This method gets the spinner from the view offers layout, get the current student
    its registrations, and populate the spinner with only the jobs with a DepartmentApproved
    state
     */

    private void populateOffersSpinner() {
        Spinner offersSpinner = (Spinner) findViewById(R.id.offers_spinner);
        ArrayAdapter<CharSequence> courseName = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        courseName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Student currentStudent = department.getCurrentStudent();
        List<Registration> registrations = currentStudent.getRegistrations();

        for(Registration registration:  registrations){
            if(registration.getJob().getJobState().equals(Job.JobState.DepartmentApproved)){
                courseName.add(registration.getJob().getJobPosting().getInstructor().getCourse().getId().trim());
            }
        }

        offersSpinner.setAdapter(courseName);

    }

    /*
    This method gets the spinner from the view posting page, and gets the job spinner as well,
   then populate the course spinner with all the courses in the ECE department
     */

    private void populateCourseSpinner(){
        Spinner coursepostingspinner = (Spinner) findViewById(R.id.coursepostingspinner);
        ArrayAdapter<CharSequence> courseName = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        courseName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner jobpostingspinner = (Spinner) findViewById(R.id.jobnumberspinner);
        ArrayAdapter<CharSequence> jobNumber = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        jobNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        for(Course course : department.getCourses()){
            courseName.add(course.getId());
        }


        coursepostingspinner.setAdapter(courseName);

    }

    /*
       this methods get the spinner from the view applications layout, and populate the spinner with
       the course that the current student applied to. To do so, this method gets the applications of the
       current student using the StudentDepartmentControoler, and then loop through the applications and
       populate the spinner
     */

    private void populateApplicationList(){
        Spinner applicationSpinner = (Spinner) findViewById(R.id.viewappliedcoursespinner);
        TextView errorDisplayApplication = (TextView)  findViewById(R.id.error_display_applications);
        ArrayAdapter<CharSequence> courseName = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        courseName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        StudentDepartmentController dp = new StudentDepartmentController(department);

        List<Application> applications = new ArrayList<Application>();
        applications = dp.viewOwnApplications();

        if(applications != null) {
            for (Application application : applications) {
                courseName.add(application.getJobApplication().getInstructor().getCourse().getId());
            }

            applicationSpinner.setAdapter(courseName);
        }

    }

    /*
    this methods get the spinner form the view evaluations spinner, and populate it with the courses
    for which the current student has evaluations. To do so, it gets the list of evaluations from the
    current student, and loop through the evaluations, and add the course related to the evaluation to
    the spinner
     */

    private void populateEvaluationSpinner(){
        Spinner evaluationSpinner = (Spinner) findViewById(R.id.evaluations_spinner);

        ArrayAdapter<CharSequence> courseName = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        courseName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Student currentStudent = department.getCurrentStudent();

        List<Evaluation> evaluations = currentStudent.getEvaluations();

        for(Evaluation evaluation : evaluations){
            courseName.add(evaluation.getCourse());
        }

        evaluationSpinner.setAdapter(courseName);
    }

    /*
    this method gets the job from the view single job layout, then gets the current jobs of the current student
    and loop through those jobs to add them to the spinner
     */

    private void populateCurrentJobSpinner() {
        error = null;
        Spinner currentjobSpinner = (Spinner) findViewById(R.id.current_jobs);
        TextView errorView = (TextView) findViewById(R.id.error_my_jobs);

        ArrayAdapter<CharSequence> courseName = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        courseName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Student currentStudent = department.getCurrentStudent();
        StudentDepartmentController dp = new StudentDepartmentController(department);

        List<Job> currentJobs = new ArrayList<Job>();

        currentJobs = dp.viewOwnEmployment();

        if(error == null){
           for(Job job : currentJobs){
                courseName.add(job.getJobPosting().getInstructor().getCourse().getId());
           }
            currentjobSpinner.setAdapter(courseName);
        }else{
            errorView.setText(error);
        }

    }

    /*
    This method will display the view sinlge application layout. After it displays the layout, the
    method gets all the information of an application, and populate all the text views of this
    layout
     */

    public void openViewSingleApplication(View view, Application application){
        setContentView(R.layout.view_single_application);

        TextView studentEmailView = (TextView) findViewById(R.id.single_application_email);
        TextView studentExperienceView = (TextView) findViewById(R.id.single_application_experience);
        TextView studentPreferenceView = (TextView) findViewById(R.id.application_preference);

        String studentEmail = application.getEmailAddress();
        String studentExperience = application.getPastExperience();
        int studentPreference = application.getPreference();
        String studentApplicationPreference = Integer.toString(studentPreference);

        studentEmailView.setText(studentEmail);
        studentExperienceView.setText(studentExperience);
        studentPreferenceView.setText(studentApplicationPreference);

    }

    /*
    This method is called when the current student applies to a job. The method will get all the
    informations inputed by the student (email. experience and preference), then will find the
    class for which the student is applying, and then will call a controller method in order to
    apply for a job. then redirects to the home page
     */

    public void sendApplication(View view){
        //getting the textfields and the edit text
        EditText studentEmail = (EditText) findViewById(R.id.student_email);
        EditText studentExperience = (EditText) findViewById(R.id.student_past_experience);
        Spinner spinner = (Spinner) findViewById(R.id.preference_spinner);

        String email = studentEmail.getText().toString();
        String experience = studentExperience.getText().toString();

        int preference = spinner.getSelectedItemPosition() + 1;

        //getting the course applying for
        Course aCourse = null;
        for(Course course : department.getCourses()){
            if(course.getId().trim().equals(selectedCourse.trim())){
                aCourse = course;
            }
        }

        //getting the job posting selected by the student
        JobPosting job = aCourse.getInstructor().getJobOffering(selectedJob);

        StudentDepartmentController dp = new StudentDepartmentController(department);
        try{
            dp.applyForJob(job, email, experience, preference);
            setContentView(R.layout.home_page);
        }catch(InvalidInputException e){
            error = e.getMessage();
            TextView applyError = (TextView) findViewById(R.id.error_send_application);
            applyError.setText(error);
        }
    }

    /*
    This method loads the view single application layout. Before displaying this layout, the method
    gets all the information needed from the previous layout. it gets the selected course, and finds
    the course object, then gets all the applications of the current student. it will loop through all
    the applications, and once it find the selected application, it will set the content view to view
    single application by passing the application as argument

     */

    public void viewSelectedApplication(View view){
        error = null;
        Spinner courseApplied = (Spinner) findViewById(R.id.viewappliedcoursespinner);
        TextView errorDisplayApplication = (TextView)  findViewById(R.id.error_display_applications);

        Object courseObject = courseApplied.getSelectedItem();

        if(courseObject != null){
            String courseName = courseObject.toString().trim();
            StudentDepartmentController dp = new StudentDepartmentController(department);
            List<Application> applications = null;

            applications = dp.viewOwnApplications();

            Application thisApplication = null;
            for(Application application : applications){
                if(application.getJobApplication().getInstructor().getCourse().getId().trim().equals(courseName)){
                    thisApplication = application;
                    break;
                }
            }

            if(thisApplication == null){
                error = "The selected application is not for you.";
                errorDisplayApplication.setText(error);
                viewApplications(view);
            }else {
                if (error == null) {
                    openViewSingleApplication(view, thisApplication);
                }else{
                    viewApplications(view);
                }
            }
        }else{
            error = "Please select an application from the spinner";
            errorDisplayApplication.setText(error);
        }
    }

    /*
    This method is called when the student wants to delete an application. Same as above, the method
    gets the selected course firstly, then gets all the current student's application, and finds the one
    selected by the student. Finally, it calls a controller method to cancel the application
     */

    public void cancelApplication(View view){
        Spinner courseApplied = (Spinner) findViewById(R.id.viewappliedcoursespinner);
        TextView errorDisplayApplication = (TextView)  findViewById(R.id.error_display_applications);

        //getting the selected course name

        Object courseObject = courseApplied.getSelectedItem();
        if(courseObject != null){
            String courseName = courseObject.toString().trim();
            StudentDepartmentController dp = new StudentDepartmentController(department);

            List<Application> applications = null;

            //getting the aplications of the current student
            applications = dp.viewOwnApplications();

            Application thisApplication = null;

            //getting the application chosen by the student
            for(Application application : applications){
                if(application.getJobApplication().getInstructor().getCourse().getId().trim().equals(courseName)){
                    thisApplication = application;
                    break;
                }
            }

            if(thisApplication == null){
                error = "The selected application is not for you.";
                errorDisplayApplication.setText(error);
                viewApplications(view);
            }else{
                if(error == null){
                    //canceling the application
                    try {
                        dp.cancelApplication(thisApplication);
                    } catch (InvalidInputException e) {
                        error = e.getMessage();
                    }
                    if(error == null){
                        errorDisplayApplication.setText(error);
                    }
                    viewApplications(view);
                }
            }
        }else{
            error = "Please select an application from the spinner";
            errorDisplayApplication.setText(error);
        }

    }

    /*
    This method is called when the student doesn't accept the job offer. Firstly, the method gets
    all the information of the job (stored in the textviews) in order to find which job the student
    wants to decline. once the job is fund, the method calls a controller method to decline the offer
     */

    public void declineOffer(View view){

        TextView instructorName = (TextView) findViewById(R.id.intructorName_offer);
        TextView courseName = (TextView) findViewById(R.id.offer_course_name);
        TextView jobType = (TextView) findViewById(R.id.offer_position);
        TextView errorlabel = (TextView) findViewById(R.id.error_single_offer);

        String instructor = instructorName.getText().toString().trim();
        String course = courseName.getText().toString().trim();
        String position = jobType.getText().toString().trim();

        Student currentStudent = department.getCurrentStudent();
        List<Registration> registrations = currentStudent.getRegistrations();

        Job jobToDecline = null;

        for(Registration registration : registrations){
            if(registration.getJob().getJobPosting().getInstructor().getName().trim().equals(instructor)){
                if(registration.getJob().getJobTypeFullName().toString().equals(position)){
                    jobToDecline = registration.getJob();
                }
            }
        }

        if(jobToDecline != null){
            StudentDepartmentController dp = new StudentDepartmentController(department);
            try {
                dp.declineJobOffer(jobToDecline);
                backToHomePage(view);
            } catch (InvalidInputException e) {
                String error = e.getMessage();
                errorlabel.setText(error);
            }
        }else{
            errorlabel.setText("The Job offer is not available.");
        }

    }

    /*
    This method works exactly as the one above, but instead of calling a controller method to decline
    the offer, it calls another controller method which accepts the offer
     */

    public void acceptOffer(View view){
        StudentDepartmentController dp = new StudentDepartmentController(department);

        TextView instructorName = (TextView) findViewById(R.id.intructorName_offer);
        TextView courseName = (TextView) findViewById(R.id.offer_course_name);
        TextView jobType = (TextView) findViewById(R.id.offer_position);
        TextView errorlabel = (TextView) findViewById(R.id.error_single_offer);

        String instructor = instructorName.getText().toString().trim();
        String course = courseName.getText().toString().trim();
        String position = jobType.getText().toString().trim();

        Student currentStudent = department.getCurrentStudent();
        List<Registration> registrations = currentStudent.getRegistrations();

        Job jobToAccpet = null;

        for(Registration registration : registrations){
            if(registration.getJob().getJobPosting().getInstructor().getName().trim().equals(instructor)){
                if(registration.getJob().getJobTypeFullName().toString().equals(position)){
                    jobToAccpet = registration.getJob();
                }
            }
        }

        if(jobToAccpet != null){
            try {
                dp.acceptJobOffer(jobToAccpet);
                backToHomePage(view);
            } catch (InvalidInputException e) {
                String error = e.getMessage();
                errorlabel.setText(error);
            }
        }else{
            errorlabel.setText("The Job offer is not available.");
        }

    }

    /*
    This method redirects the student to the login page, when the logout button is clicked
     */

    public void logout(View view){
        setContentView(R.layout.login_page);
    }

    /*
    This method opens the view single evaluation layout. at first, it gets the selected course using
    the course name, and then gets the evaluation related to this particular course. once it finds the
    application, it opens the view single evaluation layout and sends the application as input
     */
    public void seeEvaluation(View view){
        Spinner selectedEvaluation = (Spinner) findViewById(R.id.evaluations_spinner);
        TextView errorDisplayApplication = (TextView)  findViewById(R.id.error_my_evaluations);

        //getting the selected course name
        Object courseObject = selectedEvaluation.getSelectedItem();
        if(courseObject != null){
            String courseName = courseObject.toString().trim();
            Student currentStudent = department.getCurrentStudent();
            Evaluation evaluationSelected = null;
            for(Evaluation evaluation : currentStudent.getEvaluations()){
                if(evaluation.getCourse().trim().equals(courseName.trim())){
                    evaluationSelected = evaluation;
                    break;
                }
            }

            if(evaluationSelected != null){
                openSingleEvaluation(view, evaluationSelected);
            }else{
                error = "The selected evaluation is not for you";
                errorDisplayApplication.setText(error);
            }
        }else{
            error = "Please select an evaluation from the drop down";
            errorDisplayApplication.setText(error);
        }
    }

    /*
    This method gets the string inputted by the user in the login page. Then it loops through all
    the students in the department, and checks if it finds the student who wants to login.
     */

    public void loginStudent(View view){
        TextView usernameTextView = (TextView) findViewById(R.id.login_username);
        TextView errorLogin = (TextView) findViewById(R.id.error_login);

        String username = usernameTextView.getText().toString().trim();
        StudentDepartmentController dp = new StudentDepartmentController(department);
        Student currentStudent = null;
        boolean found=false;
        if(username.trim().isEmpty() || username.trim().equals("")){
            errorLogin.setText("Please enter your name to login");
        }else {
            for (Student student : department.getStudents()) {
                if (student.getName().trim().equals(username)) {
                    currentStudent = student;
                    found = true;
                }
            }

            if (found == false) {
                errorLogin.setText("User not found. Please try again.");
            } else {
                department.setCurrentStudent(currentStudent);
                setContentView(R.layout.home_page);
            }
        }
    }

    /*
    This method opens the view single job layout. At first, it gets the course selected form the
    current student from the spinner. Once it finds the course, it gets a list of all the current
    employment of the student, and loop through them in order to find the one that the student wants
    to see.
     */
    public void seeSingleJob(View view){
        error =null;
        StudentDepartmentController dp = new StudentDepartmentController(department);
        Spinner selectedJob = (Spinner) findViewById(R.id.current_jobs);
        TextView errorSelectedJob = (TextView) findViewById(R.id.error_my_jobs);

        Object courseObject = selectedJob.getSelectedItem();
        if(courseObject != null){
            String courseName = courseObject.toString().trim();
            Student currentStudent = department.getCurrentStudent();

            Job currentJob = null;

            List<Job> jobs = new ArrayList<Job>();

            jobs = dp.viewOwnEmployment();


            if(error == null){
                for(Job job : jobs){
                    if(courseName.equals(job.getJobPosting().getInstructor().getCourse().getId().trim())){
                        currentJob = job;
                        break;
                    }
                }
            }else{
                errorSelectedJob.setText(error);
            }

            if(currentJob == null){
                error = "Please select a course";
                errorSelectedJob.setText(error);
            }else{
                openViewSingleJob(view, currentJob);
            }
        }
        else{
            error = "Please select a course from the drop down";
            errorSelectedJob.setText(error);
        }
    }
    /*
    This method sets view single evaluation layout to visible. it firstly gets all the textViews of
    the page, populate them with evaluation object sent as argument.
     */

    public void openSingleEvaluation(View view, Evaluation evaluation){
        setContentView(R.layout.view_single_evaluation);

        TextView courseEvaluated = (TextView) findViewById(R.id.evaluated_course);
        TextView instructorName = (TextView) findViewById(R.id.intructorName_evaluation);
        TextView studentEvaluation = (TextView) findViewById(R.id.student_evaluation);

        String course = evaluation.getCourse();
        String instructor = evaluation.getInstructorName();
        String thisEvaluation = evaluation.getText();

        courseEvaluated.setText(course);
        instructorName.setText(instructor);
        studentEvaluation.setText(thisEvaluation);
    }

    /*

     */

    public void viewSinglePosting(View view) {
        //getting the jobSpinner and courseSpinner from the view
        Spinner jobSpinner = (Spinner) findViewById(R.id.jobnumberspinner);
        Spinner courseSpinner = (Spinner) findViewById(R.id.coursepostingspinner);
        TextView seePostingError = (TextView) findViewById(R.id.error_see_posting);

            Object clickCourse = courseSpinner.getSelectedItem();
            if(clickCourse != null){
                String clickedCourse = clickCourse.toString().trim();
                selectedCourse = clickedCourse;

                //get the selected job Number
                int jobNumber = jobSpinner.getSelectedItemPosition();

                selectedJob = jobNumber;

                Course displayCourse = null;
                //looping through the list of courses, to find the selected course

                for(Course course : department.getCourses()){
                    if(course.getId().trim().equals(clickedCourse)){
                        displayCourse = course;
                    }
                }

                if(displayCourse != null){
                    //get the number of jobs
                    int numberOfJobs = displayCourse.getInstructor().numberOfJobOfferings();

                    //a check for the selected job, which will throw an error if the student selects a job which doesn't exist
                    if(jobNumber == 1 && numberOfJobs != 2 ){
                        error = "This course has only 1 job offering. Please select 1.";

                        seePostingError.setText(error);
                    }else {
                        //changing the layout to see the selected job posting
                        setContentView(R.layout.current_posting);
                        TextView tv1 = (TextView) findViewById(R.id.ourseNumberID);

                        //displaying the selected course
                        tv1.setText(selectedCourse);

                        //displaying the corresponding description
                        TextView tv2 = (TextView) findViewById(R.id.currentCourseDescription);
                        tv2.setText(displayCourse.getInstructor().getJobOffering(jobNumber).getDescription());
                    }
                }else{
                    error = "Course not found.";
                    seePostingError.setText(error);
                }
            }else{
                error ="Please select a course from the drop down";
                seePostingError.setText(error);
            }


    }

    /*
    this method lets the student view any job offer he got. at first, the method gets the selected
    course, and finds it. then, it loops through the current student's registration, in order to find
    which registration (student + job), the current student is selecting (it works, since each registration
    has a unique course and job). then, the method gets the job related to the registraion and calls the method
    that populates the views of the view single offer layout
     */

    public void viewSelectedOffer(View view){
        Spinner offerSpinner = (Spinner) findViewById(R.id.offers_spinner);
        TextView error = (TextView) findViewById(R.id.error_see_offers);
        Object clickCourse = offerSpinner.getSelectedItem();

        Job selectedJob = null;

        if(clickCourse != null){
            Student currentStudent = department.getCurrentStudent();
            String clickedCourse = clickCourse.toString().trim();
            List<Registration> registrations = currentStudent.getRegistrations();

            for(Registration registration:  registrations){
                if(registration.getJob().getJobState().equals(Job.JobState.DepartmentApproved)){
                   if(registration.getJob().getJobPosting().getInstructor().getCourse().getId().trim().equals(clickedCourse)){
                        selectedJob = registration.getJob();
                   }
                }
            }

            if(selectedJob != null){
                openSingleJobOffer(view, selectedJob);
            }else{
                error.setText("The selected Job is not offered!");
            }

        }else{
            error.setText("Please select a course from the drop down");
        }


    }

    /*
    This method has a job as input. It gets all the information it needs from the job argument, and
    uses them to populate the views on this page.
     */
    public void openSingleJobOffer(View view, Job currentJob){
        setContentView(R.layout.view_single_offer);

        TextView instructorName = (TextView) findViewById(R.id.intructorName_offer);
        TextView courseName = (TextView) findViewById(R.id.offer_course_name);
        TextView positionTitle = (TextView) findViewById(R.id.offer_position);
        TextView startDate = (TextView) findViewById(R.id.startdate_offer);
        TextView endDate = (TextView) findViewById(R.id.enddate_offer);
        TextView hourlyRate = (TextView) findViewById(R.id.offer_hourly_rate);
        TextView workHours = (TextView) findViewById(R.id.work_hour_offer);
        TextView errorSingleJob = (TextView) findViewById(R.id.error_single_offer);


        String instructor = currentJob.getJobPosting().getInstructor().getName();
        String course = currentJob.getJobPosting().getInstructor().getCourse().getId();
        String position = currentJob.getJobTypeFullName();
        String start = currentJob.getStartDate().toString();
        String end = currentJob.getEndDate().toString();


        Integer hourRate = currentJob.getHourlyRate();
        String hours = hourRate.toString() + "$/Hour";

        Integer work_hours = currentJob.getWorkHours();
        String work = work_hours.toString() + " Hours";

        instructorName.setText(instructor);
        courseName.setText(course);
        positionTitle.setText(position);
        startDate.setText(start);
        endDate.setText(end);
        hourlyRate.setText(hours);
        workHours.setText(work);

    }

    /*
    This method gets a job as an input. It gets all the information it needs to get in order to populate
    the view single job layout, and then, populate all the views.
     */

    public void openViewSingleJob(View view, Job currentJob){
        setContentView(R.layout.view_single_job);
        TextView instructorName = (TextView) findViewById(R.id.intructorName_job);
        TextView courseName = (TextView) findViewById(R.id.job_course_name);
        TextView positionTitle = (TextView) findViewById(R.id.job_position);
        TextView startDate = (TextView) findViewById(R.id.startdate_job);
        TextView endDate = (TextView) findViewById(R.id.enddate_job);
        TextView hourlyRate = (TextView) findViewById(R.id.job_hourly_rate);
        TextView workHours = (TextView) findViewById(R.id.work_hour_job);
        TextView errorSingleJob = (TextView) findViewById(R.id.error_single_job);


        String instructor = currentJob.getJobPosting().getInstructor().getName();
        String course = currentJob.getJobPosting().getInstructor().getCourse().getId();
        String position = currentJob.getJobTypeFullName();
        String start = currentJob.getStartDate().toString();
        String end = currentJob.getEndDate().toString();

        Integer hourRate = currentJob.getHourlyRate();
        String hours = hourRate.toString() + "$/Hour";

        Integer work_hours = currentJob.getWorkHours();
        String work = work_hours.toString() + " Hours";

        instructorName.setText(instructor);
        courseName.setText(course);
        positionTitle.setText(position);
        startDate.setText(start);
        endDate.setText(end);
        hourlyRate.setText(hours);
        workHours.setText(work);

    }

    /*
    All the methods below are basic methods that allow the student to navigate through the application
     */
    public void backToCurrentOffers(View view){
        viewJobOffers(view);
    }

    public void backToCurrentJobs(View view){
        viewCurrentJob(view);
    }

    public void backToSeeApplications(View view){
        viewApplications(view);
    }

    public void openMyEvaluations(View view){
        setContentView(R.layout.personal_evaluations);
        populateEvaluationSpinner();
    }

    public void backToJobPostings(View view){
        setContentView(R.layout.job_postings);
        populateCourseSpinner();
    }

    public void openApplicationForm(View view){
        setContentView(R.layout.application_form);

        //setting the error to empty string when redering the page
        TextView applyError = (TextView) findViewById(R.id.error_send_application);
        applyError.setText("");
    }

    public void backToHomePage(View view){
        setContentView(R.layout.home_page);
    }

    public void viewJobPostings(View view) {
        setContentView(R.layout.job_postings);

        //setting the error to empty string when redering the page
        TextView seePostingError = (TextView) findViewById(R.id.error_see_posting);
        seePostingError.setText("");

        populateCourseSpinner();
    }

    public void viewApplications(View view) {
        setContentView(R.layout.applications);
        populateApplicationList();
    }

    public void viewJobOffers(View view) {
        setContentView(R.layout.job_offers);
        populateOffersSpinner();
    }

    public void viewCurrentJob(View view) {
        setContentView(R.layout.current_job);
        populateCurrentJobSpinner();
    }

    public void backToEvaluations(View view){
        openMyEvaluations(view);
    }

}

