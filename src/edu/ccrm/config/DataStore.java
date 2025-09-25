
package edu.ccrm.config;

import edu.ccrm.domain.*;
import java.util.*;

/**
 * In-memory datastore Singleton for quick access in services.
 */
public class DataStore {
    private static DataStore ds;
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final Map<String, Instructor> instructors = new LinkedHashMap<>();

    private DataStore() {}

    public static synchronized DataStore getInstance() {
        if (ds == null) ds = new DataStore();
        return ds;
    }

    public Map<String, Student> getStudents() { return students; }
    public Map<String, Course> getCourses() { return courses; }
    public Map<String, Instructor> getInstructors() { return instructors; }
}
