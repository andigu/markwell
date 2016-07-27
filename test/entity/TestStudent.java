package entity;

/**
 * Tests students to ensure they store courses properly.
 *
 * @author Katrina James
 * @course ICS4U
 * @date 6/21/2016
 */
public class TestStudent {

    public static void main(String[] args) {

        Student josh = new Student("Joshua", "5678", "person");

        Course dataManagement = new Course("MDM4U");
        Course computerScience = new Course("ICS4U");
        Course religion = new Course("HRE3U");
        Course chemistry = new Course("SCH3U");

        StudentCourseAssociation period1 = new StudentCourseAssociation(dataManagement, josh);
        StudentCourseAssociation period2 = new StudentCourseAssociation(computerScience, josh);
        StudentCourseAssociation period3 = new StudentCourseAssociation(religion, josh);
        StudentCourseAssociation period4 = new StudentCourseAssociation(chemistry, josh);

        System.out.println(period1.getStudent().toString());
        System.out.println(period1.getCourse().toString());
        System.out.println(period1.toString());

        System.out.println(period2.getStudent().toString());
        System.out.println(period2.getCourse().toString());
        System.out.println(period2.toString());

        System.out.println(period3.getStudent().toString());
        System.out.println(period3.getCourse().toString());
        System.out.println(period3.toString());

        System.out.println(period4.getStudent().toString());
        System.out.println(period4.getCourse().toString());
        System.out.println(period4.toString());

        System.out.println(josh.getName());
    }
}
