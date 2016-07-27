package entity;

/**
 * Stores a username, password pair that belongs to a Person object.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
class Credentials {
    private String username;
    private String password;

    Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
