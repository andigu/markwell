package entity;

import java.util.*;

/**
 * Stores information about the connection between a teacher and a course they teach
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class TeacherCourseAssociation extends CourseAssociation {
    private Teacher teacher;
    private Set<StudentCourseAssociation> studentsCourseAssociations;

    public TeacherCourseAssociation(Course course, Teacher teacher) {
        super(course);
        this.teacher = teacher;
        studentsCourseAssociations = new HashSet<>();
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public StudentCourseAssociation[] getStudentCourseAssociations() {
        return studentsCourseAssociations.toArray(new StudentCourseAssociation[0]);
    }

    /**
     * Enrolls a student in the course, being taught by this teacher
     *
     * @param studentCourseAssociation The new courseAssociation
     */
    public void addStudentCourseAssociation(StudentCourseAssociation studentCourseAssociation) {
        studentsCourseAssociations.add(studentCourseAssociation);
    }

    /**
     * Gets the average of all marks that all students being taught by this teacher in the course have.
     *
     * @return The average of all marks that all students being taught by this teacher in the course have.
     */
    public int getAverage() {
        int markSum = 0;
        for (StudentCourseAssociation studentCourseAssociation : studentsCourseAssociations) {
            markSum += studentCourseAssociation.getAverage();
        }
        if (studentsCourseAssociations.size() == 0) { // If there are no students enrolled in the course (no zero division error)
            return 0;
        } else {
            return markSum / studentsCourseAssociations.size();
        }
    }

    /**
     * Gets all students being taught by this teacher and are enrolled in the course.
     *
     * @return Array of all students being taught by this teacher and are in the course.
     */
    public Student[] getStudents() {
        // Initiates an array of students the same size as the number of students enrolled in the course
        Student[] students = new Student[getStudentCourseAssociations().length];
        int count = 0;
        for (StudentCourseAssociation courseAssociation : getStudentCourseAssociations()) {
            students[count] = courseAssociation.getStudent();
            count++;
        }
        return students;
    }
}
