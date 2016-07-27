package entity;

/**
 * Stores information about a teacher
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class Teacher extends Person {
    private String email;

    public Teacher(String name, String teacherNumber, String password, String email) {
        super(name, teacherNumber, password);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
