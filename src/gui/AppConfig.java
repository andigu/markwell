package gui;

import service.DefaultService;
import service.Service;

import java.sql.SQLException;

/**
 * Connects the user interface to the service - providing a way for the front end to act with the backend. Provides
 * a central way to change database information and credentials in case database info changes.
 *
 * @author Katrina James
 * @course ICS4U
 * @date 6/20/2016
 */
public class AppConfig {
    private static Service service = null;

    public static Service getService() {
        if (service == null) {
            try {
                service = new DefaultService("markwell_tester", "root", "markwell");
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return service;
    }
}
