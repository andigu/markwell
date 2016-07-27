package service;

import java.sql.SQLException;

import entity.Assessment;
import entity.AssessmentMark;
import entity.Course;
import entity.Student;
import entity.StudentCourseAssociation;
import entity.Teacher;

/**
 * A concrete Service that could be instantiated to test the service methods that are <b>non-database related</b>.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */

class InMemoryService extends AbstractService {

    InMemoryService(Population<Teacher> teachers, Population<Student> students) {
        this.studentSearchTree = students;
        this.teacherSearchTree = teachers;
    }

    @Override
    public void addAssessment(Assessment assessment, Course course) throws SQLException {

    }

    @Override
    public void setClubs(Student student, int clubs) throws SQLException {

    }

    @Override
    public void setVolunteerHours(Student student, int volunteerHours) throws SQLException {

    }

    @Override
    public void addAbsence(StudentCourseAssociation courseAssociation) throws SQLException {

    }

    @Override
    public void addLate(StudentCourseAssociation courseAssociation) throws SQLException {

    }

    @Override
    public void updateAssessment(String oldName, Assessment assessment, Course course) throws SQLException {

    }

    @Override
    public void updateMark(StudentCourseAssociation studentCourseAssociation, AssessmentMark mark) throws SQLException {

    }

}
