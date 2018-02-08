package ca.mcgill.ecse321.tamas.application;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.tamas.controller.DepartmentControllerTest;
import ca.mcgill.ecse321.tamas.controller.InstructorDepartmentController;
import ca.mcgill.ecse321.tamas.controller.InstructorDepartmentControllerTest;
import ca.mcgill.ecse321.tamas.controller.StudentDepartmentControllerTest;
import ca.mcgill.ecse321.tamas.persistence.TestPersistence;
@RunWith(Suite.class)
@SuiteClasses({ DepartmentControllerTest.class, TestPersistence.class, StudentDepartmentControllerTest.class , InstructorDepartmentControllerTest.class })public class AllTests {

}
