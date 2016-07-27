package entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Stores information about a course enrollment that a student has
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class StudentCourseAssociation extends CourseAssociation {
    private Student student;
    private int lates;
    private int absences;
    private Teacher teacher;
    private Set<AssessmentMark> marks;

    public StudentCourseAssociation(Course course, Student student, Teacher teacher) {
        super(course);
        this.student = student;
        this.teacher = teacher;
        this.lates = 0;
        this.absences = 0;
        marks = new HashSet<>();
    }

    public int getLates() {
        return lates;
    }

    public int getAbsences() {
        return absences;
    }

    public void setLates(int lates) {
        this.lates = lates;
    }

    public void setAbsences(int absences) {
        this.absences = absences;
    }

    public StudentCourseAssociation(Course course, Student student) {
        this(course, student, null);
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Student getStudent() {
        return student;
    }

    public AssessmentMark[] getMarks() {
        return marks.toArray(new AssessmentMark[0]);
    }

    /**
     * Updates an assessment mark for this instance. If the assessment mark exists, updates it with the new one,
     * otherwise adds it to the set. This ensures no more than one mark for one assessment.
     *
     * @param mark New assessment mark to add/update.
     */
    public void updateMark(AssessmentMark mark) {
        AssessmentMark existingMark = getMarkByAssessment(mark.getAssessment());
        if (existingMark != null) {
            marks.remove(existingMark);
        }
        marks.add(mark);
    }

    /**
     * Gets the overall average that the student has in the course.
     *
     * @return The overall average the student has in the course.
     */
    public int getAverage() {
        int markSum = 0;
        int weightSum = 0;
        for (AssessmentMark mark : marks) {
            markSum += mark.getWeightedMark();
            weightSum += mark.getAssessment().getWeight();
        }
        if (weightSum == 0) {
            return 0;
        } else {
            return markSum / weightSum;
        }
    }

    /**
     * Gets the AssessmentMark a student has in the course by assessment. Matches by checking the assessment names.
     *
     * @param assessment The assessment whose mark is to be found
     * @return The AssessmentMark the student has that is under the given assessment
     */
    public AssessmentMark getMarkByAssessment(Assessment assessment) {
        for (AssessmentMark mark : marks) {
            if (mark.getAssessment().getName().equals(assessment.getName())) {
                return mark;
            }
        }
        return null;
    }

    /**
     * Gets the average a student has in the course by category
     *
     * @param category The category whose average is to be found
     * @return The average the student has that is under the given category
     */
    public int getCategoryAverage(AssessmentCategory category) {
        int markSum = 0;
        int weightSum = 0;
        for (AssessmentMark mark : marks) {
            markSum += mark.getMark(category) * mark.getAssessment().getWeight();
            weightSum += mark.getAssessment().getWeight();
        }
        if (weightSum == 0) {
            return 0;
        } else {
            return markSum / weightSum;
        }
    }
}
