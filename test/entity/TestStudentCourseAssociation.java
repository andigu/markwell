package entity;

import utility.TestUtil;

/**
 * Tests student course associations to check that they add assessment marks and connect with courses properly.
 *
 * @author Katrina James
 * @course ICS4U
 * @date 6/21/2016
 */
public class TestStudentCourseAssociation {
    /**
     * Generates sample StudentCourseAssociation
     * @return A StudentCourseAssociation to test on
     */
    private StudentCourseAssociation getStudentCourseAssociation() {
        Course course = new Course("math");
        course.setCategoryWeight(AssessmentCategory.Application, 50);
        course.setCategoryWeight(AssessmentCategory.Communication, 20);
        course.setCategoryWeight(AssessmentCategory.Knowledge, 20);
        course.setCategoryWeight(AssessmentCategory.Thinking, 10);
        Assessment assessmentA = new Assessment("mid-term", course, 10);
        Assessment assessmentB = new Assessment("final", course, 10);
        course.addAssessment(assessmentA);
        course.addAssessment(assessmentB);

        Student student = new Student("jason", "js", "password");
        StudentCourseAssociation studentCourseAssociation = new StudentCourseAssociation(course, student);
        Teacher teacher = new Teacher("Smith", "101", "password", "a@gmail.com");
        studentCourseAssociation.setTeacher(teacher);
        return studentCourseAssociation;
    }

    /**
     * Tests the math behind calculating assessment averages and adding assessments
     */
    private void testAssessmentMark() {
        StudentCourseAssociation target = getStudentCourseAssociation();

        TestUtil.assertEquals(0, target.getMarks().length); // Should have no mark


        Assessment assessmentA = target.getCourse().getAssessmentByName("final");
        AssessmentMark assessmentMarkA = new AssessmentMark(assessmentA);
        assessmentMarkA.setMark(AssessmentCategory.Application, 80);
        assessmentMarkA.setMark(AssessmentCategory.Communication, 80);
        assessmentMarkA.setMark(AssessmentCategory.Knowledge, 80);
        assessmentMarkA.setMark(AssessmentCategory.Thinking, 80);

        target.updateMark(assessmentMarkA);

        TestUtil.assertEquals(1, target.getMarks().length); // Should have one mark

        AssessmentMark mark2 = new AssessmentMark(assessmentA);
        mark2.setMark(AssessmentCategory.Application, 90);
        mark2.setMark(AssessmentCategory.Communication, 90);
        mark2.setMark(AssessmentCategory.Knowledge, 90);
        mark2.setMark(AssessmentCategory.Thinking, 100);

        target.updateMark(mark2);

        TestUtil.assertEquals(1, target.getMarks().length); // Should still have one mark as it is an update

        AssessmentMark latest = target.getMarkByAssessment(assessmentA);

        TestUtil.assertEquals("final", latest.getAssessment().getName());
        TestUtil.assertEquals(100, latest.getMark(AssessmentCategory.Thinking));

        Assessment assessmentB = target.getCourse().getAssessmentByName("mid-term");

        AssessmentMark midTermMark = new AssessmentMark(assessmentB);
        midTermMark.setMark(AssessmentCategory.Application, 100);
        target.updateMark(midTermMark);

        TestUtil.assertEquals(2, target.getMarks().length); // Should have two assessment marks

        int markA = target.getMarkByAssessment(assessmentA).getWeightedMark();
        int markB = target.getMarkByAssessment(assessmentB).getWeightedMark();
        int average = target.getAverage();
        int expectedAverage = (markA + markB) / 100;

        TestUtil.assertEquals(expectedAverage, average);
    }

    public static void main(String[] args) {
        TestStudentCourseAssociation test = new TestStudentCourseAssociation();
        test.testAssessmentMark();
    }
}
