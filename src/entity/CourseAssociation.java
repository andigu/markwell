package entity;

/**
 * Represents the connection between a Person object and a Course object.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public abstract class CourseAssociation {
    private Course course;

    CourseAssociation(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public abstract int getAverage();
}
