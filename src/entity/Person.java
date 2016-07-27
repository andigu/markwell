package entity;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;

/**
 * Stores information about a person that is a member of a school.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */

public abstract class Person {
    private String name;
    private Credentials credentials;
    private Set<CourseAssociation> courseAssociations;

    /**
     * Constructs a new Person object with the given name and credentials.
     *
     * @param name     The name of the new person.
     * @param username The username of the new person.
     * @param password The password of the new person.
     */
    Person(String name, String username, String password) {
        this.name = name;
        credentials = new Credentials(username, password);
        courseAssociations = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void addCourseAssociation(CourseAssociation courseAssociation) {
        courseAssociations.add(courseAssociation);
    }

    public CourseAssociation[] getCourseAssociations() {
        return courseAssociations.toArray(new CourseAssociation[0]);
    }

    /**
     * Gets the CourseAssociation object that has the course with the given name.
     *
     * @param name The name of the course.
     * @return The CourseAssociation object that has the course with the given name.
     * @throws NoSuchElementException
     */
    public CourseAssociation getCourseAssociationByName(String name) {
        // Loops through all course associations until one with the matching course name is found
        for (CourseAssociation courseAssociation : courseAssociations) {
            if (name.equals(courseAssociation.getCourse().getName())) {
                return courseAssociation;
            }
        }
        throw new NoSuchElementException(name);
    }

    /**
     * Gets all the courses the person is enrolled in or is teaching.
     *
     * @return An array of all the courses the person is enrolled in or teaching.
     */
    public Course[] getCourses() {
        // Initiates array of courses the same length as the number of courses enrolled in
        Course[] courses = new Course[this.courseAssociations.size()];
        int count = 0;
        for (CourseAssociation courseAssociation : courseAssociations) { // Adds every course association's course into the array
            courses[count] = courseAssociation.getCourse();
            count++;
        }
        return courses;
    }

    /**
     * Gets the overall average of a course that the person is enrolled in or teaching.
     *
     * @param course The course that the person is enrolled in or teaching.
     * @return The overall average that the person has in the course (if it's a student, returns the student's mark
     * in that course, if it's a teacher, returns the average of all the student's marks in that course).
     */
    private int getAverageByCourse(Course course) {
        return getCourseAssociationByName(course.getName()).getAverage();
    }

    /**
     * Gets the overall average of the person. If it's a teacher, returns the average of all his/her course averages.
     * It it's a student, returns the average of all their marks in each course.
     *
     * @return The overall average of the person.
     */
    int getOverallAverage() {
        int markSum = 0;
        for (Course course : getCourses()) {
            markSum += getAverageByCourse(course); // Sums all the course averages of the person
        }
        if (courseAssociations.size() == 0) { // If person is enrolled in no courses (no zero division error)
            return 0;
        } else {
            return markSum / courseAssociations.size(); // Averages the sum of all the individual course averages
        }
    }

    /**
     * Compares two people by username.
     *
     * @return A negative number if the person's username is less lexicographically than the given person's username,
     * zero if they are the same, and a positive number if the person's username is lexicographically greater than the
     * given person's username.
     */
    public int compareTo(Person person) {
        return getUsername().compareTo(person.getUsername());
    }

    public String getUsername() {
        return credentials.getUsername();
    }

    public boolean passwordMatches(String password) {
        return password.equals(credentials.getPassword());
    }

    public String toString() {
        return String.format("name = %s, username = %s, password = %s", name, getUsername(), credentials.getPassword());
    }
}
