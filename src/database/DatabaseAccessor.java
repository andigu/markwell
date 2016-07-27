package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides way to access a database.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
abstract class DatabaseAccessor {
    private String databaseName;
    private String username;
    private String password;

    DatabaseAccessor(String databaseName, String username, String password) throws SQLException, ClassNotFoundException {
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets connection to database that was specified in the constructor.
     *
     * @return Connection to database
     * @throws SQLException
     */
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false", username, password);
    }

    /**
     * Closes connection to database to prevent resource leaks.
     *
     * @param connection Connection to be closed
     */
    void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ignored) {
        }
    }
}
