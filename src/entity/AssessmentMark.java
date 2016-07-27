package entity;

import java.util.EnumMap;

/**
 * Stores information about a mark a student has, for example:
 * <ul>
 * <li>Assessment: Unit 4 Math test</li>
 * <li>Marks: Knowledge - 60%, Application - 80%</li>
 * </ul>
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class AssessmentMark {
    private Assessment assessment;
    private EnumMap<AssessmentCategory, Integer> categoryMarks;

    public AssessmentMark(Assessment assessment) {
        this.assessment = assessment;
        this.categoryMarks = new EnumMap<>(AssessmentCategory.class);
    }

    public Assessment getAssessment() {
        return assessment;
    }

    /**
     * Gets the mark of the assessment that is calculated into the student's overall average.
     *
     * @return The weighted mark of the assessment.
     */
    int getWeightedMark() {
        return getUnweightedMark() * assessment.getWeight();
    }

    /**
     * Gets the overall mark of the assessment.
     *
     * @return The unweighted mark of the assessment.
     */
    int getUnweightedMark() {
        int total = 0; // Stores the total number of marks the student has (after category weightings are considered)
        int weightTotal = 0; // Stores the total weight of all assessments (weights are not stored as percents, they are arbitrary integers)
        for (AssessmentCategory assessmentCategory : categoryMarks.keySet()) {
            int categoryWeight = assessment.getCourse().getCategoryWeights().get(assessmentCategory); // Gets the weight of the category
            total += categoryWeight * categoryMarks.get(assessmentCategory); // Gets the mark of that category
            weightTotal += categoryWeight;
        }
        if (weightTotal == 0) { // In case there are have been no assessments assigned yet
            return total;
        }
        return total / weightTotal;
    }

    public void setMark(AssessmentCategory assessmentCategory, int mark) {
        categoryMarks.put(assessmentCategory, mark);
    }

    public void setMark(String assessmentCategory, int mark) {
        setMark(AssessmentCategory.fromValue(assessmentCategory), mark);
    }

    public String toString() {
        return String.format("assessment = %s, mark = %s", assessment.getName(), getUnweightedMark());
    }

    public int getMark(AssessmentCategory category) {
        if (!categoryMarks.containsKey(category)) {
            return 0;
        } else {
            return categoryMarks.get(category);
        }
    }

    private boolean hasCategory(AssessmentCategory category) {
        return categoryMarks.containsKey(category);
    }

    public boolean hasCategory(String category) {
        return hasCategory(AssessmentCategory.fromValue(category));
    }
}
