package service;

import java.sql.SQLException;

import database.DatabaseReader;
import database.DatabaseWriter;
import entity.Assessment;
import entity.AssessmentMark;
import entity.Course;
import entity.Student;
import entity.StudentCourseAssociation;

/**
 * Provides a way for the user interface to interact with the object model and the database. All methods are fully
 * implemented (including those that interact with the database).
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/20/2016
 */
public class DefaultService extends AbstractService  {
    private String databaseName;
    private String username;
    private String password;
    private DatabaseWriter databaseWriter;
 
    public DefaultService(String databaseName, String username, String password) throws SQLException, ClassNotFoundException {
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        getData();
    }

    private void getData() throws SQLException, ClassNotFoundException {
        DatabaseReader databaseReader = new DatabaseReader(databaseName, username, password);
        databaseWriter = new DatabaseWriter(databaseName, username, password);
        studentSearchTree = new Population<>(databaseReader.getStudents());
        teacherSearchTree = new Population<>(databaseReader.getTeachers());
    }

    /**
     * Updates the mark of a given student course enrollment. Updates both in the object model and in the database.
     *
     * @param studentCourseAssociation The student course enrollment to be updated with the new mark.
     * @param mark The new mark.
     */
    public void updateMark(StudentCourseAssociation studentCourseAssociation, AssessmentMark mark) throws SQLException {
        studentCourseAssociation.updateMark(mark);
        databaseWriter.updateMark(studentCourseAssociation, mark);
    }

    /**
     * Adds assessment to given course. Updates both in the object model and in the database.
     *
     * @param assessment The assessment to be updated.
     * @param course The course to be updated with the course.
     */
    public void addAssessment(Assessment assessment, Course course) throws SQLException {
        course.addAssessment(assessment);
        databaseWriter.addAssessment(assessment, course);
    }

    /**
     * Adds assessment to given course. Adds both in the object model and in the database.
     *
     * @param oldName the old name of the assessment. This is because this information is needed to find the old
     *                assessment data in the database
     * @param assessment The assessment to be added.
     * @param course The course to to which the assessment should be added.
     */
    public void updateAssessment(String oldName, Assessment assessment, Course course) throws SQLException {
        course.deleteAssessment(oldName);
        course.addAssessment(assessment);
        databaseWriter.updateAssessment(oldName, assessment, course);
    }

    /**
     * Sets number of clubs for a given student to the new number of clubs
     *
     * @param student The student to be updated.
     * @param clubs The new number of clubs.
     */
    public void setClubs(Student student, int clubs) throws SQLException {
        student.setClubs(clubs);
        databaseWriter.setClubs(student, clubs);
    }

    /**
     * Sets number of volunteer hours for a given student to the new number of hours
     *
     * @param student The student to be updated.
     * @param volunteerHours The new number of volunteer hours.
     */
    public void setVolunteerHours(Student student, int volunteerHours) throws SQLException {
        student.setVolunteerHours(volunteerHours);
        databaseWriter.setVolunteerHours(student, volunteerHours);
    }

    /**
     * Increments the lates a student has in a course by 1
     *
     * @param courseAssociation The courseAssociation where the student was late in.
     */
    public void addLate(StudentCourseAssociation courseAssociation) throws SQLException {
        courseAssociation.setLates(courseAssociation.getLates() + 1);
        databaseWriter.setLates(courseAssociation, courseAssociation.getLates());
    }

    /**
     * Increments the absences a student has in a course by 1
     *
     * @param courseAssociation The courseAssociation where the student was absent in.
     */
    public void addAbsence(StudentCourseAssociation courseAssociation) throws SQLException {
        courseAssociation.setAbsences(courseAssociation.getLates() + 1);
        databaseWriter.setAbsences(courseAssociation, courseAssociation.getAbsences());
    }
}