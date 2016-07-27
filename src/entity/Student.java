package entity;

/**
 * Stores information about a student.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class Student extends Person {
    private int clubs;
    private int volunteerHours;

    public Student(String name) {
        this(name, null, null);
    }

    public Student(String name, String username, String password) {
        super(name, username, password);
        clubs = 0;
        volunteerHours = 0;
    }

    public int getClubs() {
        return clubs;
    }

    public int getVolunteerHours() {
        return volunteerHours;
    }

    public void setClubs(int clubs) {
        this.clubs = clubs;
    }

    public void setVolunteerHours(int volunteerHours) {
        this.volunteerHours = volunteerHours;
    }

    /**
     * Gets the total number of times the student was late to any class.
     *
     * @return The total number of lates the student was late to any class.
     */
    public int getLates() {
        int lates = 0;
        for (CourseAssociation courseAssociation : getCourseAssociations()) {
            lates += ((StudentCourseAssociation) courseAssociation).getLates();
        }
        return lates;
    }

    /**
     * Gets the total number of times the student was absent at any class.
     *
     * @return The total number of lates the student was absent at any class.
     */
    public int getAbsences() {
        int absences = 0;
        for (CourseAssociation courseAssociation : getCourseAssociations()) {
            absences += ((StudentCourseAssociation) courseAssociation).getAbsences();
        }
        return absences;
    }

    /**
     * Gets the net score of a the student given by the formula:
     * Overall average - (lates / 2) - absences + (volunteer hours / 10) + clubs.
     *
     * @return The net score of the student.
     */
    public double getNetScore() {
        return getOverallAverage() - (getLates() * 0.5) - (getAbsences()) + (getVolunteerHours() * 0.1) +
                getClubs();
    }
}
