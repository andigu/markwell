package service;

import entity.*;

import java.sql.SQLException;
/**
 * Interface of all methods that the user interface requires in order to interact with the database and object model
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public interface Service {
    Student getStudentByCredentials(String username, String password);

    Teacher getTeacherByCredentials(String username, String password);

    Student[] sortByName(Student[] students, boolean ascending);

    void addAssessment(Assessment assessment, Course course) throws SQLException;

    Student[] getStudentsByTeacher(Teacher teacher);

    void setClubs(Student student, int clubs) throws SQLException;

    void setVolunteerHours(Student student, int volunteerHours) throws SQLException;

    void addAbsence(StudentCourseAssociation courseAssociation) throws SQLException;

    void addLate(StudentCourseAssociation courseAssociation) throws SQLException;

    void updateAssessment(String oldName, Assessment assessment, Course course) throws SQLException;

    void updateMark(StudentCourseAssociation studentCourseAssociation, AssessmentMark mark) throws SQLException;
}
