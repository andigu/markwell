package entity;

import java.util.EnumMap;

import utility.TestUtil;

/**
 * Tests assessment marks to ensure that their average calculations and weightings are done properly.
 *
 * @author Katrina James
 * @course ICS4U
 * @date 6/21/2016
 */
public class TestAssessmentMark {
    private AssessmentMark mark;

    /**
     * Creates sample assessment to test on
     *
     * @return A sample assessment
     */
    private Assessment createAssessment() {
        EnumMap<AssessmentCategory, Integer> categoryWeights = new EnumMap<>(AssessmentCategory.class);
        Course course = new Course("Test course");
        for (AssessmentCategory category : categoryWeights.keySet()) {
            course.setCategoryWeight(category, categoryWeights.get(category));
        }
        categoryWeights.put(AssessmentCategory.Application, 50);
        categoryWeights.put(AssessmentCategory.Thinking, 10);
        categoryWeights.put(AssessmentCategory.Communication, 10);
        categoryWeights.put(AssessmentCategory.Knowledge, 30);
        return new Assessment("Test", course, 10);
    }

    /**
     * Test the math behind calculating mark averages
     */
    private void testMarks() {
        mark = new AssessmentMark(createAssessment());
        mark.setMark(AssessmentCategory.Application, 90);
        mark.setMark(AssessmentCategory.Communication, 96);

        TestUtil.assertTrue(mark.hasCategory("application"));
        TestUtil.assertTrue(mark.hasCategory("communication"));
        TestUtil.assertTrue(mark.hasCategory("knowledge"));
        TestUtil.assertTrue(mark.hasCategory("thinking"));

        TestUtil.assertEquals(0, mark.getMark(AssessmentCategory.Thinking));

        int unweighted = mark.getUnweightedMark();
        int expected = (50 * 90 + 10 * 96) / 60;

        TestUtil.assertEquals(expected, unweighted);
        int weighted = mark.getWeightedMark();
        TestUtil.assertEquals(unweighted * 10, weighted);
    }

    /**
     * Test when no mark has been assigned cases
     */
    private void testEdgeCase() {
        mark = new AssessmentMark(createAssessment());
        int unweighted = mark.getUnweightedMark();
        TestUtil.assertEquals(0, unweighted);
    }

    public static void main(String[] args) {
        TestAssessmentMark test = new TestAssessmentMark();
        test.testMarks();
        test.testEdgeCase();

        System.out.println("Tests all successful");
    }
}
