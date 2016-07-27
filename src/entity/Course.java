package entity;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Stores information about a course, including the name, a list of the assessments in that course, and the weights
 * for each category.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class Course {
    private String name;
    private List<Assessment> assessments;
    private EnumMap<AssessmentCategory, Integer> categoryWeights;

    public Course(String name) {
        this.name = name;
        assessments = new ArrayList<>();
        categoryWeights = new EnumMap<>(AssessmentCategory.class);
    }

    EnumMap<AssessmentCategory, Integer> getCategoryWeights() {
        return categoryWeights;
    }

    public String getName() {
        return name;
    }

    public Assessment[] getAssessments() {
        return assessments.toArray(new Assessment[0]);
    }

    /**
     * Gets the assessment that matches the given name.
     *
     * @param name The name of the assessment to be found.
     * @return The Assessment object with the given name.
     * @throws NoSuchElementException
     */
    public Assessment getAssessmentByName(String name) {
        for (Assessment assessment : assessments) { // Loops through all assessments until one with the same name is found.
            if (name.equals(assessment.getName())) {
                return assessment;
            }
        }
        throw new NoSuchElementException(name);
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    /**
     * Updates the category with the new weight if it's already in the category weights, if not, adds the category
     * with the given weight.
     *
     * @param category The category of the weight to be updated.
     * @param weight   The weight of the category.
     */
    public void setCategoryWeight(AssessmentCategory category, int weight) {
        categoryWeights.put(category, weight);
    }

    /**
     * Deletes the assessment with the given name.
     *
     * @param name The name of the assessment to be deleted.
     */
    public void deleteAssessment(String name) {
        for (Assessment assessment : assessments) { // Loops through all assessments until one with the same name is found
            if (name.equals(assessment.getName())) {
                assessments.remove(assessment);
            }
        }
    }
}
