package entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Stores data about an assessment that a teacher would assign, for example:
 * <ul>
 * <li>Name: Unit 4 Math Test</li>
 * <li>Course: Math</li>
 * <li>Weight: 4</li>
 * <li>Categories: Knowledge, Thinking</li>
 * </ul>
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class Assessment {

    private String name;
    private int weight;
    private Course course;
    private Set<AssessmentCategory> categories;

    /**
     * Constructs new Assessment object that starts out with no categories.
     *
     * @param name   Name of the assessment.
     * @param course Course that the assessment belongs to.
     * @param weight Weight of the assessment in the final average.
     */
    public Assessment(String name, Course course, int weight) {
        this.name = name;
        this.course = course;

        this.weight = weight;
        categories = new HashSet<>();
    }

    public Course getCourse() {
        return course;
    }

    private void addCategory(AssessmentCategory category) {
        categories.add(category);
    }

    public void addCategory(String category) {
        addCategory(AssessmentCategory.fromValue(category));
    }

    public boolean hasKnowledge() {
        return categories.contains(AssessmentCategory.Knowledge);
    }

    public boolean hasThinking() {
        return categories.contains(AssessmentCategory.Thinking);
    }

    public boolean hasCommunication() {
        return categories.contains(AssessmentCategory.Communication);
    }

    public boolean hasApplication() {
        return categories.contains(AssessmentCategory.Application);
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}
