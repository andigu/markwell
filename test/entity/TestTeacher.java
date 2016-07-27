package entity;

/**
 * Tests teachers to ensure the way they store course associations and states is correct.
 *
 * @author Katrina James
 * @course ICS4U
 * @date 6/21/2016
 */
public class TestTeacher {

    public static void main(String[] args) {
        Teacher teacherAndi = new Teacher("Andi", "0123", "3210", "andi@email.com");

        Course dataManagement = new Course("MDM4U");
        Course comSci12 = new Course("ICS4U");
        Course religion11 = new Course("HRE3U");

        TeacherCourseAssociation period1 = new TeacherCourseAssociation(dataManagement, teacherAndi);
        TeacherCourseAssociation period2 = new TeacherCourseAssociation(comSci12, teacherAndi);
        TeacherCourseAssociation period3 = new TeacherCourseAssociation(religion11, teacherAndi);

        System.out.println(period1.getCourse().toString());
        System.out.println(period2.getCourse().toString());
        System.out.println(period3.getCourse().toString());

        System.out.println(period1.toString());
        System.out.println(period2.toString());
        System.out.println(period3.toString());
    }
}
