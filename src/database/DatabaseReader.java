package database;

import entity.*;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Reads data from a database and puts it into objects. Read-only access.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class DatabaseReader extends DatabaseAccessor {
    private Map<String, Student> students; // Map of students with (username, Student object) pairs
    private Map<String, Teacher> teachers; // Map of teachers with (username, Teacher object) pairs
    private Map<String, Course> courses; // Map of courses with (course code, Course object) pairs

    /**
     * Constructs new DatabaseReader object, initiates data needed to connect to the database, reads all data from the
     * database
     *
     * @param databaseName Name of the database to connect to.
     * @param username     Username of the user for the server.
     * @param password     Password of the user for the server.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public DatabaseReader(String databaseName, String username, String password) throws SQLException, ClassNotFoundException {
        super(databaseName, username, password);
        refresh(); // Gets all initial data from database so it's easier for callers initialize all data fully
    }

    /**
     * Refreshes the objects stored by re-reading data from the database. To be used when database is updated with new data.
     *
     * @throws SQLException
     */
    void refresh() throws SQLException {
        students = new TreeMap<>();
        teachers = new TreeMap<>();
        courses = new TreeMap<>();
        fetchCourses();
        fetchStudents();
        fetchTeachers();
        makeStudentTeacherConnections();
    }

    /**
     * Reads data from a table in the database specified.
     *
     * @param connection Connection to database from which to read data.
     * @param tableName  Name of table from which to read data.
     * @return ResultSet of the data in the given table.
     * @throws SQLException
     */
    private ResultSet fetchTable(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM " + tableName);
    }

    /**
     * Reads data from students table into Student objects.
     *
     * @throws SQLException
     */
    private void fetchStudents() throws SQLException {
        Connection connection = getConnection();

        // Try-with-resources statement gets all data from all 3 tables required to initialize students. ResultSets are
        // automatically closed at the end of the try block.
        try (ResultSet studentRecords = fetchTable(connection, "student");
             ResultSet courseAssociationRecord = fetchTable(connection, "student_course_association");
             ResultSet markRecord = fetchTable(connection, "mark")) {

            // Gets basic information about the student. No connections required - simple data such as name and # of clubs
            while (studentRecords.next()) {
                String name = studentRecords.getString("student_name");
                String username = studentRecords.getString("student_id");
                String password = studentRecords.getString("student_password");
                int clubs = studentRecords.getInt("clubs");
                int volunteerHours = studentRecords.getInt("volunteer_hours");
                Student student = new Student(name, username, password);
                student.setClubs(clubs);
                student.setVolunteerHours(volunteerHours);
                students.put(username, student);
            }

            // Connects students with each of their course enrollments (StudentCourseAssociation objects)
            while (courseAssociationRecord.next()) {
                String courseId = courseAssociationRecord.getString("course_id");
                String studentId = courseAssociationRecord.getString("student_id");
                int lates = courseAssociationRecord.getInt("lates");
                int absences = courseAssociationRecord.getInt("absences");
                Course course = courses.get(courseId); // Gets the Course with matching courseId
                Student student = students.get(studentId); // Gets the Student with matching username
                StudentCourseAssociation courseAssociation = new StudentCourseAssociation(course, student);
                courseAssociation.setLates(lates);
                courseAssociation.setAbsences(absences);
                student.addCourseAssociation(courseAssociation);
            }

            String[] categories = new String[]{"knowledge", "thinking", "application", "communication"};
            Statement statement = connection.createStatement();
            while (markRecord.next()) {
                String studentCourseAssociationId = markRecord.getString("student_course_association_id");
                // Gets the row from the student_course_association with the matching primary key (student_course_association_id)
                // Note - student_course_association_id has no significant value for the actual program, it's used to
                // find data from the student_course_association table with only one piece of data, and normalizes the mark table.
                ResultSet studentIdMatch = statement.executeQuery("SELECT * FROM student_course_association WHERE " +
                        "student_course_association_id = " + studentCourseAssociationId);
                studentIdMatch.next();
                String studentId = studentIdMatch.getString("student_id");
                String courseId = studentIdMatch.getString("course_id");
                String assessmentName = markRecord.getString("assessment_id");
                Student student = students.get(studentId);
                // Gets the courseAssociation of the student where the course's name matches the course code in the database.
                StudentCourseAssociation courseAssociation = (StudentCourseAssociation) student.getCourseAssociationByName(courseId);
                Assessment assessment = courseAssociation.getCourse().getAssessmentByName(assessmentName);
                AssessmentMark assessmentMark = new AssessmentMark(assessment);
                for (String category : categories) {
                    int mark = markRecord.getInt(category + "_mark"); // Gets the mark under the category
                    assessmentMark.setMark(category, mark);
                }
                courseAssociation.updateMark(assessmentMark); // Adds mark to the studentCourseAssociation
            }
        } finally {
            closeConnection(connection); // Close the connection to the database to stop resource leaks
        }
    }

    /**
     * Reads data from teachers table into Teacher objects.
     *
     * @throws SQLException
     */
    private void fetchTeachers() throws SQLException {
        Connection connection = getConnection();

        // Try-with-resources statement gets all data from both tables required to initialize teachers. ResultSets are
        // automatically closed at the end of the try block.
        try (ResultSet teacherRecords = fetchTable(connection, "teacher");
             ResultSet courseAssociationRecord = fetchTable(connection, "teacher_course_association")) {
            // Gets all basic information about the teacher. No pointers initialized yet
            while (teacherRecords.next()) {
                String name = teacherRecords.getString("teacher_name");
                String username = teacherRecords.getString("teacher_id");
                String password = teacherRecords.getString("teacher_password");
                String email = teacherRecords.getString("teacher_email");
                teachers.put(username, new Teacher(name, username, password, email));
            }

            // Connects teachers with all the courses that he/she is teaching
            while (courseAssociationRecord.next()) {
                String courseId = courseAssociationRecord.getString("course_id");
                String teacherId = courseAssociationRecord.getString("teacher_id");
                Course course = courses.get(courseId); // Gets course with matching course code
                Teacher teacher = teachers.get(teacherId); // Gets teacher with matching username
                teacher.addCourseAssociation(new TeacherCourseAssociation(course, teacher));
            }
        } finally {
            closeConnection(connection); // Closes the connection to the database to stop resource leaks
        }
    }

    /**
     * Reads data from students table into Course objects.
     *
     * @throws SQLException
     */
    private void fetchCourses() throws SQLException {
        Connection connection = getConnection();

        // Try-with-resources statement gets all data from the table required to initialize courses. ResultSets are
        // automatically closed at the end of the try block.
        try (ResultSet courseRecords = fetchTable(connection, "course")) {
            String[] categories = new String[]{"knowledge", "thinking", "application", "communication"};
            while (courseRecords.next()) {
                String name = courseRecords.getString("course_id");
                Course course = new Course(name);
                // Loops through all categories and sets the weights for each
                for (String category : categories) {
                    int categoryWeight = courseRecords.getInt(category + "_weight");
                    course.setCategoryWeight(AssessmentCategory.fromValue(category), categoryWeight);
                }
                courses.put(name, course);
            }

            ResultSet assessmentRecords = fetchTable(connection, "assessment");
            while (assessmentRecords.next()) {
                String courseId = assessmentRecords.getString("course_id");
                String name = assessmentRecords.getString("assessment_id");
                int weight = assessmentRecords.getInt("weight");
                Course course = courses.get(courseId);
                Assessment assessment = new Assessment(name, course, weight);
                // Puts in the categories for each assessment
                if (assessmentRecords.getBoolean("has_knowledge")) {
                    assessment.addCategory("knowledge");
                }
                if (assessmentRecords.getBoolean("has_thinking")) {
                    assessment.addCategory("thinking");
                }
                if (assessmentRecords.getBoolean("has_application")) {
                    assessment.addCategory("application");
                }
                if (assessmentRecords.getBoolean("has_communication")) {
                    assessment.addCategory("communication");
                }
                course.addAssessment(assessment);
            }
        } finally {
            closeConnection(connection);  // Closes the connection to the database to stop resource leaks
        }
    }

    /**
     * Connects the Student objects with the Teacher objects through CourseAssociation objects. Necessary because there
     * are some circular references in the student and teacher objects.
     *
     * @throws SQLException
     */
    private void makeStudentTeacherConnections() throws SQLException {
        Connection connection = getConnection();
        // Try-with-resources statement gets all data from the table required to connect teachers and students.
        // ResultSets are automatically closed at the end of the try block.
        try (ResultSet studentCourseAssociationRecords = fetchTable(connection, "student_course_association")) {
            while (studentCourseAssociationRecords.next()) {
                String studentId = studentCourseAssociationRecords.getString("student_id");
                String teacherId = studentCourseAssociationRecords.getString("teacher_id");
                String courseId = studentCourseAssociationRecords.getString("course_id");
                Student student = students.get(studentId);
                Teacher teacher = teachers.get(teacherId);
                // Gets the courseAssociations for both the student and teacher where the course name matches courseId
                StudentCourseAssociation studentCourseAssociation = (StudentCourseAssociation) student.getCourseAssociationByName(courseId);
                TeacherCourseAssociation teacherCourseAssociation = (TeacherCourseAssociation) teacher.getCourseAssociationByName(courseId);
                studentCourseAssociation.setTeacher(teacher);
                teacherCourseAssociation.addStudentCourseAssociation(studentCourseAssociation);
            }
        } finally {
            closeConnection(connection);  // Closes the connection to the database to stop resource leaks
        }
    }

    /**
     * Getter for all Student objects.
     *
     * @return Array of all Student objects
     */
    public Student[] getStudents() {
        return students.values().toArray(new Student[0]);
    }

    /**
     * Getter for all Teacher objects.
     *
     * @return Array of all Teacher objects
     */
    public Teacher[] getTeachers() {
        return teachers.values().toArray(new Teacher[0]);
    }

    /**
     * Getter for all Course objects.
     *
     * @return Array of all Course objects
     */
    Course[] getCourses() {
        return courses.values().toArray(new Course[0]);
    }
}
