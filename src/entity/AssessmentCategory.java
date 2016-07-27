package entity;

/**
 * Categories that a mark can belong to:
 * <ul>
 * <li>Knowledge</li>
 * <li>Thinking</li>
 * <li>Application</li>
 * <li>Communication</li>
 * </ul>
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public enum AssessmentCategory {
    Knowledge("knowledge"),
    Thinking("thinking"),
    Application("application"),
    Communication("communication");

    private String value;

    AssessmentCategory(String value) {
        this.value = value;
    }

    /**
     * Gets the AssessmentCategory that matches with a given string value (e.g "knowledge" pairs to
     * AssessmentCategory.Knowledge).
     *
     * @param value String value of the category.
     * @return The enumerator entry that matches the given string value.
     * @throws IllegalArgumentException
     */
    public static AssessmentCategory fromValue(String value) {
        // Loops through all values of AssessmentCategory (i.e. AssessmentCategory.Knowledge, etc) and checks if that
        // category's string value matches the value passed in as a parameter.
        for (AssessmentCategory assessmentCategory : AssessmentCategory.values()) {
            if (assessmentCategory.value.equals(value)) {
                return assessmentCategory;
            }
        }
        // Invalid category name was passed in (i.e. app instead of application)
        throw new IllegalArgumentException("Invalid category: " + value);
    }
}
