package entity;

/**
 * Tests assessment marks to ensure that their average calculations and weightings are done properly.
 *
 * @author Katrina James
 * @course ICS4U
 * @date 6/21/2016
 */
public class TestAssessment {

    public static void main(String[] args) {
        Assessment unit1Test = new Assessment("Katrina", new Course("test course"), 5);

        AssessmentMark testMark = new AssessmentMark(unit1Test);

        testMark.setMark(AssessmentCategory.Knowledge, 93);
        testMark.setMark(AssessmentCategory.Thinking, 82);
        testMark.setMark(AssessmentCategory.Communication, 86);
        testMark.setMark(AssessmentCategory.Application, 78);

        System.out.println(testMark.getWeightedMark());
    }
}
