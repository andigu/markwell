package database;

import entity.*;
import gui.AppConfig;
import service.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Tests the database reader to ensure it reads data to objects correctly.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class TestDatabaseReader {
    private TestDatabaseReader() throws SQLException, ClassNotFoundException {
        DatabaseReader databaseReader = new DatabaseReader("markwell_tester", "root", "markwell");
        AssessmentMark mark;

        for (Student student : databaseReader.getStudents()) {
            System.out.println(student.toString());
            for (CourseAssociation courseAssociation : student.getCourseAssociations()) {
                System.out.println("course = " + courseAssociation.getCourse().getName() + " ");
                if (student.getName().equals("A") && courseAssociation.getCourse().getName().equals("math")) {
                    Assessment assessment = courseAssociation.getCourse().getAssessmentByName("math_test");
                    mark = new AssessmentMark(assessment);
                    mark.setMark(AssessmentCategory.Knowledge, 30);
                    mark.setMark(AssessmentCategory.Thinking, 20);
                    mark.setMark(AssessmentCategory.Application, 60);
                    mark.setMark(AssessmentCategory.Communication, 100);
                    DatabaseWriter writer = new DatabaseWriter("Markwell", "root", "markwell");
                    writer.updateMark((StudentCourseAssociation) courseAssociation, mark);
                }

                for (AssessmentMark assessmentMark : ((StudentCourseAssociation) courseAssociation).getMarks()) {
                    System.out.print("\t" + assessmentMark.toString() + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

        databaseReader.refresh();
        System.out.println("________Refresh_________");
        for (Student student : databaseReader.getStudents()) {
            System.out.println(student.toString());
            for (CourseAssociation courseAssociation : student.getCourseAssociations()) {
                System.out.println("course = " + courseAssociation.getCourse().getName() + " ");
                for (AssessmentMark assessmentMark : ((StudentCourseAssociation) courseAssociation).getMarks()) {
                    System.out.print("\t" + assessmentMark.toString() + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

        for (Teacher teacher : databaseReader.getTeachers()) {
            System.out.println(teacher.toString());
            for (CourseAssociation courseAssociation : teacher.getCourseAssociations()) {
                System.out.println("course = " + courseAssociation.getCourse().getName() + " ");
                for (StudentCourseAssociation studentCourseAssociation : ((TeacherCourseAssociation) courseAssociation).getStudentCourseAssociations()) {
                    System.out.println("\t" + studentCourseAssociation.getStudent().toString());
                }
            }
        }

        for (Course course : databaseReader.getCourses()) {
            System.out.println(course.getName());
            for (Assessment assessment : course.getAssessments()) {
                System.out.println("\t" + assessment.getName() + " " + assessment.getWeight());
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        DatabaseReader databaseReader = new DatabaseReader("markwell_tester", "root", "markwell");
        new TestDatabaseReader();
        Service service = AppConfig.getService();
        System.out.println(service.getStudentByCredentials("A", "abc").toString());
        System.out.println(Arrays.toString(service.sortByName(databaseReader.getStudents(), true)));
    }
}
