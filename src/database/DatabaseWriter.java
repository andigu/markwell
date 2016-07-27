package database;

import entity.*;

import java.sql.*;

/**
 * Provides methods to update data or add new data to a database. Read and write access.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class DatabaseWriter extends DatabaseAccessor {
    public DatabaseWriter(String databaseName, String username, String password)
            throws SQLException, ClassNotFoundException {
        super(databaseName, username, password);
    }

    /**
     * Gets the primary key of the row in the student_course_association table containing the given course code,
     * teacher number, and student number. Helper function for functions needing the student_course_association_id
     * in order to update data about it. Usages: updateMark function.
     *
     * @param courseId  Code of the course to be found.
     * @param teacherId The teacher number of the teacher to be found.
     * @param studentId The student number of the student to be found.
     * @return The primary key of the row in student_course_association with the given information
     * @throws SQLException
     */
    private int getStudentCourseAssociationId(String courseId, String teacherId, String studentId) throws SQLException {
        // Query gets the student_course_association_id where the course code, teacher number, and student number match
        // the ones passed in.
        String querySQL = "SELECT student_course_association_id FROM student_course_association"
                + " WHERE course_id = ? AND teacher_id =? AND student_id =?";
        PreparedStatement statement = getConnection().prepareStatement(querySQL);
        statement.setString(1, courseId);
        statement.setString(2, teacherId);
        statement.setString(3, studentId);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getInt("student_course_association_id"); // Returns the matching student_course_association_id
        } else {
            // Tells the user the desired student course association doesn't exist
            throw new RuntimeException(String.format("No such student course association found: courseId = %s, " +
                    "teachId = %s, studentId = %s", courseId, teacherId, studentId));
        }
    }

    /**
     * Updates the data from the given AssessmentMark into the database, if it's already in the database.
     * If not, it adds the data from the AssessmentMark into the database.
     *
     * @param studentCourseAssociation The studentCourseAssociation object that the mark belongs to
     * @param mark                     The AssessmentMark object containing the data to be added or updated
     * @throws SQLException
     */
    public void updateMark(StudentCourseAssociation studentCourseAssociation, AssessmentMark mark) throws SQLException {
        Connection connection = getConnection();
        // Statement to updates the row in the mark table, setting new marks for each category
        PreparedStatement update = connection.prepareStatement("UPDATE mark SET knowledge_mark = ?,"
                + "thinking_mark = ?, application_mark = ?, communication_mark = ? WHERE assessment_id = ?  "
                + "AND student_course_association_id = ?");
        // Statement to add a mark into the mark table with the given name and category marks
        PreparedStatement insert = connection.prepareStatement("INSERT INTO mark(assessment_id, "
                + "student_course_association_id, knowledge_mark, thinking_mark, application_mark, "
                + "communication_mark) VALUES (?, ?, ?, ?, ?, ?)");

        // Gets the primary key of student_course_association where the row data matches the given course code,
        // teacher username, and student username
        int studentCourseAssociationId = getStudentCourseAssociationId(studentCourseAssociation.getCourse().getName(),
                studentCourseAssociation.getTeacher().getUsername(), studentCourseAssociation.getStudent().getUsername());

        update.clearParameters();
        update.setInt(1, mark.getMark(AssessmentCategory.Knowledge));
        update.setInt(2, mark.getMark(AssessmentCategory.Thinking));
        update.setInt(3, mark.getMark(AssessmentCategory.Application));
        update.setInt(4, mark.getMark(AssessmentCategory.Communication));
        update.setString(5, mark.getAssessment().getName());
        update.setInt(6, studentCourseAssociationId);

        if (update.executeUpdate() != 1) { // If no row was updated then the assessment doesn't exist, so add the assessment in
            insert.clearParameters();
            insert.setString(1, mark.getAssessment().getName());
            insert.setInt(2, studentCourseAssociationId);
            insert.setInt(3, mark.getMark(AssessmentCategory.Knowledge));
            insert.setInt(4, mark.getMark(AssessmentCategory.Thinking));
            insert.setInt(5, mark.getMark(AssessmentCategory.Application));
            insert.setInt(6, mark.getMark(AssessmentCategory.Communication));
            insert.executeUpdate();
        }
        closeConnection(connection);
    }

    /**
     * Adds the data from the Assessment into the database.
     *
     * @param assessment Assessment object containing the data to be added
     * @param course     Course object that the assessment belongs to
     */
    public void addAssessment(Assessment assessment, Course course) throws SQLException {
        Connection connection = getConnection();
        // Statement to insert a new assessment into the assessment table
        PreparedStatement insert = connection.prepareStatement("INSERT INTO assessment(course_id, assessment_id,"
                + "weight, has_knowledge, has_thinking, has_application, has_communication) VALUES (?, ?, ?, ?, ?, ?, ?)");
        String courseId = course.getName();
        String assessmentId = assessment.getName();
        int weight = assessment.getWeight();
        insert.setString(1, courseId);
        insert.setString(2, assessmentId);
        insert.setInt(3, weight);
        insert.setBoolean(4, assessment.hasKnowledge());
        insert.setBoolean(5, assessment.hasThinking());
        insert.setBoolean(6, assessment.hasApplication());
        insert.setBoolean(7, assessment.hasCommunication());
        insert.executeUpdate();
        closeConnection(connection);
    }

    /**
     * Updates the data from the Assessment in the database. Must be separated from the addAssessment function because
     * there is a chance that the name was changed, which is the primary key of assessment in the table.
     *
     * @param oldName    The old name of the assessment to be updated. Required because it's the primary key of the assessment
     *                   and is needed to find the correct row to update
     * @param assessment The Assessment object containing the data to be updated
     * @param course     The Course object that the assessment belongs to
     */
    public void updateAssessment(String oldName, Assessment assessment, Course course) throws SQLException {
        Connection connection = getConnection();
        // Statement to update an assessment.
        PreparedStatement edit = connection.prepareStatement("UPDATE assessment SET assessment_id = ?, weight = ?,"
                + "has_knowledge = ?, has_thinking = ?, has_application = ?, has_communication = ? WHERE assessment_id = ?"
                + "AND course_id = ?");
        edit.setString(1, assessment.getName());
        edit.setInt(2, assessment.getWeight());
        edit.setBoolean(3, assessment.hasKnowledge());
        edit.setBoolean(4, assessment.hasThinking());
        edit.setBoolean(5, assessment.hasApplication());
        edit.setBoolean(6, assessment.hasCommunication());
        edit.setString(7, oldName);
        edit.setString(8, course.getName());
        edit.executeUpdate();
        closeConnection(connection);
    }

    /**
     * Updates the number of clubs a student is a member of in the database
     *
     * @param student The Student object to be updated
     * @param clubs   The new number of clubs for the Student object
     */
    public void setClubs(Student student, int clubs) throws SQLException {
        Connection connection = getConnection();
        // Statement to update the number of clubs a student is a member of
        String update = "UPDATE student SET clubs = ?  WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setInt(1, clubs);
        statement.setString(2, student.getName());
        statement.executeUpdate();
        closeConnection(connection);
    }

    /**
     * Updates the number of volunteer hours a student has acquired in the database
     *
     * @param student        The Student object to be updated
     * @param volunteerHours The new number of volunteer hours for the Student object
     */
    public void setVolunteerHours(Student student, int volunteerHours) throws SQLException {
        Connection connection = getConnection();
        // Statement to update the number of volunteer hours a student has in the database
        String update = "UPDATE student SET volunteer_hours = ? WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setInt(1, volunteerHours);
        statement.setString(2, student.getName());
        statement.executeUpdate();
        closeConnection(connection);
    }

    /**
     * Updates the number of lates a student has in the database
     *
     * @param courseAssociation The StudentCourseAssociation object to be updated (because lates are per course, not
     *                          per student)
     * @param lates             The new number of lates for the StudentCourseAssociation object
     */
    public void setLates(StudentCourseAssociation courseAssociation, int lates) throws SQLException {
        Connection connection = getConnection();
        // Statement to update the number of lates under a student's course enrollment
        String update = "UPDATE student_course_association SET lates = ? WHERE student_id = ? AND course_id = ? ";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setInt(1, lates);
        statement.setString(2, courseAssociation.getStudent().getName());
        statement.setString(3, courseAssociation.getCourse().getName());
        statement.executeUpdate();
        closeConnection(connection);
    }

    /**
     * Updates the number of absences a student has in the database
     *
     * @param courseAssociation The StudentCourseAssociation object to be updated (because absences are per course, not
     *                          per student)
     * @param absences          The new number of absences for the StudentCourseAssociation object
     * @throws SQLException
     */
    public void setAbsences(StudentCourseAssociation courseAssociation, int absences) throws SQLException {
        Connection connection = getConnection();
        // Statement to update the number of absences under a student's course enrollment
        String update = "UPDATE student_course_association SET absences = ? WHERE student_id = ? AND course_id = ? ";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setInt(1, absences);
        statement.setString(2, courseAssociation.getStudent().getName());
        statement.setString(3, courseAssociation.getCourse().getName());
        closeConnection(connection);
    }
}
